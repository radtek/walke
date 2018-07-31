package com.aipiao.bkpkold.bean.aicai;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caihui on 2018/3/20.
 */

public class ForeCastManDetail  implements Serializable{


    /**
     * code : 0000
     * lastRecoder : [{"dashiId":"5924009e88b768488421c40e","addTime":"20180318214105","yuceType":93105,"catId":1,"qihao":"2018071","lottery":"1,9","hitCount":1,"money":8,"updateTime":"20180319212821","kaijiangNum":"7,7,9"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180317214105","yuceType":93105,"catId":1,"qihao":"2018070","lottery":"0,7","hitCount":1,"money":6,"updateTime":"20180318212827","kaijiangNum":"5,6,7"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180316214105","yuceType":93105,"catId":1,"qihao":"2018069","lottery":"1,5","hitCount":2,"money":8,"updateTime":"20180317213023","kaijiangNum":"1,1,9"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180315214105","yuceType":93105,"catId":1,"qihao":"2018068","lottery":"3,4","hitCount":2,"money":2,"updateTime":"20180316212820","kaijiangNum":"4,4,8"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180314214105","yuceType":93105,"catId":1,"qihao":"2018067","lottery":"0,8","hitCount":2,"money":8,"updateTime":"20180315213027","kaijiangNum":"8,5,8"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180313214105","yuceType":93105,"catId":1,"qihao":"2018066","lottery":"5,8","hitCount":1,"money":2,"updateTime":"20180314213421","kaijiangNum":"0,9,5"},{"dashiId":"5924009e88b768488421c40e","addTime":"20180312214105","yuceType":93105,"catId":1,"qihao":"2018065","lottery":"1,4","hitCount":1,"money":6,"updateTime":"20180313213024","kaijiangNum":"8,4,8"}]
     * currentYuce : {"_id":"5aafbdf17011bd57bddadfe4","dashiId":"5924009e88b768488421c40e","addTime":"20180319214105","yuceType":93105,"catId":1,"qihao":"2018072","lottery":"1,8","hitCount":0,"money":2,"updateTime":"20180319214105","isYuce":true,"isOrder":false,"couponCount":0,"boundStatus":"待开奖"}
     */

    private String code;
    private CurrentYuceBean currentYuce;
    private List<LastRecoderBean> lastRecoder;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CurrentYuceBean getCurrentYuce() {
        return currentYuce;
    }

    public void setCurrentYuce(CurrentYuceBean currentYuce) {
        this.currentYuce = currentYuce;
    }

    public List<LastRecoderBean> getLastRecoder() {
        return lastRecoder;
    }

    public void setLastRecoder(List<LastRecoderBean> lastRecoder) {
        this.lastRecoder = lastRecoder;
    }

    public static class CurrentYuceBean {
        /**
         * _id : 5aafbdf17011bd57bddadfe4
         * dashiId : 5924009e88b768488421c40e
         * addTime : 20180319214105
         * yuceType : 93105
         * catId : 1
         * qihao : 2018072
         * lottery : 1,8
         * hitCount : 0
         * money : 2
         * updateTime : 20180319214105
         * isYuce : true
         * isOrder : false
         * couponCount : 0
         * boundStatus : 待开奖
         */

        private String _id;
        private String dashiId;
        private String addTime;
        private int yuceType;
        private int catId;
        private String qihao;
        private String lottery;
        private int hitCount;
        private int money;
        private String updateTime;
        private boolean isYuce;
        private boolean isOrder;
        private int couponCount;
        private String boundStatus;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDashiId() {
            return dashiId;
        }

        public void setDashiId(String dashiId) {
            this.dashiId = dashiId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getYuceType() {
            return yuceType;
        }

        public void setYuceType(int yuceType) {
            this.yuceType = yuceType;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public String getQihao() {
            return qihao;
        }

        public void setQihao(String qihao) {
            this.qihao = qihao;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public int getHitCount() {
            return hitCount;
        }

        public void setHitCount(int hitCount) {
            this.hitCount = hitCount;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isIsYuce() {
            return isYuce;
        }

        public void setIsYuce(boolean isYuce) {
            this.isYuce = isYuce;
        }

        public boolean isIsOrder() {
            return isOrder;
        }

        public void setIsOrder(boolean isOrder) {
            this.isOrder = isOrder;
        }

        public int getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(int couponCount) {
            this.couponCount = couponCount;
        }

        public String getBoundStatus() {
            return boundStatus;
        }

        public void setBoundStatus(String boundStatus) {
            this.boundStatus = boundStatus;
        }
    }

    public static class LastRecoderBean {
        /**
         * dashiId : 5924009e88b768488421c40e
         * addTime : 20180318214105
         * yuceType : 93105
         * catId : 1
         * qihao : 2018071
         * lottery : 1,9
         * hitCount : 1
         * money : 8
         * updateTime : 20180319212821
         * kaijiangNum : 7,7,9
         */

        private String dashiId;
        private String addTime;
        private int yuceType;
        private int catId;
        private String qihao;
        private String lottery;
        private int hitCount;
        private int money;
        private String updateTime;
        private String kaijiangNum;

        public String getDashiId() {
            return dashiId;
        }

        public void setDashiId(String dashiId) {
            this.dashiId = dashiId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getYuceType() {
            return yuceType;
        }

        public void setYuceType(int yuceType) {
            this.yuceType = yuceType;
        }

        public int getCatId() {
            return catId;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public String getQihao() {
            return qihao;
        }

        public void setQihao(String qihao) {
            this.qihao = qihao;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }

        public int getHitCount() {
            return hitCount;
        }

        public void setHitCount(int hitCount) {
            this.hitCount = hitCount;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getKaijiangNum() {
            return kaijiangNum;
        }

        public void setKaijiangNum(String kaijiangNum) {
            this.kaijiangNum = kaijiangNum;
        }
    }
}
