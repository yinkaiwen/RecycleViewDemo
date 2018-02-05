package com.example.kevin.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.kevin.recycleviewdemo.adapter.MyAdapter;
import com.example.kevin.recycleviewdemo.data.DataUtils;

import java.util.ArrayList;
import java.util.List;

import myrecyclerview.PullRecyclerView;

public class MainActivity extends AppCompatActivity implements DataUtils.DataListener, PullRecyclerView.LoadMoreListener {

    private PullRecyclerView mPullRecyclerView;
    private List<String> data = new ArrayList<>();
    private int index = 1;
    private MyAdapter mAdapter;
    private DataUtils mDataUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        initRecyclerView();
        initData();
    }

    private void bindViews() {
        mPullRecyclerView = findViewById(R.id.recycler_view);
    }

    private void initRecyclerView() {
        mAdapter = new MyAdapter(this, data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mPullRecyclerView.setLoadMoreListener(this);
        mPullRecyclerView.setLayoutManager(manager);
        mPullRecyclerView.setIsCanScrollWithRefreshingOrLoadingMore(true);
        mPullRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        mDataUtils = new DataUtils();
        mDataUtils.setListener(this);
        mPullRecyclerView.setRefreshing(true);
        mDataUtils.loadMore(index++);
    }

    @Override
    public void netCompleted(List<String> data) {
        if (index == 1) {
            for (int i = 0; i < data.size(); i++) {
                if (!this.data.contains(data.get(i))){
                    this.data.add(i, data.get(i));
                }
            }
        } else {
            this.data.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
        mPullRecyclerView.loadCompeleted();
    }

    @Override
    public void loadMore() {
        mDataUtils.loadMore(index++);
    }

    @Override
    public void onRefresh() {
        index = 0;
        loadMore();
    }
}
