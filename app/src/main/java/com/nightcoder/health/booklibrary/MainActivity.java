package com.nightcoder.health.booklibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nightcoder.health.booklibrary.Fragments.BookFragment;
import com.nightcoder.health.booklibrary.Fragments.OrderFragment;
import com.nightcoder.health.booklibrary.Supports.Memory;

import java.util.ArrayList;

import static com.nightcoder.health.booklibrary.Literals.Database.ADMIN_USERNAME;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_CATEGORY;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_LOG_IN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ((TabLayout) findViewById(R.id.tab_lay)).setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookFragment(), "Books");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Log.d("User", Memory.getString(this, KEY_USER_CATEGORY, "none"));
        if (Memory.getString(this, KEY_USER_CATEGORY, "none").equals(ADMIN_USERNAME))
            adapter.addFragment(new OrderFragment(), "Orders");
        else {
            adapter.addFragment(new OrderFragment(), "My Orders");
            adapter.addFragment(new OrderFragment(), "CART");
        }
        viewPager.setAdapter(adapter);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            Memory.putString(this, KEY_USER_CATEGORY, "none");
            Memory.putBool(this, KEY_USER_LOG_IN, false);
            startActivity(new Intent(MainActivity.this, SignActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}