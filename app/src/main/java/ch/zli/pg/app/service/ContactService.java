package ch.zli.pg.app.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.zli.pg.app.data.Contact;
import ch.zli.pg.app.data.ContactDatabase;
import ch.zli.pg.app.data.ContactModel;

public class ContactService extends Service {

    private final IBinder binder = new ContactBinder();

    public class ContactBinder extends Binder {
        public ContactService getService() {
            return ContactService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @SuppressLint("Range")
    public void loadContacts() {
        ContactDatabase db = ContactDatabase.getDatabase(getApplicationContext(), "contacts-database");

        Callable<?> execution = (Callable<?>) () -> {
            db.contactDAO().deleteAll();

            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    Contact contact = new Contact();
                    @SuppressLint("Range") String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    contact.setName(name);


                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            @SuppressLint("Range") String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contact.number = phoneNo;
                        }
                        Log.i("ContactService", name);

                        db.contactDAO().insertAll(contact);
                        pCur.close();
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }
            db.contactDAO().getAll().forEach(ContactModel::addContact);
            return null;
        };

        Future<?> future = Executors.newSingleThreadExecutor().submit(execution);
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> getContacts() {
        Callable<List<Contact>> execution = () -> ContactDatabase.getDatabase(getApplicationContext(), "contacts-database").contactDAO().getAll();

        Future<List<Contact>> future = Executors.newSingleThreadExecutor().submit(execution);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}