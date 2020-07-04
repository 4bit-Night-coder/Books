package com.nightcoder.health.booklibrary.Listeners;

public interface FragmentChangeListener {
    void onClickRegister();
    void onSignIn(String email, String pass);
    void onRegister(String name, String email, String pass);
}
