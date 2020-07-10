package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
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
import com.nightcoder.health.booklibrary.Supports.Memory;

import java.util.ArrayList;

import static com.nightcoder.health.booklibrary.Literals.Database.ADMIN_USERNAME;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_CATEGORY;

public class BookFragment extends Fragment {

    private Context mContext;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView noBooks;
    private ImageButton addButton;

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
        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, AddBooksActivity.class));
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerView();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideAddButton();
                } else {
                    showAddButton();
                }
            }
        });
        showAddButton();
        return view;
    }

    private void hideAddButton() {
        if (Memory.getString(mContext, KEY_USER_CATEGORY, "none").equals(ADMIN_USERNAME)) {
            addButton.setVisibility(View.GONE);
            addButton.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_down));
        }
    }

    private void showAddButton() {
        if (Memory.getString(mContext, KEY_USER_CATEGORY, "none").equals(ADMIN_USERNAME)) {
            addButton.setVisibility(View.VISIBLE);
            addButton.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_up));
        }
    }

    private void setRecyclerView() {
        BooksDataHelper helper = new BooksDataHelper(mContext);
        ArrayList<Book> books = helper.getAllBooks();
        if (!books.isEmpty()) {
            BookAdapter adapter;
            if (Memory.getString(mContext, KEY_USER_CATEGORY, "none").equals(ADMIN_USERNAME)) {
                adapter = new BookAdapter(mContext, books, true);
            } else {
                adapter = new BookAdapter(mContext, books, false);
            }
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            noBooks.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noBooks.setVisibility(View.VISIBLE);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }
}