package com.krupal.foodmeal.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.krupal.foodmeal.Authentication;
import com.krupal.foodmeal.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class login_Fragment extends Fragment {
    Authentication parent_activity;


    @BindView(R.id.email_ET)
    EditText email;

    @BindView(R.id.password_ET)
    EditText password;

    @BindView(R.id.login_BT)
    TextView login;

      @BindView(R.id.create_acc_TV)
    TextView create_acc;



    public login_Fragment(Authentication activity) {
     this.parent_activity= activity;
         }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parent_activity.replaceFragment(new Registration_Fragment(parent_activity));
            }
        });




        return view;
    }
}