package com.bit.schoolcomment.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.activity.ShopActivity;
import com.bit.schoolcomment.model.DataModel;
import com.bit.schoolcomment.util.PullUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CommentListFragment extends BaseListFragment<DataModel> {

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

    private class ShopListAdapter extends BaseListAdapter<ShopViewHolder>
            implements View.OnClickListener {

        @Override
        public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_shop, parent, false);
            ShopViewHolder holder = new ShopViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(ShopViewHolder holder, int position) {
            DataModel model = getModel(position);
            holder.imageDv.setImageURI("https://www.baidu.com/img/bd_logo1.png");
            holder.nameTv.setText(model.name);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ShopActivity.class);
            startActivity(intent);
        }
    }

    private static class ShopViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageDv;
        private TextView nameTv;

        public ShopViewHolder(View itemView) {
            super(itemView);
            imageDv = (SimpleDraweeView) itemView.findViewById(R.id.item_shop_image);
            nameTv = (TextView) itemView.findViewById(R.id.item_shop_name);
        }
    }
}
