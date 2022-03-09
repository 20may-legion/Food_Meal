package com.krupal.foodmeal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.krupal.foodmeal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngrediantsAdapter extends RecyclerView.Adapter<IngrediantsAdapter.ViewHolder> {
    private Context context;
    ArrayList<String> ingrediants_items;
    ArrayList<String> choosen_items;
    public IngrediantsAdapter(Context context, ArrayList<String> ingrediants_items,ArrayList<String> choosen_items) {
        this.context = context;
        this.ingrediants_items = ingrediants_items;
        this.choosen_items =choosen_items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediants_adapter,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredient_tv.setText(ingrediants_items.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    choosen_items.add(ingrediants_items.get(position));
                System.out.println("item listSSS"+choosen_items.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingrediants_items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ingredient_CB)
        AppCompatCheckBox checkBox;
        @BindView(R.id.ingredient_TV)
        AppCompatTextView ingredient_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
