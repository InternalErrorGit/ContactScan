package ch.zli.pg.app.data;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Contact {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "first_name")
    public String firstname;

    @ColumnInfo(name = "last_name")
    public String lastname;



}
