package com.aipiao.bkpkold.bean.match;

/**
 * Created by caihui on 2018/4/10.
 */

public class MatchIntegral {

    /**
     * code : 3001
     * message : 请求成功
     * dw_home : {"shengLv":"","shi":"","ping":"","lose":"","name":"","rank":"","sai":"","saishi":"","win":"","jin":""}
     * dw_away : {"shengLv":"","shi":"","ping":"","lose":"","name":"","rank":"","sai":"","saishi":"","win":"","jin":""}
     * jf_away : {"shi":"1","ping":"0","lose":"0","name":"哈马比","rank":"5","sai":1,"saishi":null,"jf":"3","win":"1","jin":"3"}
     * jf_home : {"shi":"1","ping":"0","lose":"0","name":"哥德堡","rank":"6","sai":1,"saishi":null,"jf":"3","win":"1","jin":"3"}
     */

    private String code;
    private String message;
    private DwHomeBean dw_home;
    private DwAwayBean dw_away;
    private JfAwayBean jf_away;
    private JfHomeBean jf_home;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DwHomeBean getDw_home() {
        return dw_home;
    }

    public void setDw_home(DwHomeBean dw_home) {
        this.dw_home = dw_home;
    }

    public DwAwayBean getDw_away() {
        return dw_away;
    }

    public void setDw_away(DwAwayBean dw_away) {
        this.dw_away = dw_away;
    }

    public JfAwayBean getJf_away() {
        return jf_away;
    }

    public void setJf_away(JfAwayBean jf_away) {
        this.jf_away = jf_away;
    }

    public JfHomeBean getJf_home() {
        return jf_home;
    }

    public void setJf_home(JfHomeBean jf_home) {
        this.jf_home = jf_home;
    }

    public static class DwHomeBean {
        /**
         * shengLv :
         * shi :
         * ping :
         * lose :
         * name :
         * rank :
         * sai :
         * saishi :
         * win :
         * jin :
         */

        private String shengLv;
        private String shi;
        private String ping;
        private String lose;
        private String name;
        private String rank;
        private String sai;
        private String saishi;
        private String win;
        private String jin;

        public String getShengLv() {
            return shengLv;
        }

        public void setShengLv(String shengLv) {
            this.shengLv = shengLv;
        }

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getPing() {
            return ping;
        }

        public void setPing(String ping) {
            this.ping = ping;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getSai() {
            return sai;
        }

        public void setSai(String sai) {
            this.sai = sai;
        }

        public String getSaishi() {
            return saishi;
        }

        public void setSaishi(String saishi) {
            this.saishi = saishi;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getJin() {
            return jin;
        }

        public void setJin(String jin) {
            this.jin = jin;
        }
    }

    public static class DwAwayBean {
        /**
         * shengLv :
         * shi :
         * ping :
         * lose :
         * name :
         * rank :
         * sai :
         * saishi :
         * win :
         * jin :
         */

        private String shengLv;
        private String shi;
        private String ping;
        private String lose;
        private String name;
        private String rank;
        private String sai;
        private String saishi;
        private String win;
        private String jin;

        public String getShengLv() {
            return shengLv;
        }

        public void setShengLv(String shengLv) {
            this.shengLv = shengLv;
        }

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getPing() {
            return ping;
        }

        public void setPing(String ping) {
            this.ping = ping;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getSai() {
            return sai;
        }

        public void setSai(String sai) {
            this.sai = sai;
        }

        public String getSaishi() {
            return saishi;
        }

        public void setSaishi(String saishi) {
            this.saishi = saishi;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getJin() {
            return jin;
        }

        public void setJin(String jin) {
            this.jin = jin;
        }
    }

    public static class JfAwayBean {
        /**
         * shi : 1
         * ping : 0
         * lose : 0
         * name : 哈马比
         * rank : 5
         * sai : 1
         * saishi : null
         * jf : 3
         * win : 1
         * jin : 3
         */

        private String shi;
        private String ping;
        private String lose;
        private String name;
        private String rank;
        private String sai;
        private String saishi;
        private String jf;
        private String win;
        private String jin;

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getPing() {
            return ping;
        }

        public void setPing(String ping) {
            this.ping = ping;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getSai() {
            return sai;
        }

        public void setSai(String sai) {
            this.sai = sai;
        }

        public String getSaishi() {
            return saishi;
        }

        public void setSaishi(String saishi) {
            this.saishi = saishi;
        }

        public String getJf() {
            return jf;
        }

        public void setJf(String jf) {
            this.jf = jf;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getJin() {
            return jin;
        }

        public void setJin(String jin) {
            this.jin = jin;
        }
    }

    public static class JfHomeBean {
        /**
         * shi : 1
         * ping : 0
         * lose : 0
         * name : 哥德堡
         * rank : 6
         * sai : 1
         * saishi : null
         * jf : 3
         * win : 1
         * jin : 3
         */

        private String shi;
        private String ping;
        private String lose;
        private String name;
        private String rank;
        private String sai;
        private String saishi;
        private String jf;
        private String win;
        private String jin;

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getPing() {
            return ping;
        }

        public void setPing(String ping) {
            this.ping = ping;
        }

        public String getLose() {
            return lose;
        }

        public void setLose(String lose) {
            this.lose = lose;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getSai() {
            return sai;
        }

        public void setSai(String sai) {
            this.sai = sai;
        }

        public String getSaishi() {
            return saishi;
        }

        public void setSaishi(String saishi) {
            this.saishi = saishi;
        }

        public String getJf() {
            return jf;
        }

        public void setJf(String jf) {
            this.jf = jf;
        }

        public String getWin() {
            return win;
        }

        public void setWin(String win) {
            this.win = win;
        }

        public String getJin() {
            return jin;
        }

        public void setJin(String jin) {
            this.jin = jin;
        }
    }
}
