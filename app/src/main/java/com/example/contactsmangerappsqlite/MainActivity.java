package com.example.contactsmangerappsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.contactsmangerappsqlite.adapter.ContactsAdapter;
import com.example.contactsmangerappsqlite.db.ContactsDatabase;
import com.example.contactsmangerappsqlite.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Using SQLite

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsDatabase contactsDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts Manager");


        //RecyclerView
        recyclerView = findViewById(R.id.recycler_view_contacts);
        //Database
        contactsDatabase = Room.databaseBuilder();

        //Contacts list
        contactArrayList.addAll(db.getAllContacts());

        contactsAdapter = new ContactsAdapter(this,contactArrayList,MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false,null,-1);
            }
        });

    }

    public void addAndEditContacts(final boolean isUpdated,final Contact contact,final int positions) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact,null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);
        final EditText contactPhoneNumb = view.findViewById(R.id.phoneNumb);

        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        if(isUpdated && contact != null){
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
            contactPhoneNumb.setText(contact.getPhoneNumb());
        }

        alertDialog.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if(isUpdated){
                                    DeleteContact(contact, positions);
                                }else{
                                    dialogInterface.cancel();
                                }
                            }
                        }

                );

        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(newContact.getText().toString())){
                    Toast.makeText(MainActivity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    alertDialog1.dismiss();
                }

                if(isUpdated && contact != null){
                    UpdateContact(newContact.getText().toString(),contactEmail.getText().toString(),contactPhoneNumb.getText().toString(),positions);

                }else{
                    CreateContact(newContact.getText().toString(),contactEmail.getText().toString(),contactPhoneNumb.getText().toString());
                }
            }
        });
    }

    private void UpdateContact(String name, String email, String phoneNumb, int positions) {
        Contact contact = contactArrayList.get(positions);

        contact.setName(name);
        contact.setEmail(email);
        contact.setPhoneNumb(phoneNumb);

        db.updateContact(contact);
        contactArrayList.set(positions,contact);
        contactsAdapter.notifyDataSetChanged();
    }

    private void DeleteContact(Contact contact, int positions) {
        contactArrayList.remove(positions);
        db.deleteContact(contact);
        contactsAdapter.notifyDataSetChanged();
    }

    private void CreateContact(String name,String email,String phoneNumb){
        long id = db.insertContact(name,email,phoneNumb);
        Contact contact = db.getContact(id);

        if(contact != null){
            contactArrayList.add(0,contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }


    //Menu Bar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}