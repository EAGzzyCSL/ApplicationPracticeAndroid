package com.bit.schoolcomment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopModel extends BaseModel
        implements Parcelable {

    public int ID;
    public String name;
    public String address;
    public String image;
    public float rate;
    public int school_ID;

    protected ShopModel(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        address = in.readString();
        image = in.readString();
        rate = in.readFloat();
        school_ID = in.readInt();
    }

    public static final Creator<ShopModel> CREATOR = new Creator<ShopModel>() {

        @Override
        public ShopModel createFromParcel(Parcel in) {
            return new ShopModel(in);
        }

        @Override
        public ShopModel[] newArray(int size) {
            return new ShopModel[size];
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
        dest.writeString(address);
        dest.writeString(image);
        dest.writeFloat(rate);
        dest.writeInt(school_ID);
    }
}
