package com.krupal.foodmeal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.krupal.foodmeal.R;
import com.krupal.foodmeal.ShowRecipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
    ArrayList<String> Dishes_available;
    private Context context;

    public DishesAdapter(ArrayList<String> dishes_available, Context context) {
        this.Dishes_available = dishes_available;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishes_adapter,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdapter.ViewHolder holder, int position) {
            holder.tv_dish_name.setText(Dishes_available.get(position));
            holder.CV_dishCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), ShowRecipe.class);
                    i.putExtra("Dishname", Dishes_available.get(position));
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return Dishes_available.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_name_TV)
        TextView tv_dish_name;

        @BindView(R.id.dish_card_CV)
        CardView CV_dishCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}