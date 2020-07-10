package com.nightcoder.health.booklibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nightcoder.health.booklibrary.Models.Order;

import java.util.ArrayList;

import static com.nightcoder.health.booklibrary.Literals.Database.KEY_ADDRESS;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_BOOK_NAME;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_COPY;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_EMAIL;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_GIFT;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_PIN_CODE;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_TIMESTAMP;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USERNAME;

public class OrderDatabase extends SQLiteOpenHelper {

    private final String TABLE_NAME = "orders_lists";

    public OrderDatabase(@Nullable Context context) {
        super(context, "Database_order", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                KEY_TIMESTAMP + " DOUBLE PRIMARY KEY UNIQUE, " +
                KEY_BOOK_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_ADDRESS + " TEXT, " +
                KEY_PIN_CODE + " TEXT, " +
                KEY_COPY + " INTEGER, " +
                KEY_GIFT + " BOOLEAN)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addOrder(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    Order order = new Order();
                    order.setBookName(cursor.getString(1));
                    order.setEmail(cursor.getString(2));
                    order.setUsername(cursor.getString(3));
                    order.setAddress(cursor.getString(4));
                    order.setPincode(cursor.getString(5));
                    order.setCopy(cursor.getInt(6));
                    order.setGift(cursor.getInt(7) == 1);
                    order.setTime(cursor.getDouble(0));
                    orders.add(order);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean removeOrder(Double timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_TIMESTAMP + "=" + timestamp, null) > 0;
    }

    public ArrayList<Order> getOrders(String email) {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    if (email.equals(cursor.getString(2))) {
                        Order order = new Order();
                        order.setBookName(cursor.getString(1));
                        order.setEmail(cursor.getString(2));
                        order.setUsername(cursor.getString(3));
                        order.setAddress(cursor.getString(4));
                        order.setPincode(cursor.getString(5));
                        order.setCopy(cursor.getInt(6));
                        order.setGift(cursor.getInt(7) == 1);
                        order.setTime(cursor.getDouble(0));
                        orders.add(order);
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
