package com.example.kevin.recycleviewdemo.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2018/2/3.
 * https://github.com/yinkaiwen
 */

public class DataUtils {
    private TaskThread taskThread = new TaskThread(TaskThread.class.getSimpleName());

    public DataUtils() {
        taskThread.start();
    }

    private DataListener mListener;

    public void setListener(DataListener listener) {
        mListener = listener;
    }

    public void loadMore(final int i) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int j = i; j < i + 10; j++) {
                    data.add(String.format("加载数据%s", j));
                }
                runOnUIThread(data);
            }
        };
        runOnThread(task);
    }

    public void refresh() {
        loadMore(0);
    }

    public interface DataListener {
        void netCompleted(List<String> data);
    }


    private void runOnThread(Runnable task) {
        taskThread.post(task);
    }

    private Handler mHandler;

    private void runOnUIThread(final List<String> data) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        Runnable resultTask = new Runnable() {
            @Override
            public void run() {
                mListener.netCompleted(data);
            }
        };
        mHandler.post(resultTask);
    }


    class TaskThread extends HandlerThread {
        private Handler mHandler;

        TaskThread(String name) {
            super(name);
        }

        void post(Runnable task) {
            if (mHandler == null) {
                mHandler = new Handler(getLooper());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.post(task);
        }
    }
}
