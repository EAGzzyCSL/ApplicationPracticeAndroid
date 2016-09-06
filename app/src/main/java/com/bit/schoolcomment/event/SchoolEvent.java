package com.bit.schoolcomment.event;

import com.bit.schoolcomment.model.list.SchoolListModel;

public class SchoolEvent {

    public SchoolListModel schoolListModel;

    public SchoolEvent(SchoolListModel schoolListModel) {
        this.schoolListModel = schoolListModel;
    }
}
