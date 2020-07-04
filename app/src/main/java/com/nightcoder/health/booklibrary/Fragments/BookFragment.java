package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcoder.health.booklibrary.Adapters.BookAdapter;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.R;

import java.util.ArrayList;

public class BookFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setBookName("Oxford");
        book.setAuthor("Einsten");
        book.setCategory("English");
        book.setStock(15);
        book.setPrice(100);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);

        BookAdapter adapter = new BookAdapter(mContext, books);
        recyclerView.setAdapter(adapter);

        return view;
    }
}