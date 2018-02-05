package myrecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by kevin on 2018/2/4.
 * https://github.com/yinkaiwen
 */

public class PullRecyclerViewSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

    private PullRecyclerView mPullRecyclerView;

    public PullRecyclerViewSwipeRefreshListener(PullRecyclerView pullRecyclerView) {
        mPullRecyclerView = pullRecyclerView;
    }

    @Override
    public void onRefresh() {
        if (!mPullRecyclerView.isRefreshing()){
            mPullRecyclerView.onRefresh();
        }
    }
}
