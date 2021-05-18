package com.example.cosmeticdiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchCosmeticData;

import java.util.ArrayList;

public class SearchCosmeticAdapter extends RecyclerView.Adapter<SearchCosmeticAdapter.ItemViewHolder> {
    private ArrayList<SearchCosmeticData> searchCosmeticData = new ArrayList<>();

    public SearchCosmeticAdapter(ArrayList<SearchCosmeticData> cosmeticArray) {
        this.searchCosmeticData = cosmeticArray;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_cosmetic, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
//        holder.img.setImageResource(searchCosmeticData.get(position).getImg());
        holder.name.setText(searchCosmeticData.get(position).getName());
        holder.brand.setText(searchCosmeticData.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return searchCosmeticData.size();
    }

    void addItem(SearchCosmeticData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        searchCosmeticData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private TextView brand;

        ItemViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iv_cosmetic);
            name = itemView.findViewById(R.id.tv_cosmetic_name);
            brand = itemView.findViewById(R.id.tv_brand);
        }

        void onBind(SearchCosmeticData data) {
//            img.setImageResource(data.getImg());
            name.setText(data.getName());
            brand.setText(data.getBrand());
        }
    }
}
