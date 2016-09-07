package com.bit.schoolcomment.event.comment;

import com.bit.schoolcomment.model.list.CommentListModel;

public class GoodsCommentListEvent {

    public CommentListModel commentListModel;

    public GoodsCommentListEvent(CommentListModel commentListModel) {
        this.commentListModel = commentListModel;
    }
}
