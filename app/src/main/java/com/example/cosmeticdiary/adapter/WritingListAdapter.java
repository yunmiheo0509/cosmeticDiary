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
    private RecyclerViewClickListener listener;

    public WritingListAdapter(ArrayList<WritingListData> writingListArray, RecyclerViewClickListener listener) {
        this.writingListData = writingListArray;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WritingListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킴
        // return 인자는 ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_writing_list, parent, false);
        return new WritingListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WritingListAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수
//        holder.onBind(listData.get(position));
        holder.img.setImageResource(writingListData.get(position).getImg());
        holder.name.setText(writingListData.get(position).getName());
        holder.condition.setText(writingListData.get(position).getCondition());
        holder.satisfy.setText(writingListData.get(position).getSatisfy());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return writingListData.size();
    }

    void addItem(WritingListData data) {
        // 외부에서 item을 추가시킬 함수
        writingListData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder
    // 여기서 subView를 setting
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

            itemView.setOnClickListener(this);
        }

        void onBind(WritingListData data) {
            img.setImageResource(data.getImg());
            name.setText(data.getName());
            condition.setText(data.getCondition());
            satisfy.setText(data.getSatisfy());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
