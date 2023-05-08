package com.example.contactsmangerappsqlite.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactsmangerappsqlite.db.entity.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsDatabase extends RoomDatabase {

    //Linking ContactDAO with ContactsDatabase class
    public abstract ContactDAO getContactDAO();
}
