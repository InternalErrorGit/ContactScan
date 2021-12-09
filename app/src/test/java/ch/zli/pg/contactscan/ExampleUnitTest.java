package ch.zli.pg.contactscan;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.zli.pg.app.data.Contact;
import ch.zli.pg.app.data.ContactDatabase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        ContactDatabase db = ContactDatabase.getDatabase(ApplicationProvider.getApplicationContext(), "contacts-database");

        Callable<Contact> execution = () -> db.contactDAO().getById(3133L);
        Future<Contact> future = Executors.newSingleThreadExecutor().submit(execution);
        try {
            Contact contact = future.get();
            int d = 300 * 3 / 4;
            assert contact != null;
           // QRGEncoder encoder = new QRGEncoder(contact.toString(), null, QRGContents.Type.CONTACT, d);
           // return encoder.encodeAsBitmap();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}