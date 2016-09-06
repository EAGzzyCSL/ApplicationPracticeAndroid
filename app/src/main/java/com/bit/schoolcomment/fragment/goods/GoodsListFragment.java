package com.bit.schoolcomment.fragment.goods;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.activity.GoodsActivity;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.GoodsModel;
import com.facebook.drawee.view.SimpleDraweeView;

public abstract class GoodsListFragment extends BaseListFragment<GoodsModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ShopListAdapter();
    }

    private class ShopListAdapter extends BaseListAdapter<GoodsViewHolder>
            implements View.OnClickListener {

        @Override
        public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_shop, parent, false);
            GoodsViewHolder holder = new GoodsViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(GoodsViewHolder holder, int position) {
            GoodsModel model = getModel(position);
            holder.imageDv.setImageURI("https://www.baidu.com/img/bd_logo1.png");
            holder.nameTv.setText(model.name);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), GoodsActivity.class);
            startActivity(intent);
        }
    }

    private static class GoodsViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageDv;
        private TextView nameTv;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            imageDv = (SimpleDraweeView) itemView.findViewById(R.id.item_shop_image);
            nameTv = (TextView) itemView.findViewById(R.id.item_shop_name);
        }
    }
}