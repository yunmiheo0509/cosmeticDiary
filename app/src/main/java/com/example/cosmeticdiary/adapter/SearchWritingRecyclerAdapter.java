package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.SearchWritingModel;

import java.util.List;

public class SearchWritingRecyclerAdapter extends RecyclerView.Adapter<SearchWritingRecyclerAdapter.ItemViewHolder> {
    private Context c;
    private List<SearchWritingModel> dataList;

    public SearchWritingRecyclerAdapter(Context c, List<SearchWritingModel> dataList) {
        this.c = c;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SearchWritingRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.item_search_writing, parent, false);
        return new SearchWritingRecyclerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
//        holder.img.setImageResource(searchWritingModel.get(position).getImg());
        holder.date.setText(dataList.get(position).getDate());
        holder.cosmetic.setText(dataList.get(position).getCosmetic());
        holder.brand.setText("("+ dataList.get(position).getBrand() +")");
        holder.condition.setText(dataList.get(position).getCondition());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return dataList.size();
    }

    void addItem(SearchWritingModel data) {
        // 외부에서 item을 추가시킬 함수입니다.
        dataList.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView date;
        private TextView cosmetic;
        private TextView brand;
        private TextView condition;

        ItemViewHolder(View itemView) {
            super(itemView);

//            img = itemView.findViewById(R.id.iv_cosmetic);
            date = itemView.findViewById(R.id.tv_date);
            cosmetic = itemView.findViewById(R.id.tv_cosmetic_name);
            brand = itemView.findViewById(R.id.tv_brand);
            condition = itemView.findViewById(R.id.tv_condition);
        }

        void onBind(SearchWritingModel data) {
//            img.setImageResource(data.getImg());
            date.setText(data.getDate());
            cosmetic.setText(data.getCosmetic());
            brand.setText(data.getBrand());
            condition.setText(data.getCondition());
        }
    }
}
