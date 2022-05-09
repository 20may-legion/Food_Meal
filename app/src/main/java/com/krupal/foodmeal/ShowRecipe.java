package com.krupal.foodmeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowRecipe extends AppCompatActivity {

    @BindView(R.id.dishname_TV)
    TextView TV_dishname;

    @BindView(R.id.stepNo_TV)
    TextView TV_stepno;


    @BindView(R.id.dish_recipe_TV)
    TextView TV_dish_recipe;

    @BindView(R.id.previous_BT)
    MaterialButton BT_previous;

    @BindView(R.id.next_bt)
    MaterialButton BT_next;

    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> Recipe;

    int StepNo = 0;
    String Dish_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        Dish_name = getIntent().getStringExtra("Dishname");
        TV_dishname.setText(Dish_name);
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Recipe = new ArrayList<>();
        new getDatafromDatabase().execute();


        BT_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StepNo < Recipe.size() - 1) {
                    StepNo++;
                    showData(Recipe, StepNo);
                }
            }
        });

        BT_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StepNo > 0) {
                    StepNo--;
                    showData(Recipe, StepNo);
                }

            }
        });


    }

    private class getDatafromDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            DocumentReference docref = firestore.collection("Recipe").document(Dish_name);


            docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    System.out.println("value of snapshot" + value.getData());
                    if (value.exists()) {
                        for (Object item : value.getData().values())
                            Recipe.add(item.toString());

                        showData(Recipe, StepNo);
                    } else {
                        DocumentReference docref = firestore.collection("Recipe").document(" " + Dish_name);
                        docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                System.out.println("value of snapshot" + value.getData());
                                if (value.exists()) {
                                    for (Object item : value.getData().values())
                                        Recipe.add(item.toString());

                                    showData(Recipe, StepNo);
                                } else {
                                    DocumentReference docref = firestore.collection("Recipe").document(Dish_name + " ");
                                    docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            System.out.println("value of snapshot" + value.getData());
                                            if (value.exists()) {
                                                for (Object item : value.getData().values())
                                                    Recipe.add(item.toString());

                                                showData(Recipe, StepNo);
                                            } else {
                                                DocumentReference docref = firestore.collection("Recipe").document(" "+Dish_name+" ");


                                                docref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        System.out.println("value of snapshot" + value.getData());
                                                        if (value.exists()) {
                                                            for (Object item : value.getData().values())
                                                                Recipe.add(item.toString());

                                                            showData(Recipe, StepNo);
                                                        }
                                                        else{

                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });

            return null;
        }
    }

    private void showData(ArrayList<String> recipe, int stepNo) {
        TV_dish_recipe.setText(recipe.get(stepNo));
        TV_stepno.setText(String.valueOf(stepNo + 1));


    }
}