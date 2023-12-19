package com.example.suleiman_smarthomeenergymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationMessage extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);

        textView = findViewById(R.id.tv_message);
        Bundle bundle = getIntent().getExtras();                                                    //call the data which is passed by another intent
        textView.setText(bundle.getString("message"));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NotificationMessage.this, HomeScreen.class));
        finish();
    }
}