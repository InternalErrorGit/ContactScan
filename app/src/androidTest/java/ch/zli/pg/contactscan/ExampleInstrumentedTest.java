package ch.zli.pg.contactscan;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.zli.pg.app.data.Contact;
import ch.zli.pg.app.data.ContactDatabase;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
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