package com.krupal.foodmeal.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.krupal.foodmeal.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class NotificationsFragment extends Fragment {
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser current_user;
    private FirebaseAuth firebaseAuth;
    String fname, lname, mobile, email, password;

    @BindView(R.id.fname_ET)
    EditText ET_fname;

    @BindView(R.id.fname_TV)
    TextView TV_fname;

    @BindView(R.id.lname_ET)
    EditText ET_lname;

    @BindView(R.id.lname_TV)
    TextView TV_lname;

    @BindView(R.id.mobile_ET)
    EditText ET_mobile;

    @BindView(R.id.mobile_TV)
    TextView TV_mobile;

    @BindView(R.id.email_ET)
    EditText ET_email;

    @BindView(R.id.email_TV)
    TextView TV_email;

    @BindView(R.id.password_ET)
    EditText ET_password;

    @BindView(R.id.password_TV)
    TextView TV_password;

    @BindView(R.id.fullname_TV)
    TextView TV_fullname;

    @BindView(R.id.edit_IV)
    ImageView IV_edit;

    @BindView(R.id.confirmation_RL)
    RelativeLayout RL_confirmation;

    @BindView(R.id.discard_BT)
    android.widget.Button BT_discard;

    @BindView(R.id.apply_BT)
    android.widget.Button BT_apply;


    boolean edit_detials = false;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        butterknife.ButterKnife.bind(this, root);
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user = firebaseAuth.getCurrentUser();

        getdataFomDatabase(current_user.getUid());

        IV_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    TV_fname.setVisibility(View.VISIBLE);
//                    TV_lname.setVisibility(View.VISIBLE);
//                    TV_email.setVisibility(View.VISIBLE);
//                    TV_password.setVisibility(View.VISIBLE);
//                    TV_mobile.setVisibility(View.VISIBLE);
//
//                    ET_fname.setVisibility(View.GONE);
//                    ET_lname.setVisibility(View.GONE);
//                    ET_email.setVisibility(View.GONE);
//                    ET_password.setVisibility(View.GONE);
//                    ET_mobile.setVisibility(View.GONE);


                TV_fname.setVisibility(View.GONE);
                TV_lname.setVisibility(View.GONE);
                TV_email.setVisibility(View.GONE);
                TV_password.setVisibility(View.GONE);
                TV_mobile.setVisibility(View.GONE);
                IV_edit.setVisibility(View.GONE);

                ET_fname.setVisibility(View.VISIBLE);
                ET_lname.setVisibility(View.VISIBLE);
                ET_email.setVisibility(View.VISIBLE);
                ET_password.setVisibility(View.VISIBLE);
                ET_mobile.setVisibility(View.VISIBLE);

                RL_confirmation.setVisibility(View.VISIBLE);

            }
        });
        BT_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_fname.setVisibility(View.VISIBLE);
                TV_lname.setVisibility(View.VISIBLE);
                TV_email.setVisibility(View.VISIBLE);
                TV_password.setVisibility(View.VISIBLE);
                TV_mobile.setVisibility(View.VISIBLE);
                IV_edit.setVisibility(View.VISIBLE);

                ET_fname.setVisibility(View.GONE);
                ET_lname.setVisibility(View.GONE);
                ET_email.setVisibility(View.GONE);
                ET_password.setVisibility(View.GONE);
                ET_mobile.setVisibility(View.GONE);
                RL_confirmation.setVisibility(View.GONE);

            }
        });
        BT_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychanhes(ET_fname.getText().toString().toString(),ET_lname.getText().toString().toString(),ET_email.getText().toString().toString(),ET_mobile.getText().toString().toString(),ET_password.getText().toString().toString());
                IV_edit.setVisibility(View.VISIBLE);
                RL_confirmation.setVisibility(View.GONE);
                TV_fname.setVisibility(View.VISIBLE);
                TV_lname.setVisibility(View.VISIBLE);
                TV_email.setVisibility(View.VISIBLE);
                TV_password.setVisibility(View.VISIBLE);
                TV_mobile.setVisibility(View.VISIBLE);
                IV_edit.setVisibility(View.VISIBLE);

                ET_fname.setVisibility(View.GONE);
                ET_lname.setVisibility(View.GONE);
                ET_email.setVisibility(View.GONE);
                ET_password.setVisibility(View.GONE);
                ET_mobile.setVisibility(View.GONE);
                RL_confirmation.setVisibility(View.GONE);
            }
        });

        return root;
    }

    private void applychanhes(String fname,String lname,String email,String mobile,String password) {
        Map<String,Object> user = new HashMap<>();
        user.put("first name", fname);
        user.put("last name", lname);
        user.put("email id", email);
        user.put("mobile no", mobile);
        user.put("password", password);

        current_user.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("password updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error"+ e.getMessage());
            }
        });
        current_user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("email updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("error"+ e.getMessage());
            }
        });
        firestore.collection("User").document(current_user.getUid()).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showToast("details updated");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("something went wrong please try again");

            }
        });



    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    private void getdataFomDatabase(String uid) {

        DocumentReference userDocumet = firestore.collection("User").document(uid);
        userDocumet.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname = value.getData().get("first name").toString();
                lname = value.getData().get("last name").toString();
                email = value.getData().get("email id").toString();
                mobile = value.getData().get("mobile no").toString();
                password = value.getData().get("password").toString();

                TV_fname.setText(fname);
                TV_lname.setText(lname);
                TV_email.setText(email);
                TV_password.setText(password);
                TV_mobile.setText(mobile);

                ET_fname.setText(fname);
                ET_lname.setText(lname);
                ET_email.setText(email);
                ET_password.setText(password);
                ET_mobile.setText(mobile);

                TV_fullname.setText(fname + " " + lname);
            }
        });

    }
}