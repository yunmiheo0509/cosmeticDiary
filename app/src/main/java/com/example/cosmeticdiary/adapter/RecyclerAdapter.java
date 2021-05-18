package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.SearchCosmeticModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private Context c;
    private List<SearchCosmeticModel> dataList;

    public RecyclerAdapter(Context c, List<SearchCosmeticModel> dataList) {
        this.c = c;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.item_search_cosmetic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        holder.name.setText(dataList.get(position).getName());
        holder.brand.setText("" + dataList.get(position).getBrand());
//        holder.img.setText("" + dataList.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView brand;
        TextView img;
//        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_cosmetic_name);
            brand = (TextView)itemView.findViewById(R.id.tv_brand);
//            img = (TextView)itemView.findViewById(R.id.iv_cosmetic);

        }
    }
}
