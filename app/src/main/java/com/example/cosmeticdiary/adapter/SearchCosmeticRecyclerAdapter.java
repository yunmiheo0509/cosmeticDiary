package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.SearchCosmeticModel;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SearchCosmeticRecyclerAdapter extends RecyclerView.Adapter<SearchCosmeticRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<SearchCosmeticModel> dataList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    Button btn_choice;

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
//        View activityview = LayoutInflater.from(context).inflate(R.layout.activity_search_cosmetic, parent, false);
//        btn_choice = activityview.findViewById(R.id.btn_choice);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCosmeticRecyclerAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.brand.setText(dataList.get(position).getBrand());
//        holder.img.setText("" + dataList.get(position).getImg());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(dataList.get(position).getImg());
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    holder.img.setImageBitmap(bm);
                } catch (Exception e) {
                }
                return null;
            }
        }.execute();

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
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_cosmetic_name);
            brand = (TextView) itemView.findViewById(R.id.tv_brand);
            img = (ImageView) itemView.findViewById(R.id.iv_cosmetic);

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
//            btn_choice.setEnabled(false);
        } else if (mSelectedItems.size() < 1) {
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
//            btn_choice.setEnabled(true);
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
