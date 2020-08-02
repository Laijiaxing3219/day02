package com.example.day02dangridaima.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.day02dangridaima.R;
import com.example.day02dangridaima.bean.Bean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class RlvMainAdapter extends RecyclerView.Adapter{
    private final Context mContext;
    public final ArrayList<Bean.ResultsBean> mList;
    private OnItemLongClickListener mListener;

    public RlvMainAdapter(Context context, ArrayList<Bean.ResultsBean> list) {

        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //添加parent原因 是为了水平方向子条目可以充满全屏
        //attachToRoot:是否关联到父容器中
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent,false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        Bean.ResultsBean bean = mList.get(position);
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher);
        Glide.with(mContext).load(bean.getUrl()).apply(options).into(vh.mIv);

        vh.mTv.setText(bean.getDesc());

        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null){
                    mListener.onItemLongClick(v,position);
                }
                //true:代表消费事件
                //fasle:不消费
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<Bean.ResultsBean> results) {
        mList.addAll(results);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder{

        private final TextView mTv;
        private final ImageView mIv;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
            mIv = itemView.findViewById(R.id.iv);
        }
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View v,int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        mListener = listener;
    }
}
