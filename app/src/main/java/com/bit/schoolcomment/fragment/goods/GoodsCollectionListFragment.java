package com.bit.schoolcomment.fragment.goods;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.goods.GoodsCollectionEvent;
import com.bit.schoolcomment.event.goods.GoodsCollectionListEvent;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GoodsCollectionListFragment extends GoodsListFragment {

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new GoodsCollectionListAdapter();
    }

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().getCollection();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsCollectionList(GoodsCollectionListEvent event) {
        updateUI(event.goodsListModel.data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsCollection(GoodsCollectionEvent event) {
        pullNewData();
    }

    private class GoodsCollectionListAdapter extends GoodsListAdapter
            implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {

        private int mGoodsId;

        @Override
        public GoodsListFragment.GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            GoodsViewHolder holder = super.onCreateViewHolder(parent, viewType);
            holder.itemView.setOnLongClickListener(this);
            return holder;
        }

        @Override
        public boolean onLongClick(View v) {
            mGoodsId = getModel(v.getLabelFor()).ID;
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.inflate(R.menu.menu_goods_collection);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_goods_cancel:
                    PullUtil.getInstance().cancelCollection(mGoodsId);
                    break;
            }
            return true;
        }
    }
}
