package com.nightcoder.health.booklibrary.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Book> books;
    private boolean isAdmin;

    public BookAdapter(Context mContext, ArrayList<Book> books, boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.mContext = mContext;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_book, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Book book = books.get(position);

        holder.title.setText(book.getBookName());
        holder.author.setText(book.getAuthor());
        holder.category.setText(book.getCategory());
        holder.price.setText("Rs " + book.getPrice());
        if (isAdmin) {
            holder.buy.setVisibility(View.GONE);
            holder.remove.setVisibility(View.VISIBLE);
        } else {
            holder.remove.setVisibility(View.GONE);
            holder.buy.setVisibility(View.VISIBLE);
        }

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BooksDataHelper helper = new BooksDataHelper(mContext);
                helper.removeBook(book.getBookName());
                books.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author, price, category;
        private Button buy, remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            buy = itemView.findViewById(R.id.buy_button);
            remove = itemView.findViewById(R.id.remove_button);
        }
    }
}
