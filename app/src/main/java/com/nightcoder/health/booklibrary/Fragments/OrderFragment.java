package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
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

import com.nightcoder.health.booklibrary.Adapters.OrderAdapter;
import com.nightcoder.health.booklibrary.Database.OrderDatabase;
import com.nightcoder.health.booklibrary.Models.Order;
import com.nightcoder.health.booklibrary.R;
import com.nightcoder.health.booklibrary.Supports.Memory;

import java.util.ArrayList;

import static com.nightcoder.health.booklibrary.Literals.Database.ADMIN_USERNAME;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_CATEGORY;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context mContext;
    private TextView noOrder;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh);
        noOrder = view.findViewById(R.id.no_order);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerView();
            }
        });

        return view;
    }

    private void setRecyclerView() {
        OrderDatabase database = new OrderDatabase(mContext);
        ArrayList<Order> orders;
        if (Memory.getString(mContext, KEY_USER_CATEGORY, "none").equals(ADMIN_USERNAME)) {
            orders = database.getAllOrders();
        } else {
            orders = database.getOrders(Memory.getString(mContext, KEY_USER_CATEGORY, "none"));
        }

        if (orders.isEmpty()) {
            noOrder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            OrderAdapter adapter = new OrderAdapter(mContext, orders);
            recyclerView.setAdapter(adapter);
            noOrder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }
}