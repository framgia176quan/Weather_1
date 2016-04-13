package com.example.lethuy.weathersimpleapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lethuy.weathersimpleapp.R;
import com.example.lethuy.weathersimpleapp.database.CityDatabase;
import com.example.lethuy.weathersimpleapp.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Nguyen Manh Quan on 11/04/2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>{

    private DatabaseHelper db;
    private ArrayList<CityDatabase> arrayList;

    public CityAdapter(ArrayList<CityDatabase> arrayList, Context context) {
        this.arrayList = arrayList;
        this.db = new DatabaseHelper(context);
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private Button btnDeleteCity;

        public CityViewHolder(View itemView) {
            super(itemView);
            tvName= (TextView) itemView.findViewById(R.id.tv_item_name_city);
            btnDeleteCity = (Button) itemView.findViewById(R.id.btn_item_delete);
            btnDeleteCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeCity(getAdapterPosition());
                }
            });

        }


    }


    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new CityViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        CityDatabase cityDatabase = arrayList.get(position);
        holder.tvName.setText(cityDatabase.getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void addCity(CityDatabase cityDatabase) {
        arrayList.add(cityDatabase);

    }

    public void removeCity(int position) {

        db.deleteCity(arrayList.get(position));
        Log.d("Pos", "" + arrayList.get(position));
        arrayList.remove(position);
        notifyDataSetChanged();
    }


}
