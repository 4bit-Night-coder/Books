package com.nightcoder.health.booklibrary.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nightcoder.health.booklibrary.Listeners.FragmentChangeListener;
import com.nightcoder.health.booklibrary.R;

public class RegisterFragment extends Fragment {

    private EditText email, pass, name;
    private Context mContext;
    private FragmentChangeListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (FragmentChangeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        name = view.findViewById(R.id.name);
        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty()
                        && !pass.getText().toString().isEmpty()
                        && !name.getText().toString().isEmpty()) {
                    if (!email.getText().toString().contains("@")) {
                        Toast.makeText(mContext, "Invalid email", Toast.LENGTH_SHORT).show();
                    } else {
                        listener.onRegister(name.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString());
                    }
                }
            }
        });

        return view;

    }
}