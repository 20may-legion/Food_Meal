package com.krupal.foodmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.krupal.foodmeal.Adapters.DishesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResutOfRecipes extends AppCompatActivity {
    @BindView(R.id.dishes_found_RV)
    RecyclerView rv_dishes_found;
    @BindView(R.id.dish_counter_TV)
    TextView tv_dish_counter;

    ProgressDialog pd;
    FirebaseFirestore firestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser current_user;
    List<DocumentSnapshot> dishes;
    ArrayList<String> Dishes_available;
    ArrayList<String> Dishes_found;
    ArrayList<String> ingredients_available;
    ArrayList<String> choosen_items;
    JSONArray dishes_ingredient_master;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resut_of_recipes);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("selected ingredients");
        choosen_items = (ArrayList<String>) args.getSerializable("choosenIngredients");
        System.out.println("choosen " + choosen_items);
        dishes_ingredient_master = new JSONArray();
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        pd = new ProgressDialog(this);
        Dishes_available = new ArrayList<>();
        ingredients_available = new ArrayList<>();
        dishes = new ArrayList<>();


        new getDishesdata().execute();


    }

    private void setDishAdapter(ArrayList<String> dishes_available) {
        tv_dish_counter.setText(String.valueOf(dishes_available.size()));
        DishesAdapter dishesAdapter = new DishesAdapter(dishes_available, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_dishes_found.setLayoutManager(linearLayoutManager);
        rv_dishes_found.setAdapter(dishesAdapter);
        rv_dishes_found.setNestedScrollingEnabled(false);
        pd.dismiss();

    }

    class getDishesdata extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            pd.setMessage("Getting Data ....");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CollectionReference dishes_collection = firestore.collection("Dishes");

            dishes_collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                    System.out.println("collection" + value.toString());
                    dishes = value.getDocuments();
                    for (DocumentSnapshot dish : dishes) {
                        Dishes_available.add(dish.getId());

                    }
                    System.out.println(dishes.toString());
                    String name = dishes.get(0).getId();
                    System.out.println("dishes 123" + Dishes_available.size());


                    get_dishes_ingredient_master(Dishes_available);

                }
            });


            return null;
        }
    }

    private void get_dishes_ingredient_master(ArrayList<String> dishes_available) {
        Dishes_found = new ArrayList<>();
        for (int i = 0; i < dishes_available.size(); i++) {
            DocumentReference dishes_reference = firestore.collection("Dishes").document(dishes_available.get(i));
            int finalI = i;
            dishes_reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    System.out.println("getdata" + value.getData().values());
                    System.out.println("value size " + value.getData().size());

                    Map<String, Object> ingredientsMAP = value.getData();
                    Collection<Object> ingredientsCOL = ingredientsMAP.values();
                    ArrayList<String> ingredientsAL = new ArrayList<String>();
                    for (Object o : ingredientsMAP.values()) {
                        ingredientsAL.add(o.toString());
                    }
                    for (int j = 0; j < ingredientsAL.size(); j++) {
                        for (int k = 0; k < choosen_items.size(); k++) {
                            if (ingredientsAL.contains(choosen_items.get(k))) {
                                System.out.println(choosen_items.get(k) + "is contained by" + dishes_available.get(finalI));
                                if (!Dishes_found.contains(dishes_available.get(finalI)))
                                    Dishes_found.add(dishes_available.get(finalI));
                            }
                        }
                    }
                    setDishAdapter(Dishes_found);
                    System.out.println(dishes_ingredient_master.toString());
                }
            });
        }


    }

//    private class  fetchDishes extends AsyncTask<Void,Void,Void>{
//        public fetchDishes(JSONArray dishes_ingredient_master) {
//            this.dishes_ingredient_master = dishes_ingredient_master;
//        }
//
//        JSONArray dishes_ingredient_master;
//        @Override
//        protected Void doInBackground(Void... voids) {
//            for (int i = 0; i < choosen_items.size(); i++) {
//                for (int j = 0; j < dishes_ingredient_master.length(); j++) {
//                    try {
//                        JSONObject dish = dishes_ingredient_master.getJSONObject(j);
//                        String ingredients = dish.getString(String.valueOf(dish.keys()));
//                        System.out.println("ingredients" + ingredients);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//            return null;
//        }
//    }
}