package com.krupal.foodmeal.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.krupal.foodmeal.Authentication;
import com.krupal.foodmeal.HomeActivity;
import com.krupal.foodmeal.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class login_Fragment extends Fragment {
    Authentication parent_activity;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.email_ET)
    EditText email_ET;

    @BindView(R.id.password_ET)
    EditText password_ET;

    @BindView(R.id.login_BT)
    TextView login_BT;

    @BindView(R.id.create_acc_TV)
    TextView create_acc_TV;



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
        firebaseAuth = FirebaseAuth.getInstance();



        create_acc_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent_activity.replaceFragment(new Registration_Fragment(parent_activity));
            }
        });
        login_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("login button pressed");
                loginwithEmailAndPassword(email_ET.getText().toString().trim(),password_ET.getText().toString().trim());
            }
        });

        return view;
    }

    private void loginwithEmailAndPassword(String email, String password) {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    showToast("Login successful with user id : "+ authResult.getUser().getUid());
                    Intent i = new Intent(parent_activity, HomeActivity.class);
                    startActivity(i);
                    parent_activity.finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showToast("something went wrong! please try again");
                }
            });
    }
    private void showToast(String message) {
        Toast.makeText(parent_activity, message, Toast.LENGTH_LONG).show();
    }
}