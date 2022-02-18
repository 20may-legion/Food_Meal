package com.krupal.foodmeal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.krupal.foodmeal.Authentication;
import com.krupal.foodmeal.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Registration_Fragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    Authentication parent_activity;
    FirebaseFirestore firestore;
    @BindView(R.id.have_acc_TV)
    TextView have_acc;

    @BindView(R.id.fname_ET)
    TextView fname_ET;

    @BindView(R.id.lname_ET)
    TextView lname_ET;

    @BindView(R.id.mobile_ET)
    TextView mobile_ET;

    @BindView(R.id.email_ET)
    TextView email_ET;

    @BindView(R.id.password_ET)
    TextView password_ET;

    @BindView(R.id.register_BT)
    TextView register_BT;

    public Registration_Fragment(Authentication activity) {
        // Required empty public constructor
        this.parent_activity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        FirebaseApp.initializeApp(parent_activity.getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent_activity.replaceFragment(new login_Fragment(parent_activity));

            }
        });

        register_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserwithEmailandPassword(email_ET.getText().toString().trim(),password_ET.getText().toString().trim());
            }
        });


        return view;
    }

    private void registerUserwithEmailandPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String,Object> user = new HashMap<>();
                user.put("first name", fname_ET.getText().toString().trim());
                user.put("last name", lname_ET.getText().toString().trim());
                user.put("email id", email_ET.getText().toString().trim());
                user.put("mobile no", mobile_ET.getText().toString().trim());
                user.put("password", password_ET.getText().toString().trim());
                String uid = Objects.requireNonNull(authResult.getUser()).getUid();

                firestore.collection("User").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("user registration successful");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(parent_activity, message, Toast.LENGTH_LONG).show();

    }
}