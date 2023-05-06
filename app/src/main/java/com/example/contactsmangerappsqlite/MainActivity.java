package com.example.contactsmangerappsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.contactsmangerappsqlite.db.entity.Contact;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addAndEditContacts(boolean b, Contact contact, int positions) {
    }
}