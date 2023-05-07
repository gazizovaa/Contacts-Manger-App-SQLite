package com.example.contactsmangerappsqlite.db.entity;

public class Contact {
    //Constants for Database
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "contact_ID";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_EMAIL = "contact_email";
    public static final String COLUMN_PHONE_NUMBER = "contact_phoneNumb";

    //Variables
    private int id;
    private String name;
    private String email;
    private String phoneNumb;

    //Constructor
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
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_PHONE_NUMBER + " CURRENT_PHONE_NUMB"
            + ")";
}
