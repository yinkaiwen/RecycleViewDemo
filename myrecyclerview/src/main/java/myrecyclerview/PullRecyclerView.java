package myrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.kevin.recyclerview_swipe_adapter.R;

/**
 * Created by kevin on 2018/2/4.
 * https://github.com/yinkaiwen
 */

public class PullRecyclerView extends LinearLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean mLoading = false;
    private View mFooter;

    public PullRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_recyclerview, null);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setOnRefreshListener(new PullRecyclerViewSwipeRefreshListener(this));

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setOnScrollListener(new PullRecyclerViewScrollListener(this));

        mFooter = view.findViewById(R.id.footer_view);
        mFooter.setVisibility(View.GONE);
        this.addView(view);
    }

    public void loadMore() {
        if (mLoadMoreListener != null && isLoading()) {
            mFooter.animate()
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(300)
                    .translationY(0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mFooter.setVisibility(View.VISIBLE);
                        }
                    })
                    .start();
            invalidate();
            mLoadMoreListener.loadMore();
        }
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    private LoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void onRefresh() {
        if (mLoadMoreListener != null)
            mLoadMoreListener.onRefresh();
    }

    public interface LoadMoreListener {
        void loadMore();

        void onRefresh();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView.LayoutManager getLayoutManger() {
        return mRecyclerView.getLayoutManager();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    private RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    public void loadCompeleted() {
        mFooter.animate()
                .setDuration(300)
                .translationY(mFooter.getHeight())
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        setLoading(false);
    }

    public boolean isLoading() {
        return mLoading;
    }

}
