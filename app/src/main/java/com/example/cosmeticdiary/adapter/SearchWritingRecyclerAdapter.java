package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.activity.SearchCosmeticActivity;
import com.example.cosmeticdiary.activity.WritingActivity;
import com.example.cosmeticdiary.model.SearchWritingModel;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수
//        holder.onBind(listData.get(position));
        holder.date.setText(dataList.get(position).getDate());
        holder.cosmetic.setText(dataList.get(position).getCosmetic());
        holder.satisfy.setText("(" + dataList.get(position).getSatisfy() + ")");
        holder.content.setText(dataList.get(position).getContent());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(dataList.get(position).getImg());
                    Log.d("url주소", url.toString());
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    holder.img.setImageBitmap(bm);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return dataList.size();
    }

//    //??
//    void addItem(SearchWritingModel data) {
//        // 외부에서 item을 추가시킬 함수입니다.
//        dataList.add(data);
//    }

    // RecyclerView의 핵심인 ViewHolder
    // 여기서 subView를 setting
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView date;
        private TextView cosmetic;
        private TextView satisfy;
        private TextView content;

        ItemViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iv_cosmetic_image);
            date = itemView.findViewById(R.id.tv_date);
            cosmetic = itemView.findViewById(R.id.tv_cosmetic_name);
            satisfy = itemView.findViewById(R.id.tv_satisfy);
            content = itemView.findViewById(R.id.tv_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //writingActivity로 인텐트 전달
                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), WritingActivity.class);

                    intent.putExtra("cosmeticname", dataList.get(position).getCosmetic());
                    intent.putExtra("img", dataList.get(position).getImg());
                    intent.putExtra("satisfy", dataList.get(position).getSatisfy());
                    intent.putExtra("content", dataList.get(position).getContent());
                    intent.putExtra("date", dataList.get(position).getDate());
                    intent.putExtra("ingredient", dataList.get(position).getIngredient());
                    intent.putExtra("jopssal", dataList.get(position).getJopssal());
                    intent.putExtra("dry", dataList.get(position).getDry());
                    intent.putExtra("hwanongsung", dataList.get(position).getHwanongsung());
                    intent.putExtra("good", dataList.get(position).getGood());
                    intent.putExtra("trouble", dataList.get(position).getTrouble());
                    intent.putExtra("etc", dataList.get(position).getEtc());

                    v.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
    }

//    public List<String> choice() {
//        int position;
//        ArrayList<String> intentArray = new ArrayList<>();
//        position = mSelectedItems.keyAt(0);
//        mSelectedItems.put(position, false);
//        notifyItemChanged(position);
//
//        mSelectedItems.clear();
//
//        intentArray.add(dataList.get(position).getImg());
//        intentArray.add(dataList.get(position).getDate());
//        intentArray.add(dataList.get(position).getCosmetic());
//        intentArray.add(dataList.get(position).getSatisfy());
//        intentArray.add(dataList.get(position).getContent());
//
////        Intent intent = new Intent(context, WritingActivity.class);
////        intent.putExtra("intentArray", intentArray);
////        Log.d("넘어갈 인텐트", intentArray.get(0));
////        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//
//        return intentArray;
//    }
}