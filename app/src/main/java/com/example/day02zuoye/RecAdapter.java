package com.example.day02zuoye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class RecAdapter extends RecyclerView.Adapter<RecAdapter.Holder> {
    private ArrayList<FuliBean.ResultsBean> list;
    private Context context;

    public RecAdapter(ArrayList<FuliBean.ResultsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fuli_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Glide.with(context).load(list.get(position).getUrl()).into(holder.iv);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(view,position);
            }
        });*/
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickLong.OnClickLong(view,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private final ImageView iv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }



    public setOnClick onClick;
    public setOnClickLong onClickLong;

    public void setOnClick(setOnClick onClick){
        this.onClick=onClick;
    }

    public void setOnClickLong(setOnClickLong onClickLong){
        this.onClickLong=onClickLong;
    }

    public interface setOnClick{
        void onClick(View view,int position);
    }

    public interface setOnClickLong{
        void OnClickLong(View view,int position);
    }

}
