package com.example.contactsmangerappsqlite.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    //Variables
    @ColumnInfo(name = "contact_ID")
    @PrimaryKey(autoGenerate = true) //this will increase id by 1 automatically
    private int id;
    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_email")
    private String email;
    @ColumnInfo(name = "contact_phoneNumb")
    private String phoneNumb;

    //Constructor
    @Ignore
    public Contact(){

    }

    public Contact(int id, String name, String email, String phoneNumb){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumb = phoneNumb;
    }

    //Getters and Setter
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPhoneNumb(String phoneNumb){
        this.phoneNumb = phoneNumb;
    }

    public String getPhoneNumb(){
        return phoneNumb;
    }

    //SQL query - create the table
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + COLUMN_NAME + " TEXT,"
//            + COLUMN_EMAIL + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
//            + COLUMN_PHONE_NUMBER + " CURRENT_PHONE_NUMB"
//            + ")";
}
