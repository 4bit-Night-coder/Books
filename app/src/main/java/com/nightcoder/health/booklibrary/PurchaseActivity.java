package com.nightcoder.health.booklibrary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Database.OrderDatabase;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.Supports.Memory;
import com.nightcoder.health.booklibrary.Supports.ViewSupports;

import static com.nightcoder.health.booklibrary.Literals.Database.KEY_ADDRESS;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_BOOK_NAME;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_COPY;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_EMAIL;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_GIFT;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_PIN_CODE;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_TIMESTAMP;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_CATEGORY;

public class PurchaseActivity extends AppCompatActivity {

    private EditText count, name, email, pincode, address;
    public static Book book;
    private CheckBox checkBox;
    private BooksDataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        helper = new BooksDataHelper(this);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        ((TextView) findViewById(R.id.price)).setText("Rs " + book.getPrice());
        ((TextView) findViewById(R.id.author)).setText(book.getAuthor());
        ((TextView) findViewById(R.id.category)).setText(book.getCategory());
        ((TextView) findViewById(R.id.title)).setText(book.getBookName());

        count = findViewById(R.id.count);
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pincode = findViewById(R.id.pincode);
        address = findViewById(R.id.address);
        checkBox = findViewById(R.id.gift);

        count.setHint(book.getStock() + " available");
        email.setText(Memory.getString(this, KEY_USER_CATEGORY, "none"));

        findViewById(R.id.buy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
            }
        });
    }

    private void addOrder() {
        if (!count.getText().toString().trim().isEmpty())
            if (Integer.parseInt(count.getText().toString()) > 0) {
                if (Integer.parseInt(count.getText().toString()) <= book.getStock()) {
                    if (!name.getText().toString().trim().isEmpty()) {
                        if (!address.getText().toString().trim().isEmpty()) {
                            if (!pincode.getText().toString().trim().isEmpty()) {
                                OrderDatabase database = new OrderDatabase(this);

                                ContentValues values = new ContentValues();
                                values.put(KEY_ADDRESS, address.getText().toString().trim());
                                values.put(KEY_BOOK_NAME, book.getBookName());
                                values.put(KEY_EMAIL, email.getText().toString().trim());
                                values.put(KEY_PIN_CODE, pincode.getText().toString().trim());
                                values.put(KEY_COPY, Integer.parseInt(count.getText().toString()));
                                values.put(KEY_TIMESTAMP, System.currentTimeMillis());
                                values.put(KEY_GIFT, checkBox.isChecked());

                                boolean check = database.addOrder(values);
                                if (check) {
                                    helper.updateStock(book.getBookName(),
                                            book.getStock() -
                                                    Integer.parseInt(count.getText().toString()));
                                    Dialog dialog = ViewSupports.materialDialog(
                                            this,
                                            "Book Order",
                                            "Your book has been " +
                                                    "successfully added to order list");
                                    dialog.setOnCancelListener(
                                            new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialogInterface) {
                                                    finish();
                                                }
                                            });
                                } else {
                                    ViewSupports.materialDialog(
                                            this,
                                            "Order failed",
                                            "Your book order failed");
                                }
                            } else {
                                Toast.makeText(this,
                                        "Provide address",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this,
                                    "Provide address",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,
                                "Provide name",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ViewSupports.materialDialog(this,
                            "Order Failed",
                            "Only " +
                                    book.getStock()
                                    + " left reduce your copy, ow wait for few days");
                }
            } else {
                Toast.makeText(this,
                        "Provide copy count",
                        Toast.LENGTH_SHORT).show();
            }
    }
}