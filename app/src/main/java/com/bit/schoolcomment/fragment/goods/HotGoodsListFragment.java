package com.bit.schoolcomment.fragment.goods;

import com.bit.schoolcomment.event.goods.HotGoodsListEvent;
import com.bit.schoolcomment.event.school.SchoolSelectEvent;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HotGoodsListFragment extends GoodsListFragment {

    @Override
    protected void pullNewData() {
        int schoolId = DataUtil.getSchoolModel().ID;
        PullUtil.getInstance().getHotGoods(schoolId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleHotGoodsList(HotGoodsListEvent event) {
        updateUI(event.goodsListModel.data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        int schoolId = event.schoolModel.ID;
        PullUtil.getInstance().getHotGoods(schoolId);
    }
}
