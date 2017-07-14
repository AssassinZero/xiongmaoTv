package com.ycsxt.admin.xiongmaotv.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/2/9.
 */

public class ZhongLeiBean implements Parcelable {

    /**
     * cname : 英雄联盟
     * ename : lol
     * img : http://i9.pdim.gs/23e529ba353b33c2f70e6d60f6be4c29.jpeg
     * ext : 1000
     * status : 1
     * cdn_rate : 0
     */

    private String cname;
    private String ename;
    private String img;
    private String ext;
    private String status;
    private String cdn_rate;

    public ZhongLeiBean() {
    }

    protected ZhongLeiBean(Parcel in) {
        cname = in.readString();
        ename = in.readString();
        img = in.readString();
        ext = in.readString();
        status = in.readString();
        cdn_rate = in.readString();
    }

    public static final Creator<ZhongLeiBean> CREATOR = new Creator<ZhongLeiBean>() {
        @Override
        public ZhongLeiBean createFromParcel(Parcel in) {
            return new ZhongLeiBean(in);
        }

        @Override
        public ZhongLeiBean[] newArray(int size) {
            return new ZhongLeiBean[size];
        }
    };

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCdn_rate() {
        return cdn_rate;
    }

    public void setCdn_rate(String cdn_rate) {
        this.cdn_rate = cdn_rate;
    }

    public ZhongLeiBean(String cname, String ename, String img, String ext, String status, String cdn_rate) {
        this.cname = cname;
        this.ename = ename;
        this.img = img;
        this.ext = ext;
        this.status = status;
        this.cdn_rate = cdn_rate;
    }

    @Override
    public String toString() {
        return "ZhongLeiBean{" +
                "cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", img='" + img + '\'' +
                ", ext='" + ext + '\'' +
                ", status='" + status + '\'' +
                ", cdn_rate='" + cdn_rate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)return false;
        if(obj.getClass()!=getClass())return false;
        ZhongLeiBean b= (ZhongLeiBean) obj;
        return b.getEname().equals(getEname());
    }

    @Override
    public int hashCode() {
        return getEname().hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        dest.writeString(ename);
        dest.writeString(img);
        dest.writeString(ext);
        dest.writeString(status);
        dest.writeString(cdn_rate);
    }
}
