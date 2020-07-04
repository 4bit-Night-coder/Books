package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nightcoder.health.booklibrary.Listeners.FragmentChangeListener;
import com.nightcoder.health.booklibrary.R;

public class SignFragment extends Fragment {

    private FragmentChangeListener listener;
    private EditText email, pass;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (FragmentChangeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);

        view.findViewById(R.id.sign_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty()
                        && !pass.getText().toString().isEmpty()) {
                    listener.onSignIn(email.getText().toString(), pass.getText().toString());
                }
            }
        });
        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickRegister();
            }
        });

        return view;
    }
}