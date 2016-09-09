package com.bit.schoolcomment.fragment.shop;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.activity.ShopActivity;
import com.bit.schoolcomment.event.ShopListEvent;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.ShopModel;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class ShopListFragment extends BaseListFragment<ShopModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ShopListAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleShopList(ShopListEvent event) {
        if (event.targetClass == getClass()) updateUI(event.shopListModel.data);
    }

    protected class ShopListAdapter extends BaseListAdapter<ShopViewHolder>
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
            holder.itemView.setLabelFor(position);
            ShopModel model = getModel(position);
            holder.imageDv.setImageURI("http://i.k1982.com/design/up/200710/2007102763640374.jpg");
            holder.nameTv.setText(model.name);
            holder.addressTv.setText(model.address);
            holder.rateRb.setRating(model.rate);
        }

        @Override
        public void onClick(View v) {
            ShopModel model = getModel(v.getLabelFor());
            Intent intent = new Intent(getActivity(), ShopActivity.class);
            intent.putExtra(ShopActivity.EXTRA_shopId, model.ID);
            intent.putExtra(ShopActivity.EXTRA_model, model);
            startActivity(intent);
        }
    }

    protected static class ShopViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageDv;
        protected TextView nameTv;
        private TextView addressTv;
        protected RatingBar rateRb;

        public ShopViewHolder(View itemView) {
            super(itemView);
            imageDv = (SimpleDraweeView) itemView.findViewById(R.id.item_shop_image);
            nameTv = (TextView) itemView.findViewById(R.id.item_shop_name);
            addressTv = (TextView) itemView.findViewById(R.id.item_shop_address);
            rateRb = (RatingBar) itemView.findViewById(R.id.item_shop_rate);
        }
    }
}
