package com.example.firebaseuploadimagejava;

public class Upload {
    private String mName;
    private String mOrder;
    private String mImageUrl;
    private String mKey;




    public Upload() {
        //empty constructor needed
    }

    public Upload(String mName, String mOrder, String mImageUrl, String mKey) {
        if (mName.isEmpty()){
            mName="xD";
        }
        this.mName = mName;
        this.mOrder = mOrder;
        this.mImageUrl = mImageUrl;
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmOrder() {
        return mOrder;
    }

    public void setmOrder(String mOrder) {
        this.mOrder = mOrder;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
