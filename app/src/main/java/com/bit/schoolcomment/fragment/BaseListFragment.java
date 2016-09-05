package com.bit.schoolcomment.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.schoolcomment.MyApplication;
import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.BaseModel;
import com.bit.schoolcomment.util.ToastUtil;

import java.util.List;

public abstract class BaseListFragment<M extends BaseModel> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private EmptyAdapter mEmptyAdapter;

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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_list_recyclerView);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = getAdapter();
        mEmptyAdapter = new EmptyAdapter();

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        pullNewData();
    }

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    public void onRefresh() {
        pullNewData();
    }

    protected abstract void pullNewData();

    protected void updateUI(List<M> list) {
        mSwipeRefreshLayout.setRefreshing(false);

        if (list == null) {
            ToastUtil.show(getString(R.string.network_error));
        } else if (list.size() == 0) {
            mRecyclerView.setAdapter(mEmptyAdapter);
        } else {
            mList = list;
            mRecyclerView.setAdapter(mAdapter);
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

    private class EmptyAdapter extends RecyclerView.Adapter<EmptyViewHolder> {

        @Override
        public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EmptyViewHolder holder, int position) {
            holder.emptyTv.setText("empty");
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        private TextView emptyTv;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            emptyTv = (TextView) itemView.findViewById(R.id.item_empty_text);
        }
    }
}
