package com.bit.schoolcomment.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.DataModel;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ShopListFragment extends BaseListFragment {

    @Override
    protected void pullNewData() {
        super.pullNewData();
        PullUtil.getInstance().searchGasStation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(List<DataModel> list) {
        String name = list.get(0).name;
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        setAdapter(new Adapter());
        setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_shop, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
