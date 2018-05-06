package com.aipiao.bkpk.bean.aicai;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ExpertManDetail implements Serializable {


    /**
     * code : 0000
     * data : {"id":"5ab00c867011bd57bddb788f","lot":"01|06","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"},"startQihao":"2018032087","endQihao":"2018032111","catId":11111,"viewCount":62648,"fanganDetails":[{"multi":1,"touMoney":400,"leiMoney":400,"zhuanMoney":900,"status":0,"qihao":"2018032087","lastUpdateDate":"20180320031622"},{"multi":1,"touMoney":400,"leiMoney":800,"zhuanMoney":500,"status":0,"qihao":"2018032088","lastUpdateDate":"20180320031622"},{"multi":1,"touMoney":400,"leiMoney":1200,"zhuanMoney":100,"status":0,"qihao":"2018032101","lastUpdateDate":"20180320031622"},{"multi":2,"touMoney":800,"leiMoney":2000,"zhuanMoney":600,"status":0,"qihao":"2018032102","lastUpdateDate":"20180320031622"},{"multi":3,"touMoney":1200,"leiMoney":3200,"zhuanMoney":700,"status":0,"qihao":"2018032103","lastUpdateDate":"20180320031622"},{"multi":4,"touMoney":1600,"leiMoney":4800,"zhuanMoney":400,"status":0,"qihao":"2018032104","lastUpdateDate":"20180320031622"},{"multi":6,"touMoney":2400,"leiMoney":7200,"zhuanMoney":600,"status":0,"qihao":"2018032105","lastUpdateDate":"20180320031622"},{"multi":9,"touMoney":3600,"leiMoney":10800,"zhuanMoney":900,"status":0,"qihao":"2018032106","lastUpdateDate":"20180320031622"},{"multi":14,"touMoney":5600,"leiMoney":16400,"zhuanMoney":1800,"status":0,"qihao":"2018032107","lastUpdateDate":"20180320031622"},{"multi":21,"touMoney":8400,"leiMoney":24800,"zhuanMoney":2500,"status":0,"qihao":"2018032108","lastUpdateDate":"20180320031622"},{"multi":31,"touMoney":12400,"leiMoney":37200,"zhuanMoney":3100,"status":0,"qihao":"2018032109","lastUpdateDate":"20180320031622"},{"multi":47,"touMoney":18800,"leiMoney":56000,"zhuanMoney":5100,"status":0,"qihao":"2018032110","lastUpdateDate":"20180320031622"},{"multi":70,"touMoney":28000,"leiMoney":84000,"zhuanMoney":7000,"status":0,"qihao":"2018032111","lastUpdateDate":"20180320031622"}]}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable{
        /**
         * id : 5ab00c867011bd57bddb788f
         * lot : 01|06
         * playType : 8001
         * money : 84000
         * addDate : 20180320031622
         * lastUpdateDate : 20180320031622
         * status : 1
         * expert : {"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"}
         * startQihao : 2018032087
         * endQihao : 2018032111
         * catId : 11111
         * viewCount : 62648
         * fanganDetails : [{"multi":1,"touMoney":400,"leiMoney":400,"zhuanMoney":900,"status":0,"qihao":"2018032087","lastUpdateDate":"20180320031622"},{"multi":1,"touMoney":400,"leiMoney":800,"zhuanMoney":500,"status":0,"qihao":"2018032088","lastUpdateDate":"20180320031622"},{"multi":1,"touMoney":400,"leiMoney":1200,"zhuanMoney":100,"status":0,"qihao":"2018032101","lastUpdateDate":"20180320031622"},{"multi":2,"touMoney":800,"leiMoney":2000,"zhuanMoney":600,"status":0,"qihao":"2018032102","lastUpdateDate":"20180320031622"},{"multi":3,"touMoney":1200,"leiMoney":3200,"zhuanMoney":700,"status":0,"qihao":"2018032103","lastUpdateDate":"20180320031622"},{"multi":4,"touMoney":1600,"leiMoney":4800,"zhuanMoney":400,"status":0,"qihao":"2018032104","lastUpdateDate":"20180320031622"},{"multi":6,"touMoney":2400,"leiMoney":7200,"zhuanMoney":600,"status":0,"qihao":"2018032105","lastUpdateDate":"20180320031622"},{"multi":9,"touMoney":3600,"leiMoney":10800,"zhuanMoney":900,"status":0,"qihao":"2018032106","lastUpdateDate":"20180320031622"},{"multi":14,"touMoney":5600,"leiMoney":16400,"zhuanMoney":1800,"status":0,"qihao":"2018032107","lastUpdateDate":"20180320031622"},{"multi":21,"touMoney":8400,"leiMoney":24800,"zhuanMoney":2500,"status":0,"qihao":"2018032108","lastUpdateDate":"20180320031622"},{"multi":31,"touMoney":12400,"leiMoney":37200,"zhuanMoney":3100,"status":0,"qihao":"2018032109","lastUpdateDate":"20180320031622"},{"multi":47,"touMoney":18800,"leiMoney":56000,"zhuanMoney":5100,"status":0,"qihao":"2018032110","lastUpdateDate":"20180320031622"},{"multi":70,"touMoney":28000,"leiMoney":84000,"zhuanMoney":7000,"status":0,"qihao":"2018032111","lastUpdateDate":"20180320031622"}]
         */

        private String id;
        private String lot;
        private int playType;
        private int money;
        private String addDate;
        private String lastUpdateDate;
        private int status;
        private ExpertBean expert;
        private String startQihao;
        private String endQihao;
        private int catId;
        private int viewCount;
        private List<FanganDetailsBean> fanganDetails;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLot() {
            return lot;
        }

        public void setLot(String lot) {
            this.lot = lot;
        }

        public int getPlayType() {
            return playType;
        }

        public void setPlayType(int playType) {
            this.playType = playType;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getLastUpdateDate() {
            return lastUpdateDate;
        }

        public void setLastUpdateDate(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ExpertBean getExpert() {
            return expert;
        }

        public void setExpert(ExpertBean expert) {
            this.expert = expert;
        }

        public String getStartQihao() {
            return startQihao;
        }

        public void setStartQihao(String startQihao) {
            this.startQihao = startQihao;
        }

        public String getEndQihao() {
            return endQihao;
        }

        public void setEndQihao(String endQihao) {
            this.endQihao = endQihao;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public List<FanganDetailsBean> getFanganDetails() {
            return fanganDetails;
        }

        public void setFanganDetails(List<FanganDetailsBean> fanganDetails) {
            this.fanganDetails = fanganDetails;
        }

        public static class ExpertBean implements Serializable {
            /**
             * id : 10
             * name : 赢超
             * status : 1
             * photoUrl : https://img.xiaobaicp.com/expert/photo6684.png
             */

            private int id;
            private String name;
            private int status;
            private String photoUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }
        }

        public static class FanganDetailsBean  implements Serializable{
            /**
             * multi : 1
             * touMoney : 400
             * leiMoney : 400
             * zhuanMoney : 900
             * status : 0
             * qihao : 2018032087
             * lastUpdateDate : 20180320031622
             */

            private int multi;
            private int touMoney;
            private int leiMoney;
            private int zhuanMoney;
            private int status;
            private String qihao;
            private String lastUpdateDate;

            public int getMulti() {
                return multi;
            }

            public void setMulti(int multi) {
                this.multi = multi;
            }

            public int getTouMoney() {
                return touMoney;
            }

            public void setTouMoney(int touMoney) {
                this.touMoney = touMoney;
            }

            public int getLeiMoney() {
                return leiMoney;
            }

            public void setLeiMoney(int leiMoney) {
                this.leiMoney = leiMoney;
            }

            public int getZhuanMoney() {
                return zhuanMoney;
            }

            public void setZhuanMoney(int zhuanMoney) {
                this.zhuanMoney = zhuanMoney;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getQihao() {
                return qihao;
            }

            public void setQihao(String qihao) {
                this.qihao = qihao;
            }

            public String getLastUpdateDate() {
                return lastUpdateDate;
            }

            public void setLastUpdateDate(String lastUpdateDate) {
                this.lastUpdateDate = lastUpdateDate;
            }
        }
    }
}
