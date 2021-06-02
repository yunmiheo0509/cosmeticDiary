package com.example.cosmeticdiary.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.activity.WritingActivity;
import com.example.cosmeticdiary.model.SearchWritingModel;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WritingListAdapter extends RecyclerView.Adapter<WritingListAdapter.ItemViewHolder> {
    private Context c;
    private List<SearchWritingModel> dataList;
    ArrayList<String> conditionArray = new ArrayList<>();
    String conditionString = "";

    public WritingListAdapter(Context c, List<SearchWritingModel> dataList) {
        this.c = c;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public WritingListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킴
        // return 인자는 ViewHolder
        View view = LayoutInflater.from(c).inflate(R.layout.item_writing_list, parent, false);
        return new WritingListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수
//        holder.onBind(listData.get(position));

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

        holder.name.setText(dataList.get(position).getCosmetic());

        if (dataList.get(position).getJopssal().equals("true")) conditionArray.add("좁쌀");
        if (dataList.get(position).getDry().equals("true")) conditionArray.add("수분부족");
        if (dataList.get(position).getHwanongsung().equals("true")) conditionArray.add("화농성여드름");
        if (dataList.get(position).getGood().equals("true")) conditionArray.add("좋음");
        if (dataList.get(position).getTrouble().equals("true")) conditionArray.add("트러블");
        if (dataList.get(position).getGood().equals("true")) conditionArray.add("기타");

        for (int i = 0; i < conditionArray.size(); i++) {
            conditionString += conditionArray.get(i);
            if (i != conditionArray.size() - 1)
                conditionString += ", ";
        }
        holder.condition.setText(conditionString);
        holder.satisfy.setText(dataList.get(position).getSatisfy());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return dataList.size();
    }

//    void addItem(SearchWritingModel data) {
//        // 외부에서 item을 추가시킬 함수
//        data.add(data);
//    }

    // RecyclerView의 핵심인 ViewHolder
    // 여기서 subView를 setting
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView condition;
        private TextView satisfy;

        ItemViewHolder(final View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iv_cosmetic);
            name = itemView.findViewById(R.id.tv_cosmetic_name);
            condition = itemView.findViewById(R.id.tv_insert_condition);
            satisfy = itemView.findViewById(R.id.tv_insert_satisfy);

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

//    public interface RecyclerViewClickListener{
//        void onClick(View v, int position);
//    }
}
