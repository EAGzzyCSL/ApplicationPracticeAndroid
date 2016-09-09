package com.bit.schoolcomment.fragment.shop;

import com.bit.schoolcomment.event.SchoolSelectEvent;
import com.bit.schoolcomment.util.DataUtil;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HotShopListFragment extends ShopListFragment {

    @Override
    protected void pullNewData() {
        int schoolId = DataUtil.getSchoolModel().ID;
        PullUtil.getInstance().getHotShop(schoolId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        int schoolId = event.schoolModel.ID;
        PullUtil.getInstance().getHotShop(schoolId);
    }
}
