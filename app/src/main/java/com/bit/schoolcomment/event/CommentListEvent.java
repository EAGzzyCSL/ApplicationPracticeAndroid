package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.list.CommentListModel;

public class CommentListEvent {

    public CommentListModel commentListModel;
    public Class targetClass;

    public CommentListEvent(CommentListModel commentListModel, Class targetClass) {
        this.commentListModel = commentListModel;
        this.targetClass = targetClass;
    }
}
