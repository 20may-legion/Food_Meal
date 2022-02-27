package com.krupal.foodmeal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.krupal.foodmeal.MakeDishActivity;
import com.krupal.foodmeal.R;

import java.util.Objects;

import butterknife.BindView;

public class HomeFragment extends Fragment {
    @BindView(R.id.make_dish_BT)
    android.widget.Button make_dish;

    @BindView(R.id.explore_recipes_BT)
    android.widget.Button explore_reciepe;


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        butterknife.ButterKnife.bind(this, root);

        make_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("make dish pressed");
                Intent i = new Intent(getActivity(), MakeDishActivity.class);
                startActivity(i);
            }
        });
        explore_reciepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("explore reciepe pressed");
            }
        });





        return root;
    }
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}