package ch.zli.pg.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.zli.pg.contactscan.R;

public class MenuView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);
    }

    public void scan(View view) {

    }


    public void share(View view) {
        startActivity(new Intent(this, SelectView.class));
    }

}