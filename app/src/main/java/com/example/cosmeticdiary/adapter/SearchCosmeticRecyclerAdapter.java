package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.activity.WritingActivity;
import com.example.cosmeticdiary.model.SearchCosmeticModel;

import java.util.ArrayList;
import java.util.List;

public class SearchCosmeticRecyclerAdapter extends RecyclerView.Adapter<SearchCosmeticRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<SearchCosmeticModel> dataList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    public SearchCosmeticRecyclerAdapter(Context c, List<SearchCosmeticModel> dataList) {
        this.context = c;
        this.dataList = dataList;
    }

    public void setData(List<SearchCosmeticModel> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchCosmeticRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_cosmetic, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCosmeticRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.brand.setText("" + dataList.get(position).getBrand());
//        holder.img.setText("" + dataList.get(position).getImg());

//        holder.itemView.setSelected(isItemSelected(position));
        if (isItemSelected(position)) {
            holder.itemView.setBackgroundColor(Color.GREEN);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        Log.d("리사이클러뷰 선택", "position = " + position);
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

            name = (TextView) itemView.findViewById(R.id.tv_cosmetic_name);
            brand = (TextView) itemView.findViewById(R.id.tv_brand);
//            img = (TextView)itemView.findViewById(R.id.iv_cosmetic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    toggleItemSelected(position);
                }
            });

        }
    }

    private void toggleItemSelected(int position) {
        if (mSelectedItems.get(position, false) == true) {
            mSelectedItems.delete(position);
            notifyItemChanged(position);
        } else if (mSelectedItems.size() < 1) {
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }

    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public List<String> choice() {
        int position;
        ArrayList<String> intentArray = new ArrayList<>();

        position = mSelectedItems.keyAt(0);
        mSelectedItems.put(position, false);
        notifyItemChanged(position);

        mSelectedItems.clear();

        intentArray.add(dataList.get(position).getName());
        intentArray.add(dataList.get(position).getIngredient());
//
//        Intent intent = new Intent(context, WritingActivity.class);
//        intent.putExtra("intentArray", intentArray);
//        Log.d("넘어갈 인텐트", intentArray.get(0));
//        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        return intentArray;
    }
}
