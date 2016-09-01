package com.bit.schoolcomment.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bit.schoolcomment.MyApplication;
import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.BaseModel;

import java.util.List;

public abstract class BaseListFragment<M extends BaseModel> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;

    private List<M> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setEventBusOn(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.base_list_swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.base_list_recyclerView);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setHasFixedSize(true);
        mAdapter = getAdapter();
        recyclerView.setAdapter(mAdapter);

        beforePullNewData();
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    public void onRefresh() {
        beforePullNewData();
    }

    private void beforePullNewData() {
        mSwipeRefreshLayout.setRefreshing(true);
        pullNewData();
    }

    protected abstract void pullNewData();

    protected void updateUI(List<M> list) {
        if (list == null) {
            Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
        } else {
            this.mList = list;
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected M getModel(int location) {
        return mList.get(location);
    }

    protected abstract class BaseListAdapter<V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

        @Override
        public void onViewAttachedToWindow(V holder) {
            super.onViewAttachedToWindow(holder);
            holder.itemView.startAnimation(MyApplication.getDefault().mItemAnimation);
        }

        @Override
        public void onViewDetachedFromWindow(V holder) {
            super.onViewDetachedFromWindow(holder);
            holder.itemView.clearAnimation();
        }

        @Override
        public int getItemCount() {
            if (mList == null) return 0;
            else return mList.size();
        }
    }
}
