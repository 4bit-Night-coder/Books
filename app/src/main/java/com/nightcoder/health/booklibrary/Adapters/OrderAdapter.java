package com.nightcoder.health.booklibrary.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcoder.health.booklibrary.Database.BooksDataHelper;
import com.nightcoder.health.booklibrary.Database.OrderDatabase;
import com.nightcoder.health.booklibrary.Models.Book;
import com.nightcoder.health.booklibrary.Models.Order;
import com.nightcoder.health.booklibrary.R;
import com.nightcoder.health.booklibrary.Supports.Time;
import com.nightcoder.health.booklibrary.Supports.ViewSupports;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Order> orders;

    public OrderAdapter(Context mContext, ArrayList<Order> orders) {
        this.mContext = mContext;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Order order = orders.get(position);

        holder.title.setText(order.getBookName());

        BooksDataHelper helper = new BooksDataHelper(mContext);
        Book book = helper.getBook(order.getBookName());
        Log.d("BookName", order.getBookName());
        if (book != null) {
            holder.author.setText(book.getAuthor());
            holder.category.setText(book.getCategory());
            holder.price.setText("Rs " + book.getPrice());
        } else {
            holder.price.setText("Book not available");
        }
        holder.username.setText(order.getUsername());
        holder.copy.setText(order.getCopy() + " nos");
        holder.email.setText(order.getEmail());
        holder.pinCode.setText(order.getPincode());
        holder.address.setText(order.getAddress());
        holder.gift.setText(order.isGift() ? "YES" : "NO");
        holder.time.setText(Time.getTimeChatList(order.getTime()));

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        holder.container.setVisibility(View.VISIBLE);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = ViewSupports.materialDialogBottomSheet(
                        mContext, R.layout.delete_confirm);
                dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        OrderDatabase database = new OrderDatabase(mContext);
                        boolean res = database.removeOrder(order.getTime());
                        if (res) {
                            orders.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author, price, category;
        private TextView address, pinCode, email, copy, gift, username, time;
        private Button remove;
        private RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            price = itemView.findViewById(R.id.price);
            category = itemView.findViewById(R.id.category);
            remove = itemView.findViewById(R.id.remove_button);
            container = itemView.findViewById(R.id.container);
            container.setVisibility(View.GONE);

            address = itemView.findViewById(R.id.address);
            pinCode = itemView.findViewById(R.id.pincode);
            email = itemView.findViewById(R.id.email);
            copy = itemView.findViewById(R.id.copy);
            gift = itemView.findViewById(R.id.gift);
            username = itemView.findViewById(R.id.username);
            time = itemView.findViewById(R.id.time);
        }
    }
}
