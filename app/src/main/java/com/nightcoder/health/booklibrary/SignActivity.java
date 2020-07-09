package com.nightcoder.health.booklibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.nightcoder.health.booklibrary.Database.UserDataHelper;
import com.nightcoder.health.booklibrary.Fragments.RegisterFragment;
import com.nightcoder.health.booklibrary.Fragments.SignFragment;
import com.nightcoder.health.booklibrary.Listeners.FragmentChangeListener;
import com.nightcoder.health.booklibrary.Supports.Memory;
import com.nightcoder.health.booklibrary.Supports.ViewSupports;

import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_CATEGORY;
import static com.nightcoder.health.booklibrary.Literals.Database.KEY_USER_LOG_IN;

public class SignActivity extends AppCompatActivity implements FragmentChangeListener {

    private static final String SIGN_FRAGMENT_TAG = "SIGN_FRAGMENT";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mContext = SignActivity.this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame, new SignFragment(), SIGN_FRAGMENT_TAG)
                .commit();
    }

    private void openRegisterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame, new RegisterFragment())
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .addToBackStack(SIGN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onClickRegister() {
        openRegisterFragment();
    }

    @Override
    public void onSignIn(String email, String pass) {
        UserDataHelper helper = new UserDataHelper(mContext);
        String result = helper.signUser(email, pass);
        if (result.equals("Success")) {
            startActivity(new Intent(mContext, MainActivity.class));
            Memory.putString(mContext, KEY_USER_CATEGORY, email);
            Memory.putBool(mContext, KEY_USER_LOG_IN, true);
            finish();
        } else {
            ViewSupports.materialDialog(mContext, "Log In", result);
        }
    }

    @Override
    public void onRegister(String name, String email, String pass) {
        UserDataHelper helper = new UserDataHelper(mContext);
        if (helper.addUser(name, email, pass)) {
            Dialog dialog = ViewSupports.materialDialog(mContext, "Register",
                    "Created new account from " + email);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    getSupportFragmentManager().popBackStack();
                }
            });

        } else {
            ViewSupports.materialDialog(mContext, "Register",
                    "Something went to wrong try again later");
        }

    }
}