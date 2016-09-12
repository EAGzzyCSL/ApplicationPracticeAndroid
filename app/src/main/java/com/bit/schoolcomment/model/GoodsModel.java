package com.bit.schoolcomment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GoodsModel extends BaseModel
        implements Parcelable {

    public int ID;
    public String name;
    public float price;
    public float rate;
    public List<String> images;

    protected GoodsModel(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        price = in.readFloat();
        rate = in.readFloat();
        images = new ArrayList<>();
        in.readList(images, null);
    }

    public static final Creator<GoodsModel> CREATOR = new Creator<GoodsModel>() {

        @Override
        public GoodsModel createFromParcel(Parcel in) {
            return new GoodsModel(in);
        }

        @Override
        public GoodsModel[] newArray(int size) {
            return new GoodsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeFloat(rate);
        dest.writeList(images);
    }
}
