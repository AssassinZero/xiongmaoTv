package com.ycsxt.admin.xiongmaotv.domain;

import java.util.List;

/**
 * Created by admin on 2017/2/14.
 */

public class JingCaiXingYanItems {


    /**
     * errno : 0
     * errmsg :
     * data : {"items":[{"xid":"1695897","rid":"64499768","style_type":"301","display_type":"2","playstatus":"1","name":"苦涩猫咪","photo":"https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"4740","province":"内蒙古自治区","city":"呼伦贝尔市","streamurl":"http://flv-live-ws.xingyan.panda.tv/panda-xingyan/0d6b8a52e022abbb200e4fa6b9f424d0.flv","nickName":"苦涩猫咪","avatar":"http://i7.pdim.gs/68d156feb21b69cd74a80dd2d9265439.jpg","level":"7","levelicon":"anchor7"},{"xid":"4428801","rid":"72581396","style_type":"301","display_type":"2","playstatus":"1","name":"新人主播求关注 颜值板块  气质优雅","photo":"https://image.xingyan.panda.tv/14b39553c9ebb297796cb275bcc83350.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/14b39553c9ebb297796cb275bcc83350.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"2000","province":"安徽省","city":"合肥市","streamurl":"http://flv-live-qn.xingyan.panda.tv/panda-xingyan/242d32cbcaf13781cd1d558e1bff7811.flv","nickName":"美琪miki","avatar":"http://i5.pdim.gs/1656053dd18d8c121436168f6da694dd.jpg","level":"2","levelicon":"anchor2"},{"xid":"2147909","rid":"66463862","style_type":"301","display_type":"2","playstatus":"1","name":"一起聊天啊～","photo":"https://image.xingyan.panda.tv/90d8f5a24048ed6cba71f8ad6bdcad30.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/90d8f5a24048ed6cba71f8ad6bdcad30.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"3646","province":"河北省","city":"石家庄市","streamurl":"http://flv-live-qn.xingyan.panda.tv/panda-xingyan/5f7d3bae0cdac829a7bf71bdec6af308.flv","nickName":"田球球啊","avatar":"http://i9.pdim.gs/56fff113840cb98140cec5c9b95672fe.jpg","level":"5","levelicon":"anchor5"},{"xid":"1631780","rid":"64252828","style_type":"301","display_type":"2","playstatus":"1","name":"咦","photo":"https://image.xingyan.panda.tv/21749209ffafe8ca435fd6ea84795437.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/21749209ffafe8ca435fd6ea84795437.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"2548","province":"河南省","city":"信阳市","streamurl":"http://flv-live-ws.xingyan.panda.tv/panda-xingyan/dc19eb1aaf83ae1fdc882166d455092f.flv","nickName":"cccc寳","avatar":"http://i5.pdim.gs/572ffccf56c6c51e03cb04bf7c7a0424.jpg","level":"6","levelicon":"anchor6"}],"total":30,"place":"2","ename":"xingyan","cname":"熊猫星颜","icon":"https://image.xingyan.panda.tv/c9d21eed1c6b0557e24fe2c89a2ec751.png"}
     */

    private int errno;
    private String errmsg;
    private DataBean data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * items : [{"xid":"1695897","rid":"64499768","style_type":"301","display_type":"2","playstatus":"1","name":"苦涩猫咪","photo":"https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"4740","province":"内蒙古自治区","city":"呼伦贝尔市","streamurl":"http://flv-live-ws.xingyan.panda.tv/panda-xingyan/0d6b8a52e022abbb200e4fa6b9f424d0.flv","nickName":"苦涩猫咪","avatar":"http://i7.pdim.gs/68d156feb21b69cd74a80dd2d9265439.jpg","level":"7","levelicon":"anchor7"},{"xid":"4428801","rid":"72581396","style_type":"301","display_type":"2","playstatus":"1","name":"新人主播求关注 颜值板块  气质优雅","photo":"https://image.xingyan.panda.tv/14b39553c9ebb297796cb275bcc83350.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/14b39553c9ebb297796cb275bcc83350.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"2000","province":"安徽省","city":"合肥市","streamurl":"http://flv-live-qn.xingyan.panda.tv/panda-xingyan/242d32cbcaf13781cd1d558e1bff7811.flv","nickName":"美琪miki","avatar":"http://i5.pdim.gs/1656053dd18d8c121436168f6da694dd.jpg","level":"2","levelicon":"anchor2"},{"xid":"2147909","rid":"66463862","style_type":"301","display_type":"2","playstatus":"1","name":"一起聊天啊～","photo":"https://image.xingyan.panda.tv/90d8f5a24048ed6cba71f8ad6bdcad30.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/90d8f5a24048ed6cba71f8ad6bdcad30.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"3646","province":"河北省","city":"石家庄市","streamurl":"http://flv-live-qn.xingyan.panda.tv/panda-xingyan/5f7d3bae0cdac829a7bf71bdec6af308.flv","nickName":"田球球啊","avatar":"http://i9.pdim.gs/56fff113840cb98140cec5c9b95672fe.jpg","level":"5","levelicon":"anchor5"},{"xid":"1631780","rid":"64252828","style_type":"301","display_type":"2","playstatus":"1","name":"咦","photo":"https://image.xingyan.panda.tv/21749209ffafe8ca435fd6ea84795437.jpeg?x-oss-process=image/quality,q_70","s_photo":"https://image.xingyan.panda.tv/21749209ffafe8ca435fd6ea84795437.jpeg?x-oss-process=image/resize,w_480/quality,q_70","personnum":"2548","province":"河南省","city":"信阳市","streamurl":"http://flv-live-ws.xingyan.panda.tv/panda-xingyan/dc19eb1aaf83ae1fdc882166d455092f.flv","nickName":"cccc寳","avatar":"http://i5.pdim.gs/572ffccf56c6c51e03cb04bf7c7a0424.jpg","level":"6","levelicon":"anchor6"}]
         * total : 30
         * place : 2
         * ename : xingyan
         * cname : 熊猫星颜
         * icon : https://image.xingyan.panda.tv/c9d21eed1c6b0557e24fe2c89a2ec751.png
         */

        private int total;
        private String place;
        private String ename;
        private String cname;
        private String icon;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * xid : 1695897
             * rid : 64499768
             * style_type : 301
             * display_type : 2
             * playstatus : 1
             * name : 苦涩猫咪
             * photo : https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/quality,q_70
             * s_photo : https://image.xingyan.panda.tv/6cdb7878f9ce2034a5c985308050cda6.jpeg?x-oss-process=image/resize,w_480/quality,q_70
             * personnum : 4740
             * province : 内蒙古自治区
             * city : 呼伦贝尔市
             * streamurl : http://flv-live-ws.xingyan.panda.tv/panda-xingyan/0d6b8a52e022abbb200e4fa6b9f424d0.flv
             * nickName : 苦涩猫咪
             * avatar : http://i7.pdim.gs/68d156feb21b69cd74a80dd2d9265439.jpg
             * level : 7
             * levelicon : anchor7
             */

            private String xid;
            private String rid;
            private String style_type;
            private String display_type;
            private String playstatus;
            private String name;
            private String photo;
            private String s_photo;
            private String personnum;
            private String province;
            private String city;
            private String streamurl;
            private String nickName;
            private String avatar;
            private String level;
            private String levelicon;

            public String getXid() {
                return xid;
            }

            public void setXid(String xid) {
                this.xid = xid;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getStyle_type() {
                return style_type;
            }

            public void setStyle_type(String style_type) {
                this.style_type = style_type;
            }

            public String getDisplay_type() {
                return display_type;
            }

            public void setDisplay_type(String display_type) {
                this.display_type = display_type;
            }

            public String getPlaystatus() {
                return playstatus;
            }

            public void setPlaystatus(String playstatus) {
                this.playstatus = playstatus;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getS_photo() {
                return s_photo;
            }

            public void setS_photo(String s_photo) {
                this.s_photo = s_photo;
            }

            public String getPersonnum() {
                return personnum;
            }

            public void setPersonnum(String personnum) {
                this.personnum = personnum;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getStreamurl() {
                return streamurl;
            }

            public void setStreamurl(String streamurl) {
                this.streamurl = streamurl;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getLevelicon() {
                return levelicon;
            }

            public void setLevelicon(String levelicon) {
                this.levelicon = levelicon;
            }
        }
    }
}
