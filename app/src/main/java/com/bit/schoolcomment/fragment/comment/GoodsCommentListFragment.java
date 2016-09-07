package com.bit.schoolcomment.fragment.comment;

import com.bit.schoolcomment.event.comment.GoodsCommentListEvent;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GoodsCommentListFragment extends CommentListFragment {

    @Override
    protected void pullNewData() {
        int goodsId = getActivity().getIntent().getIntExtra("goodsId", 0);
        PullUtil.getInstance().getGoodsComment(goodsId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsCommentList(GoodsCommentListEvent event) {
        updateUI(event.commentListModel.data);
    }
}
