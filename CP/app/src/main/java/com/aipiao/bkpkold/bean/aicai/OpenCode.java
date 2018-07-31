package com.aipiao.bkpkold.bean.aicai;

import java.io.Serializable;
import java.util.List;

/**
 * 开奖号码
 * Created by caihui on 2018/3/21.
 */

public class OpenCode implements Serializable{


    /**
     * code : 0000
     * kaijiang : [{"lot":"02,16,18,19,27,30#14","catId":11,"qihao":"2018031","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"7.22亿"},{"lot":"03,08,13,17,23#05,11","catId":1007,"qihao":"2018031","kj_date":"2018-03-19","qh_endate":"03-19","jiangci":"51.84亿"},{"sjh":"5,4,8","lot":"4,7,3","catId":1,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"4,1,2","catId":1005,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"4,1,2,2,9","catId":1006,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"01,04,05,07,11,22,27#13","catId":13,"qihao":"2018031","kj_date":"2018-03-19","qh_endate":"03-19","jiangci":"148万"},{"lot":"3,7,0,8,6,8,1","catId":1008,"qihao":"2018031","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"354万"},{"lot":"1,6,0,0,6,1,9","catId":1201,"qihao":"2018041","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"1.1亿"},{"lot":"4,3,8,9,2,6#3","catId":1207,"qihao":"2018031","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"799万"},{"lot":"08,10,11,16,18","catId":1206,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"21,猴,秋,西","catId":2001,"qihao":"2017358","kj_date":"2017-12-31","qh_endate":"12-31","jiangci":"0"},{"lot":"02,05,12,18,29,35#27","catId":2002,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"276万"},{"lot":"01,04,10,11,17","catId":2003,"qihao":"2018031","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"0"},{"lot":"12,14,24,27,31,32,33#03","catId":1203,"qihao":"2018031","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"4222万"},{"lot":"05,06,09,15,18,23,26#07","catId":1204,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"3万"},{"lot":"05,08,13,15,22","catId":1205,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20","jiangci":"3万"},{"lot":"01,08,11,12,14","catId":1209,"qihao":"2018072","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"4,8,9,4,1,9#猪","catId":1210,"qihao":"2018031","kj_date":"2018-03-19","qh_endate":"03-19"},{"lot":"0,2,9,3,2,0#3","catId":1202,"qihao":"2018021","kj_date":"2018-03-20","qh_endate":"03-20"},{"lot":"03,06,07,11,22","catId":1211,"qihao":"2018052","kj_date":"2018-02-28","qh_endate":"02-28","jiangci":"341万"}]
     */

    private String code;
    private List<KaijiangBean> kaijiang;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<KaijiangBean> getKaijiang() {
        return kaijiang;
    }

    public void setKaijiang(List<KaijiangBean> kaijiang) {
        this.kaijiang = kaijiang;
    }

    public static class KaijiangBean implements Serializable {
        /**
         * lot : 02,16,18,19,27,30#14
         * catId : 11
         * qihao : 2018031
         * kj_date : 2018-03-20
         * qh_endate : 03-20
         * jiangci : 7.22亿
         * sjh : 5,4,8
         */

        private String lot;
        private int catId;
        private String qihao;
        private String kj_date;
        private String qh_endate;
        private String jiangci;
        private String sjh;

        public String getLot() {
            return lot;
        }

        public void setLot(String lot) {
            this.lot = lot;
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

        public String getKj_date() {
            return kj_date;
        }

        public void setKj_date(String kj_date) {
            this.kj_date = kj_date;
        }

        public String getQh_endate() {
            return qh_endate;
        }

        public void setQh_endate(String qh_endate) {
            this.qh_endate = qh_endate;
        }

        public String getJiangci() {
            return jiangci;
        }

        public void setJiangci(String jiangci) {
            this.jiangci = jiangci;
        }

        public String getSjh() {
            return sjh;
        }

        public void setSjh(String sjh) {
            this.sjh = sjh;
        }
    }
}
