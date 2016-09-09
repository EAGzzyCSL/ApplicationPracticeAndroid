package com.bit.schoolcomment.fragment.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.ShopSelectEvent;
import com.bit.schoolcomment.model.ShopModel;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.EventBus;

public class ShopSelectListFragment extends ShopListFragment {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ShopSelectListAdapter();
    }

    @Override
    protected void pullNewData() {
        int schoolId = DataUtil.getSchoolModel().ID;
        PullUtil.getInstance().getSchoolShop(schoolId);
    }

    private class ShopSelectListAdapter extends ShopListAdapter {

        @Override
        public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_shop_select, parent, false);
            ShopViewHolder holder = new ShopViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(ShopViewHolder holder, int position) {
            holder.itemView.setLabelFor(position);
            ShopModel model = getModel(position);
            holder.nameTv.setText(model.name);
            holder.rateRb.setRating(model.rate);
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new ShopSelectEvent(getModel(v.getLabelFor())));
        }
    }
}
