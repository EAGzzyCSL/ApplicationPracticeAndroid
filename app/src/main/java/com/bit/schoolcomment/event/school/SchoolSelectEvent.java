package com.bit.schoolcomment.event.school;

import com.bit.schoolcomment.model.SchoolModel;

public class SchoolSelectEvent {

    public SchoolModel schoolModel;

    public SchoolSelectEvent(SchoolModel schoolModel) {
        this.schoolModel = schoolModel;
    }
}
