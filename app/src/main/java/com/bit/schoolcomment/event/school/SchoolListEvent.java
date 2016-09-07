package com.bit.schoolcomment.event.school;

import com.bit.schoolcomment.model.list.SchoolListModel;

public class SchoolListEvent {

    public SchoolListModel schoolListModel;

    public SchoolListEvent(SchoolListModel schoolListModel) {
        this.schoolListModel = schoolListModel;
    }
}
