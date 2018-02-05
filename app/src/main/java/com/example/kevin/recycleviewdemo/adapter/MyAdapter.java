package com.example.kevin.recycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevin.recycleviewdemo.R;

import java.util.List;

/**
 * Created by kevin on 2018/2/5.
 * https://github.com/yinkaiwen
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String tag = MyAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> datas;

    public MyAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder h = (MyViewHolder) holder;
            h.mTextView.setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        int count = datas == null ? 0 : datas.size();
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item);
        }
    }
}
