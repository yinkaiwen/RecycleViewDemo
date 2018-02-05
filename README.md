# RecycleViewDemo
I read https://github.com/WuXiaolong/PullLoadMoreRecyclerView,and fixed some code from this.
You can use this PullRecyclerView in your project.

XML : no need swiperefreshlayout.
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.kevin.recycleviewdemo.MainActivity">

    <myrecyclerview.PullRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </myrecyclerview.PullRecyclerView>

</RelativeLayout>

```
Java:You need implements PullRecyclerView.LoadListener in your Activity or Fragment,then you can get this method:
```
        void loadMore();

        void onRefresh();
```
override these method and you can do something.
Other code just like normal recycler_view,such as:
```
mAdapter = new MyAdapter(this, data);
LinearLayoutManager manager = new LinearLayoutManager(this);
manager.setOrientation(LinearLayoutManager.VERTICAL);
mPullRecyclerView.setLayoutManager(manager);
//set Listener
mPullRecyclerView.setLoadListener(this);
//When refreshing or loading,you can scroll recycler_view.If is false,you can not scroll recycler_view. 
mPullRecyclerView.setIsCanScrollWithRefreshingOrLoadingMore(true);
mPullRecyclerView.setAdapter(mAdapter);
```
