package ch.zli.pg.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ch.zli.pg.contactscan.R;

public class CheckView extends AppCompatActivity {

    private TextView textName, textNumber;
    private Button buttonRescan, buttonSave, buttonCancel;
    private String name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_view);
        initComponents();
        prepareComponents();
    }

    private void prepareComponents() {
        String url = getIntent().getData().toString().replace("%20", " ").replace("%space", " ");
        name = url.substring(url.indexOf("=") + 1, url.indexOf("&"));
        number = url.substring(url.lastIndexOf("=") + 1);

        textName.setText(name);
        textNumber.setText(number);

        buttonRescan.setOnClickListener(this::onRescan);
        buttonSave.setOnClickListener(this::onSave);
        buttonCancel.setOnClickListener(this::onCancel);
    }

    public void onRescan(View view) {
        onBackPressed();
    }

    public void onCancel(View view) {
        startActivity(new Intent(CheckView.this, SplashView.class));
    }

    public void onSave(View view) {
        Intent saveIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        saveIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        saveIntent.putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, number);
        startActivityForResult(saveIntent, 1);
    }


    private void initComponents() {
        textName = findViewById(R.id.textName);
        textNumber = findViewById(R.id.textNumber);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonRescan = findViewById(R.id.buttonRescan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
    }
}