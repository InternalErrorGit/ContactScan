package ch.zli.pg.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDAO {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Insert
    void insertAll(Contact... contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("SELECT * FROM contact WHERE contact.id == :contact_id")
    Contact getById(long contact_id);
}
