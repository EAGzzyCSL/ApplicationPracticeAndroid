package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.event.goods.HotGoodsEvent;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HotGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().getHotGoods(3);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleHotGoods(HotGoodsEvent event) {
        updateUI(event.goodsListModel.data);
    }
}
