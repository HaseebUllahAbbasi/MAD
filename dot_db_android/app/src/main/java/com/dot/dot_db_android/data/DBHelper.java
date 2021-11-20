package com.dot.dot_db_android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.UniversalTimeScale;

import androidx.annotation.Nullable;

import com.dot.dot_db_android.R;
import com.dot.dot_db_android.model.Contact;
import com.dot.dot_db_android.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper
{

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE "+ Util.DATABASE_NAME+ "( "
                + Util.KEY_ID +" INTEGER PRIMARY KEY,"+ Util.KEY_NAME + " TEXT,"+ Util.KEY_PHONE+ "TEXT )";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        sqLiteDatabase.execSQL(DROP_TABLE,new String[]{Util.DATABASE_NAME});

        onCreate(sqLiteDatabase);
    }
    public void addContact(Contact contact)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME,contact.getName());
        contentValues.put(Util.KEY_PHONE,contact.getPhone());
        database.insert(Util.DATABASE_TABLE_NAME,null,contentValues);
        database.close();
    }
    public Contact getContact(int id)
    {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(Util.DATABASE_TABLE_NAME,new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE},Util.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
            int idRes = Integer.parseInt(cursor.getString(0));
            String nameRes = cursor.getString(1);
            String phoneRes = cursor.getString(2);

            return new Contact(idRes,nameRes,phoneRes);

        }
        return null;
    }
    //Get all Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Select all contacts
        String selectAll = "SELECT * FROM " + Util.DATABASE_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our data
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact("James", "213986");
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));

                //add contact objects to our list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        return contactList;
    }

    //Update contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE, contact.getPhone());

        //update the row
        //update(tablename, values, where id = 43)
        return db.update(Util.DATABASE_TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    //Delete single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.DATABASE_TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        db.close();
    }

    //Get contacts count
    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.DATABASE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}