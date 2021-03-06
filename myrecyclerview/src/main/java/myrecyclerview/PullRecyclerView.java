package myrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.kevin.recyclerview_swipe_adapter.R;

/**
 * Created by kevin on 2018/2/4.
 * https://github.com/yinkaiwen
 */

public class PullRecyclerView extends LinearLayout {
    private static final String tag = PullRecyclerView.class.getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean mLoading = false;
    private boolean isRefreshing = false;
    private View mFooter;
    //If this field is false,when you refresh or loading,you can not scroll recycler_view.
    //If this field is true,you can scroll this.
    private boolean isCanScrollWithRefreshingOrLoadingMore = false;
    private PullRecyclerViewScrollListener mPullRecyclerViewScrollListener;

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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPullRecyclerViewScrollListener = new PullRecyclerViewScrollListener(this);
        mRecyclerView.setOnScrollListener(mPullRecyclerViewScrollListener);
        mRecyclerView.setOnTouchListener(new RecyclerViewOnTouchListener());

        mFooter = view.findViewById(R.id.footer_view);
        mFooter.setVisibility(View.GONE);
        this.addView(view);
    }

    public void loadMore() {
        if (mLoadListener != null && isLoading()) {
            setSwipeRefreshEnable(false);
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
            mLoadListener.loadMore();
        }
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    private LoadListener mLoadListener;

    public void setLoadListener(LoadListener loadListener) {
        mLoadListener = loadListener;
    }

    public void onRefresh() {
        if (mLoadListener != null) {
            setRefreshing(true);
            mLoadListener.onRefresh();
        }
    }


    public void setSwipeRefreshEnable(boolean swipeRefreshEnable) {
        mSwipeRefreshLayout.setEnabled(swipeRefreshEnable);
    }

    public interface LoadListener {
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
        setLoading(false);
        setRefreshing(false);
        setSwipeRefreshEnable(true);

        mFooter.animate()
                .setDuration(300)
                .translationY(mFooter.getHeight())
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();


    }

    public boolean isLoading() {
        return mLoading;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setIsCanScrollWithRefreshingOrLoadingMore(boolean isCanScrollWithRefreshingOrLoadingMore) {
        this.isCanScrollWithRefreshingOrLoadingMore = isCanScrollWithRefreshingOrLoadingMore;
    }

    class RecyclerViewOnTouchListener implements View.OnTouchListener {
//        int lastX;
//        int lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            int currentX = (int) event.getRawX();
//            int currentY = (int) event.getRawY();
//            int dx = currentX - lastX;
//            int dy = currentY - lastY;
//
//            lastX = currentX;
//            lastY = currentY;

//            Log.i(tag, String.format("dx : %s,dy : %s", dx, dy));


            return (!isCanScrollWithRefreshingOrLoadingMore) && (mLoading || isRefreshing);
        }
    }

    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }
}
