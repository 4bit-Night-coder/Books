package com.nightcoder.health.booklibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Supports.ViewSupports;

public class AddBooksActivity extends AppCompatActivity {
    private EditText title, cat, org, stock, price, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        init();
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
    }

    private void init() {
        title = findViewById(R.id.title);
        cat = findViewById(R.id.category);
        org = findViewById(R.id.org);
        stock = findViewById(R.id.stock);
        price = findViewById(R.id.price);
        author = findViewById(R.id.author);
    }

    private void addBook() {
        if (!title.getText().toString().trim().isEmpty()) {
            if (!org.getText().toString().trim().isEmpty()) {
                if (!author.getText().toString().trim().isEmpty()) {
                    if (!cat.getText().toString().trim().isEmpty()) {
                        if (!price.getText().toString().isEmpty()) {
                            if (!stock.getText().toString().isEmpty()) {
                                BooksDataHelper helper = new BooksDataHelper(
                                        AddBooksActivity.this);
                                if (helper.getBook(title.getText().toString().trim()) == null) {
                                    boolean result = helper.addBook(
                                            title.getText().toString().trim(),
                                            Integer.parseInt(price.getText().toString()),
                                            Integer.parseInt(stock.getText().toString()),
                                            cat.getText().toString().trim(),
                                            author.getText().toString().trim(),
                                            org.getText().toString().trim());
                                    if (result) {
                                        ViewSupports.materialDialog(
                                                this,
                                                "Publish Book",
                                                "Your book has been successfully published");
                                    } else {
                                        ViewSupports.materialDialog(
                                                this,
                                                "Publication failed",
                                                "Your book Publication failed");
                                    }
                                } else {
                                    ViewSupports.materialDialog(
                                            this,
                                            "Publication rejected",
                                            "This Book already exist \n" +
                                                    "try another book or name");
                                }
                            } else {
                                Toast.makeText(this, "Add stock count",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Add price",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Add a book category",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Add book author name",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Add organization",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "provide book name",
                    Toast.LENGTH_SHORT).show();
        }
    }
}