package com.example.cosmeticdiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.WritingListData;

import java.util.ArrayList;

public class WritingListAdapter extends RecyclerView.Adapter<WritingListAdapter.ItemViewHolder>{
    private ArrayList<WritingListData> writingListData = new ArrayList<>();

    public WritingListAdapter(ArrayList<WritingListData> writingListArray) {
    }

    @NonNull
    @Override
    public WritingListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_writing_list, parent, false);
        return new WritingListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WritingListAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
//        holder.onBind(listData.get(position));
        holder.img.setImageResource(writingListData.get(position).getImg());
        holder.name.setText(writingListData.get(position).getName());
        holder.condition.setText(writingListData.get(position).getCondition());
        holder.satisfy.setText(writingListData.get(position).getSatisfy());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return writingListData.size();
    }

    void addItem(WritingListData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        writingListData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView condition;
        private TextView satisfy;

        ItemViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iv_cosmetic);
            name = itemView.findViewById(R.id.tv_cosmetic_name);
            condition = itemView.findViewById(R.id.tv_insert_condition);
            satisfy = itemView.findViewById(R.id.tv_insert_satisfy);
        }

        void onBind(WritingListData data) {
            img.setImageResource(data.getImg());
            name.setText(data.getName());
            condition.setText(data.getCondition());
            satisfy.setText(data.getSatisfy());
        }
    }
}
