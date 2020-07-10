package com.nightcoder.health.booklibrary.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.PurchaseActivity;
import com.nightcoder.health.booklibrary.R;
import com.nightcoder.health.booklibrary.Supports.ViewSupports;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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
            holder.buy.setVisibility(book.getStock() <= 0 ? View.GONE : View.VISIBLE);
        }
        holder.outStock.setVisibility(book.getStock() <= 0 ? View.VISIBLE : View.GONE);

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        holder.container.setVisibility(View.VISIBLE);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = ViewSupports.materialDialogBottomSheet(
                        mContext, R.layout.delete_confirm);
                dialog.findViewById(R.id.cancel_button)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                dialog.findViewById(R.id.remove_button)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                BooksDataHelper helper = new BooksDataHelper(mContext);
                                helper.removeBook(book.getBookName());
                                books.remove(position);
                                notifyItemRemoved(position);
                            }
                        });

            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PurchaseActivity.book = book;
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                        new Pair<View, String>(holder.container, "itemTransition"));
                mContext.startActivity(new Intent(mContext, PurchaseActivity.class), options.toBundle());
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
        private RelativeLayout container;
        private TextView outStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            buy = itemView.findViewById(R.id.buy_button);
            remove = itemView.findViewById(R.id.remove_button);
            container = itemView.findViewById(R.id.container);
            outStock = itemView.findViewById(R.id.out_stock);
            container.setVisibility(View.GONE);
        }
    }
}
