package myrecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by kevin on 2018/2/5.
 * https://github.com/yinkaiwen
 */

public class PullRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String tag = PullRecyclerViewScrollListener.class.getSimpleName();
    private PullRecyclerView mPullRecyclerView;

    public PullRecyclerViewScrollListener(PullRecyclerView pullRecyclerView) {
        mPullRecyclerView = pullRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int first = 0;
        int last = 0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int count = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            first = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            last = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (last == -1)
                last = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (last == -1)
                last = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int[] into = new int[spanCount];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(into);
            last = max(into);
            first = staggeredGridLayoutManager.findFirstVisibleItemPositions(into)[0];
        }

        if (mPullRecyclerView.getPullRefreshEnable()
                && last == (count - 1)
                && (dy > 0)
                && !mPullRecyclerView.isLoading()
                && !mPullRecyclerView.isRefreshing()) {
            mPullRecyclerView.setLoading(true);
            mPullRecyclerView.loadMore();
        }
    }

    private int max(int[] into) {
        int max = into[0];
        if (into.length > 1)
            for (int i = 1; i < into.length; i++) {
                int value = into[i];
                if (value > max) {
                    max = value;
                }
            }
        return max;
    }

}
