package com.bit.schoolcomment.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.DataModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class BaseListFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_list_recyclerView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(List<DataModel> list) {
        String name = list.get(0).name;
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
    }
}
