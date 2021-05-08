package com.example.cosmeticdiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchWritingData;

import java.util.ArrayList;

public class SearchWritingAdapter extends RecyclerView.Adapter<SearchWritingAdapter.ItemViewHolder> {
    private ArrayList<SearchWritingData> searchWritingData = new ArrayList<>();

    public SearchWritingAdapter(ArrayList<SearchWritingData> writingArray) {
        this.searchWritingData = writingArray;
    }

    @NonNull
    @Override
    public SearchWritingAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_writing, parent, false);
        return new SearchWritingAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchWritingAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
        holder.img.setImageResource(searchWritingData.get(position).getImg());
        holder.date.setText(searchWritingData.get(position).getDate());
        holder.name.setText(searchWritingData.get(position).getName());
        holder.brand.setText("("+ searchWritingData.get(position).getBrand() +")");
        holder.condition.setText(searchWritingData.get(position).getCondition());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return searchWritingData.size();
    }

    void addItem(SearchWritingData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        searchWritingData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView date;
        private TextView name;
        private TextView brand;
        private TextView condition;

        ItemViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iv_cosmetic);
            date = itemView.findViewById(R.id.tv_date);
            name = itemView.findViewById(R.id.tv_cosmetic_name);
            brand = itemView.findViewById(R.id.tv_brand);
            condition = itemView.findViewById(R.id.tv_condition);
        }

        void onBind(SearchWritingData data) {
            img.setImageResource(data.getImg());
            date.setText(data.getDate());
            name.setText(data.getName());
            brand.setText(data.getBrand());
            condition.setText(data.getCondition());
        }
    }
}
