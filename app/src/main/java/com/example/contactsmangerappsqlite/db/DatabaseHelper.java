package com.example.contactsmangerappsqlite.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contactsmangerappsqlite.db.entity.Contact;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase SQLiteDatabase) {

        SQLiteDatabase.execSQL(Contact.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Inserting data into Database
    public long insertContact(String name,String email,String phoneNumb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Contact.COLUMN_NAME, name);
        contentValues.put(Contact.COLUMN_EMAIL, email);
        contentValues.put(Contact.COLUMN_PHONE_NUMBER, phoneNumb);

        long id = db.insert(Contact.TABLE_NAME, null,contentValues);
        db.close();
        return id;
    }

    //Getting contact data from Database
    public Contact getContact(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Contact.TABLE_NAME,
                new String[]{
                        Contact.COLUMN_ID,
                        Contact.COLUMN_NAME,
                        Contact.COLUMN_EMAIL,
                        Contact.COLUMN_PHONE_NUMBER},
                Contact.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(
                cursor.getInt(cursor.getColumnIndexOrThrow(Contact.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_PHONE_NUMBER))
        );
        cursor.close();
        return contact;
    }


    //Getting all contacts
    public ArrayList<Contact> getAllContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Contact.TABLE_NAME
                + " ORDER BY " + Contact.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Contact.COLUMN_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_NAME)));
                contact.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_EMAIL)));
                contact.setPhoneNumb(cursor.getString(cursor.getColumnIndexOrThrow(Contact.COLUMN_PHONE_NUMBER)));

                contacts.add(contact);
            }while(cursor.moveToNext());
        }

        db.close();
        return contacts;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME, contact.getName());
        contentValues.put(Contact.COLUMN_EMAIL, contact.getEmail());
        contentValues.put(Contact.COLUMN_PHONE_NUMBER, contact.getPhoneNumb());

        return db.update(Contact.TABLE_NAME, contentValues, Contact.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(contact.getId())});
    }


    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Contact.TABLE_NAME, Contact.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});

        db.close();
    }
}
