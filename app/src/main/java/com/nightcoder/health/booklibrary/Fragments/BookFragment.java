package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nightcoder.health.booklibrary.Adapters.BookAdapter;
import com.nightcoder.health.booklibrary.AddBooksActivity;
import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.R;

import java.util.ArrayList;

public class BookFragment extends Fragment {

    private Context mContext;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView noBooks;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh);
        noBooks = view.findViewById(R.id.text_no_books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, AddBooksActivity.class));
            }
        });
        setRecyclerView();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerView();
            }
        });
        return view;
    }

    private void setRecyclerView() {
        BooksDataHelper helper = new BooksDataHelper(mContext);
        ArrayList<Book> books = helper.getAllBooks();
        if (!books.isEmpty()) {
            BookAdapter adapter = new BookAdapter(mContext, books);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            noBooks.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noBooks.setVisibility(View.VISIBLE);
        }
        refreshLayout.setRefreshing(false);
    }
}