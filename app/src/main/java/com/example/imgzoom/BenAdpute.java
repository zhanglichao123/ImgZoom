package com.example.imgzoom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

public class BenAdpute extends RecyclerView.Adapter<BenAdpute.Myvv> {
    List<String> list;
    Context context;

    public BenAdpute(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myvv onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_transport, viewGroup, false);
        Myvv myvv = new Myvv(inflate);
        return myvv;
    }

    @Override
    public void onBindViewHolder(@NonNull Myvv myvv, int i) {

        myvv.tvDescribe.setText(list.get(i));
        myvv.tvTime.setText("2018-06-01 12:00");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myvv extends RecyclerView.ViewHolder {

        private final TextView tvDescribe;
        private final TextView tvTime;

        public Myvv(@NonNull View itemView) {
            super(itemView);
            tvDescribe = itemView.findViewById(R.id.tv_describe);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
