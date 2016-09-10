package com.bit.schoolcomment.fragment.comment;

import com.bit.schoolcomment.util.PullUtil;

public class MyCommentListFragment extends CommentListFragment {

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().getComment();
    }
}
