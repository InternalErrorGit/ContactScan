package ch.zli.pg.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDAO contactDAO();

    private static ContactDatabase database;

    public static ContactDatabase getDatabase(Context context, String databaseName) {
        if (database == null)
            database = Room.databaseBuilder(context, ContactDatabase.class, databaseName).build();
        return database;
    }

}
