package com.bit.schoolcomment.fragment.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.activity.GoodsActivity;
import com.bit.schoolcomment.event.GoodsListEvent;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.GoodsModel;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class GoodsListFragment extends BaseListFragment<GoodsModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodsListAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsList(GoodsListEvent event) {
        if (event.targetClass == getClass()) updateUI(event.goodsListModel.data);
    }

    protected class GoodsListAdapter extends BaseListAdapter<GoodsViewHolder>
            implements View.OnClickListener {

        @Override
        public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_goods, parent, false);
            GoodsViewHolder holder = new GoodsViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(GoodsViewHolder holder, int position) {
            holder.itemView.setLabelFor(position);
            GoodsModel model = getModel(position);
            holder.imageDv.setImageURI("http://pic54.nipic.com/file/20141126/9422660_122829186000_2.jpg");
            holder.nameTv.setText(model.name);
            holder.priceTv.setText("￥" + model.price);
            holder.rateRb.setRating(model.rate);
        }

        @Override
        public void onClick(View v) {
            GoodsModel model = getModel(v.getLabelFor());
            Intent intent = new Intent(getActivity(), GoodsActivity.class);
            intent.putExtra("goodsId", model.ID);
            Bundle bundle = new Bundle();
            bundle.putParcelable("model", model);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    protected static class GoodsViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageDv;
        private TextView nameTv;
        private TextView priceTv;
        private RatingBar rateRb;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            imageDv = (SimpleDraweeView) itemView.findViewById(R.id.item_goods_image);
            nameTv = (TextView) itemView.findViewById(R.id.item_goods_name);
            priceTv = (TextView) itemView.findViewById(R.id.item_goods_price);
            rateRb = (RatingBar) itemView.findViewById(R.id.item_goods_rate);
        }
    }
}
