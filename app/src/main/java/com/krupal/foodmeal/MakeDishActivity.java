package com.krupal.foodmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    ArrayList<String> fruits_list, breads_list, dips_list, drinks_list, non_veg_list, vegetable_list, choosen_items;

    @BindView(R.id.find_dishes_BT)
    Button bt_find_dishes;

    @BindView(R.id.breads_item_RV)
    RecyclerView rv_breads_tiem;

    @BindView(R.id.breads_title_TV)
    TextView tv_breads_title;

    @BindView(R.id.dpis_item_RV)
    RecyclerView rv_dips_tiem;

    @BindView(R.id.dips_title_TV)
    TextView tv_dips_title;

    @BindView(R.id.drinks_item_RV)
    RecyclerView rv_drinks_tiem;

    @BindView(R.id.drinks_title_TV)
    TextView tv_drinks_title;

    @BindView(R.id.fruits_item_RV)
    RecyclerView rv_fruits_tiem;

    @BindView(R.id.fruits_title_TV)
    TextView tv_fruits_title;

    @BindView(R.id.nonveg_item_RV)
    RecyclerView rv_nonveg_tiem;

    @BindView(R.id.nonveg_title_TV)
    TextView tv_nonveg_title;

    @BindView(R.id.vegetable_item_RV)
    RecyclerView rv_vegetable_tiem;

    @BindView(R.id.vegetable_title_TV)
    TextView tv_vegetable_title;


    ProgressDialog pd;
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_dish);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        breads_list = new ArrayList<>();
        dips_list = new ArrayList<>();
        drinks_list = new ArrayList<>();
        fruits_list = new ArrayList<>();
        non_veg_list = new ArrayList<>();
        vegetable_list = new ArrayList<>();
        choosen_items = new ArrayList<>();


        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        pd = new ProgressDialog(MakeDishActivity.this);
        new getDataFromDatabase().execute();

        bt_find_dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ResutOfRecipes.class);
                Bundle args = new Bundle();
                args.putSerializable("choosenIngredients",choosen_items);
                i.putExtra("selected ingredients",args );
                startActivity(i);
            }
        });
    }


    private void setAdapterData(String title, ArrayList<String> ingredients_list, int adapter_number) {
        switch (adapter_number) {
            case 1: {
                tv_breads_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_breads_tiem.setLayoutManager(linearLayoutManager);
                rv_breads_tiem.setAdapter(ingrediantsAdapter);
                rv_breads_tiem.setNestedScrollingEnabled(false);
                break;
            }
            case 2: {
                tv_dips_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_dips_tiem.setLayoutManager(linearLayoutManager);
                rv_dips_tiem.setAdapter(ingrediantsAdapter);
                rv_dips_tiem.setNestedScrollingEnabled(false);
                break;
            }
            case 3: {
                tv_drinks_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_drinks_tiem.setLayoutManager(linearLayoutManager);
                rv_drinks_tiem.setAdapter(ingrediantsAdapter);
                rv_drinks_tiem.setNestedScrollingEnabled(false);
                break;
            }
            case 4: {
                tv_fruits_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_fruits_tiem.setLayoutManager(linearLayoutManager);
                rv_fruits_tiem.setAdapter(ingrediantsAdapter);
                rv_fruits_tiem.setNestedScrollingEnabled(false);
                break;
            }
            case 5: {
                tv_nonveg_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_nonveg_tiem.setLayoutManager(linearLayoutManager);
                rv_nonveg_tiem.setAdapter(ingrediantsAdapter);
                rv_nonveg_tiem.setNestedScrollingEnabled(false);
                break;
            }
            case 6: {
                tv_vegetable_title.setText(title);
                IngrediantsAdapter ingrediantsAdapter = new IngrediantsAdapter(getApplicationContext(), ingredients_list,choosen_items);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_vegetable_tiem.setLayoutManager(linearLayoutManager);
                rv_vegetable_tiem.setAdapter(ingrediantsAdapter);
                rv_vegetable_tiem.setNestedScrollingEnabled(false);
                break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    class getDataFromDatabase extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            pd.setMessage("Getting Your Ingredients....");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            DocumentReference docref = firestore.collection("Ingredients").document("breads");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getId() + " => " + document.getData());
                                for (int i = 1; i <= Objects.requireNonNull(document.getData()).size(); i++) {
                                    breads_list.add(document.getString(String.valueOf(i)));
                                }
//                                System.out.println("arraylist " + fruits_list.toString());
                                setAdapterData(document.getId(), breads_list, 1);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });

            docref = firestore.collection("Ingredients").document("dips");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getId() + " => " + document.getData());
                                for (int i = 1; i <= Objects.requireNonNull(document.getData()).size(); i++) {
                                    dips_list.add(document.getString(String.valueOf(i)));
                                }
                                //                                System.out.println("arraylist " + fruits_list.toString());
                                setAdapterData(document.getId(), dips_list, 2);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });

            docref = firestore.collection("Ingredients").document("drinks");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getId() + " => " + document.getData());
                                for (int i = 1; i <= Objects.requireNonNull(document.getData()).size(); i++) {
                                    drinks_list.add(document.getString(String.valueOf(i)));
                                }
                                                                System.out.println("arraylist " + drinks_list.toString());
                                setAdapterData(document.getId(), drinks_list, 3);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });
            docref = firestore.collection("Ingredients").document("fruits");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getId() + " => " + document.getData());
                                for (int i = 1; i <= Objects.requireNonNull(document.getData()).size(); i++) {
                                    fruits_list.add(document.getString(String.valueOf(i)));
                                }
                                //                                System.out.println("arraylist " + fruits_list.toString());
                                setAdapterData(document.getId(), fruits_list, 4);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });
            docref = firestore.collection("Ingredients").document("non veges");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println("document"+document.getData() );
                                System.out.println(document.getId() + " => " + document.getData());
                                for (int i = 1; i <= Objects.requireNonNull(document.getData()).size(); i++) {
                                    non_veg_list.add(document.getString(String.valueOf(i)));
                                }
                                //                                System.out.println("arraylist " + fruits_list.toString());
                                setAdapterData(document.getId(), non_veg_list, 5);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });

            docref = firestore.collection("Ingredients").document("vegetables");
            docref.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                System.out.println(document.getId() + " => " + document.getData());
                                System.out.println("document"+document.getData() );
                                for (int i = 1; i <= (document.getData()).size(); i++) {
                                    vegetable_list.add(document.getString(String.valueOf(i)));
                                }
                                //                                System.out.println("arraylist " + fruits_list.toString());
                                setAdapterData(document.getId(), vegetable_list, 6);
                                pd.dismiss();
                            } else {
                                System.out.println("Error getting documents.");
                            }
                        }
                    });

            return null;
        }
    }

}



