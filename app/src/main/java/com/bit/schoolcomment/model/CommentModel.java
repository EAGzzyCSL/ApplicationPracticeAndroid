package com.bit.schoolcomment.model;

import java.util.List;

public class CommentModel extends BaseModel {

    public int ID;
    public String avatar;
    public String name;
    public String content;
    public float rate;
    public String time;
    public List<String> images;
    public int like_num;
    public int ispraised;
}
