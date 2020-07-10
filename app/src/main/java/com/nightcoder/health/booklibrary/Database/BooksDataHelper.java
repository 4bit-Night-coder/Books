package com.nightcoder.health.booklibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.nightcoder.health.booklibrary.Models.Book;

import java.util.ArrayList;

public class BooksDataHelper extends SQLiteOpenHelper {

    private String TABLE_NAME = "book_table";

    public BooksDataHelper(Context context) {
        super(context, "books", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_bookName TEXT NOT NULL PRIMARY KEY UNIQUE, " +
                "price INTEGER, stock INTEGER, category TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "org TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Book getBook(String bookName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "
                    + TABLE_NAME + " WHERE _bookName='" + bookName + "'", null);
            if (cursor.moveToFirst()) {
                Book book = new Book();
                book.setBookName(cursor.getString(0));
                book.setPrice(cursor.getInt(1));
                book.setStock(cursor.getInt(2));
                book.setCategory(cursor.getString(3));
                book.setAuthor(cursor.getString(4));
                book.setOrganization(cursor.getString(5));
                cursor.close();
                return book;
            } else {
                return null;
            }
        } catch (SQLiteException e) {
            return null;
        }
    }

    public ArrayList<Book> getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setBookName(cursor.getString(0));
                    book.setPrice(cursor.getInt(1));
                    book.setStock(cursor.getInt(2));
                    book.setCategory(cursor.getString(3));
                    book.setAuthor(cursor.getString(4));
                    book.setOrganization(cursor.getString(5));
                    books.add(book);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean addBook(String name, int price, int stock,
                           String cat, String author, String org) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_bookName", name);
        values.put("price", price);
        values.put("stock", stock);
        values.put("category", cat);
        values.put("author", author);
        values.put("org", org);
        long result = db.insert(
                TABLE_NAME, null,
                values);
        return result != -1;
    }

    public boolean removeBook(String bookName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME, "_bookName='" + bookName + "'", null);
        return res != -1;
    }

    public boolean updateStock(String bookName, int stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("stock", stock);
        return db.update(TABLE_NAME, cv, "_bookName='" + bookName + "'", null) > 0;
    }
}
