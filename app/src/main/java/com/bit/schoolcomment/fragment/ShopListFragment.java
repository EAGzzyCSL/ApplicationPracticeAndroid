package com.bit.schoolcomment.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.model.DataModel;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ShopListFragment extends BaseListFragment<DataModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ShopListAdapter();
    }

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().searchGasStation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(List<DataModel> list) {
        updateUI(list);
    }

    private class ShopListAdapter extends BaseListAdapter<ShopListViewHolder> {

        @Override
        public ShopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_shop, parent, false);
            return new ShopListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ShopListViewHolder holder, int position) {
            DataModel model = getModel(position);
            holder.nameTv.setText(model.name);
        }
    }

    private static class ShopListViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public ShopListViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_shop_name);
        }
    }
}
