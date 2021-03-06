package com.example.kevin.recycleviewdemo.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2018/2/3.
 * https://github.com/yinkaiwen
 */

public class DataUtils {
    private static final String tag = DataUtils.class.getSimpleName();
    private ExecutorService exe = Executors.newSingleThreadExecutor();
    private Handler mHandler;
    private DataListener mListener;

    public void setListener(DataListener listener) {
        mListener = listener;
    }

    public void loadMore(int i) {
        loadMore(i, false);
    }

    public void loadMore(final int i, final boolean isRefreshing) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (mListener != null) {
                        mListener.netCompleted((List<String>) msg.obj);
                    }
                }
            };
        }
        final int change = 20;
        final int first = i * change;
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(tag, String.format("ThreadName : %s", Thread.currentThread().getName()));
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> data = new ArrayList<>();
                for (int j = first; j < first + change; j++) {
                    data.add(String.format("Load %s", j));
                }
                Message obtain = Message.obtain();
                obtain.obj = data;
                if (isRefreshing) {
                    obtain.arg1 = 1;
                } else {
                    obtain.arg1 = 0;
                }
                mHandler.sendMessage(obtain);
            }
        };
        exe.execute(task);
    }


    public interface DataListener {
        void netCompleted(List<String> data);
    }


}
