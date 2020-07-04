package com.nightcoder.health.booklibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataHelper extends SQLiteOpenHelper {

    private String TABLE_NAME = "user_table";

    public UserDataHelper(Context context) {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "email TEXT PRIMARY KEY UNIQUE," +
                "name TEXT, pass TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "'" + TABLE_NAME + "'");
        onCreate(sqLiteDatabase);
    }

    public String signUser(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email='" + email + "'", null);
        if (cursor.moveToFirst()) {
            String password = cursor.getString(2);
            cursor.close();
            if (pass.equals(password)) {
                return "Success";
            } else {
                return "Password incorrect try another password";
            }
        } else {
            return "Invalid Email address. Try another or create one";
        }
    }

    public boolean addUser(String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("name", name);
        values.put("pass", pass);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }
}
