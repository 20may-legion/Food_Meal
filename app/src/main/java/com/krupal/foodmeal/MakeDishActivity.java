package com.krupal.foodmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.krupal.foodmeal.Adapters.IngrediantsAdapter;
import com.krupal.foodmeal.Models.Ingredients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakeDishActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser current_user;


    @BindView(R.id.ingredients_item_RV)
    RecyclerView rv_ingredients_tiem;
    @BindView(R.id.ingredients_title_TV)
    TextView tv_ingredients_title;

    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_dish);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DocumentReference docref = firestore.collection("Ingredients").document("breads");
        docref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> ingredients_db = new ArrayList<>();
                            DocumentSnapshot document = task.getResult();
                            System.out.println(document.getId() + " => " + document.getData());
                            for(int i = 1; i<= Objects.requireNonNull(document.getData()).size(); i++){
                                ingredients_db.add(document.getString(String.valueOf(i)));
                            }
                            System.out.println("arraylist "+ ingredients_db.toString());
                       setAdapterData(document.getId(),ingredients_db);
                        } else {
                            System.out.println("Error getting documents.");
                        }
                    }
                });


    }

    private void setAdapterData(String title, ArrayList<String> ingredients_list) {
      tv_ingredients_title.setText(title);
        IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(),ingredients_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv_ingredients_tiem.setLayoutManager(linearLayoutManager);
        rv_ingredients_tiem.setAdapter(ingrediantsAdapter);
        rv_ingredients_tiem.setNestedScrollingEnabled(false);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}