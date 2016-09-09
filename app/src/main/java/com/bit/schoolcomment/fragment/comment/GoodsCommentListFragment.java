package com.bit.schoolcomment.fragment.comment;

import com.bit.schoolcomment.util.PullUtil;

public class GoodsCommentListFragment extends CommentListFragment {

    @Override
    protected void pullNewData() {
        int goodsId = getActivity().getIntent().getIntExtra("goodsId", 0);
        PullUtil.getInstance().getGoodsComment(goodsId);
    }
}
