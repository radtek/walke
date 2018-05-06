package com.aipiao.bkpk.bean.aicai;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 * 专家
 */

public class ExpertMan  implements Serializable{


    /**
     * catId : 11111
     * code : 0000
     * datas : [{"id":"5ab00c867011bd57bddb7823","lot":"09|11","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":9,"name":"彩魄","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5312.png"},"startQihao":"2018032069","endQihao":"2018032081","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7824","lot":"04,06,07,08,11","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":18,"name":"胆神","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5629.png"},"startQihao":"2018032069","endQihao":"2018032081","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7825","lot":"08,11","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":20,"name":"玲珑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5900.png"},"startQihao":"2018032069","endQihao":"2018032080","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7826","lot":"02,04,07","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":17,"name":"姜太公","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6148.png"},"startQihao":"2018032069","endQihao":"2018032110","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7827","lot":"01,02,04,06,10","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":13,"name":"给力王","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo2992.png"},"startQihao":"2018032069","endQihao":"2018032110","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7828","lot":"01,02,05,06,07,08,10,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320031622","status":1,"expert":{"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"},"startQihao":"2018032069","endQihao":"2018032088","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781d","lot":"07|11","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320194115","status":3,"expert":{"id":2,"name":"定胆王","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6286.png"},"startQihao":"2018032068","endQihao":"2018032080","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781e","lot":"02,04,06,09,10","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320193815","status":2,"expert":{"id":15,"name":"诸葛","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo3064.png"},"startQihao":"2018032068","endQihao":"2018032080","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781f","lot":"01,11","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320193815","status":2,"expert":{"id":1,"name":"红彤彤","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5788.png"},"startQihao":"2018032068","endQihao":"2018032079","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7820","lot":"05,07,10","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320193815","status":2,"expert":{"id":19,"name":"智胜","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo1288.png"},"startQihao":"2018032068","endQihao":"2018032109","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7821","lot":"02,03,04,08,09","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320193815","status":2,"expert":{"id":4,"name":"麻姑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5029.png"},"startQihao":"2018032068","endQihao":"2018032109","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7822","lot":"01,02,05,06,07,08,09,10","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320193815","status":2,"expert":{"id":5,"name":"米琪","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6558.png"},"startQihao":"2018032068","endQihao":"2018032087","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7817","lot":"07|10","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":12,"name":"密宗","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5731.png"},"startQihao":"2018032067","endQihao":"2018032079","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7818","lot":"02,03,04,07,11","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"},"startQihao":"2018032067","endQihao":"2018032079","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7819","lot":"04,05","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":7,"name":"烽火轮","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7798.png"},"startQihao":"2018032067","endQihao":"2018032078","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781a","lot":"05,06,10","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":14,"name":"公孙","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7129.png"},"startQihao":"2018032067","endQihao":"2018032108","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781b","lot":"01,06,08,09,11","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":16,"name":"慕容","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7112.png"},"startQihao":"2018032067","endQihao":"2018032108","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb781c","lot":"01,02,04,05,07,08,09,10","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320192815","status":2,"expert":{"id":11,"name":"彩霸","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5385.png"},"startQihao":"2018032067","endQihao":"2018032086","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7811","lot":"05|08","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320191815","status":2,"expert":{"id":9,"name":"彩魄","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5312.png"},"startQihao":"2018032066","endQihao":"2018032078","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7812","lot":"01,05,06,08,10","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320191815","status":2,"expert":{"id":5,"name":"米琪","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6558.png"},"startQihao":"2018032066","endQihao":"2018032078","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7813","lot":"01,07","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320191815","status":2,"expert":{"id":13,"name":"给力王","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo2992.png"},"startQihao":"2018032066","endQihao":"2018032077","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7814","lot":"01,09,11","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320191815","status":2,"expert":{"id":20,"name":"玲珑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5900.png"},"startQihao":"2018032066","endQihao":"2018032107","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7815","lot":"01,03,04,05,09","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320191815","status":2,"expert":{"id":6,"name":"彩神通","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5140.png"},"startQihao":"2018032066","endQihao":"2018032107","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7816","lot":"02,03,05,07,08,09,10,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320192215","status":3,"expert":{"id":3,"name":"保罗","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo3846.png"},"startQihao":"2018032066","endQihao":"2018032085","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780b","lot":"02|05","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320190815","status":2,"expert":{"id":15,"name":"诸葛","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo3064.png"},"startQihao":"2018032065","endQihao":"2018032077","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780c","lot":"03,04,05,07,08","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320190815","status":2,"expert":{"id":2,"name":"定胆王","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6286.png"},"startQihao":"2018032065","endQihao":"2018032077","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780d","lot":"05,09","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320190815","status":2,"expert":{"id":4,"name":"麻姑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5029.png"},"startQihao":"2018032065","endQihao":"2018032076","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780e","lot":"02,03,09","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320190815","status":2,"expert":{"id":17,"name":"姜太公","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6148.png"},"startQihao":"2018032065","endQihao":"2018032106","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780f","lot":"03,04,05,06,09","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320190815","status":2,"expert":{"id":19,"name":"智胜","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo1288.png"},"startQihao":"2018032065","endQihao":"2018032106","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7810","lot":"01,02,03,05,07,08,10,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320192215","status":3,"expert":{"id":8,"name":"追霸","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5672.png"},"startQihao":"2018032065","endQihao":"2018032084","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7805","lot":"04|10","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320191215","status":3,"expert":{"id":6,"name":"彩神通","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5140.png"},"startQihao":"2018032064","endQihao":"2018032076","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7806","lot":"03,04,05,07,09","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320185815","status":2,"expert":{"id":3,"name":"保罗","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo3846.png"},"startQihao":"2018032064","endQihao":"2018032076","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7807","lot":"07,08","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320185815","status":2,"expert":{"id":16,"name":"慕容","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7112.png"},"startQihao":"2018032064","endQihao":"2018032075","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7808","lot":"02,04,08","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320185815","status":2,"expert":{"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"},"startQihao":"2018032064","endQihao":"2018032105","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7809","lot":"01,02,04,10,11","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320185815","status":2,"expert":{"id":18,"name":"胆神","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5629.png"},"startQihao":"2018032064","endQihao":"2018032105","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb780a","lot":"01,02,03,05,06,07,08,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320194115","status":3,"expert":{"id":1,"name":"红彤彤","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5788.png"},"startQihao":"2018032064","endQihao":"2018032083","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77ff","lot":"02|06","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320190215","status":3,"expert":{"id":11,"name":"彩霸","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5385.png"},"startQihao":"2018032063","endQihao":"2018032075","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7800","lot":"01,03,06,09,10","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320190215","status":3,"expert":{"id":8,"name":"追霸","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5672.png"},"startQihao":"2018032063","endQihao":"2018032075","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7801","lot":"02,11","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320192215","status":3,"expert":{"id":9,"name":"彩魄","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5312.png"},"startQihao":"2018032063","endQihao":"2018032074","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7802","lot":"01,04,06","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320190215","status":3,"expert":{"id":14,"name":"公孙","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7129.png"},"startQihao":"2018032063","endQihao":"2018032104","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7803","lot":"02,03,06,08,10","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320184815","status":2,"expert":{"id":20,"name":"玲珑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5900.png"},"startQihao":"2018032063","endQihao":"2018032104","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb7804","lot":"01,02,03,05,06,08,10,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320192215","status":3,"expert":{"id":7,"name":"烽火轮","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo7798.png"},"startQihao":"2018032063","endQihao":"2018032082","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77f9","lot":"03|06","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320190215","status":3,"expert":{"id":4,"name":"麻姑","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5029.png"},"startQihao":"2018032062","endQihao":"2018032074","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77fa","lot":"03,06,07,09,10","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320183815","status":2,"expert":{"id":1,"name":"红彤彤","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5788.png"},"startQihao":"2018032062","endQihao":"2018032074","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77fb","lot":"10,11","playType":8002,"money":47400,"addDate":"20180320031622","lastUpdateDate":"20180320192215","status":3,"expert":{"id":12,"name":"密宗","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5731.png"},"startQihao":"2018032062","endQihao":"2018032073","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77fc","lot":"06,08,11","playType":8003,"money":47200,"addDate":"20180320031622","lastUpdateDate":"20180320183815","status":2,"expert":{"id":13,"name":"给力王","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo2992.png"},"startQihao":"2018032062","endQihao":"2018032103","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77fd","lot":"01,06,07,09,11","playType":8004,"money":479000,"addDate":"20180320031622","lastUpdateDate":"20180320183815","status":2,"expert":{"id":17,"name":"姜太公","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6148.png"},"startQihao":"2018032062","endQihao":"2018032103","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77fe","lot":"01,02,05,06,07,08,10,11","playType":8008,"money":81200,"addDate":"20180320031622","lastUpdateDate":"20180320183815","status":2,"expert":{"id":5,"name":"米琪","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6558.png"},"startQihao":"2018032062","endQihao":"2018032081","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77f3","lot":"07|10","playType":8001,"money":84000,"addDate":"20180320031622","lastUpdateDate":"20180320185115","status":3,"expert":{"id":15,"name":"诸葛","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo3064.png"},"startQihao":"2018032061","endQihao":"2018032073","catId":0,"viewCount":0},{"id":"5ab00c867011bd57bddb77f4","lot":"01,02,07,08,09","playType":8011,"money":420000,"addDate":"20180320031622","lastUpdateDate":"20180320182815","status":2,"expert":{"id":10,"name":"赢超","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo6684.png"},"startQihao":"2018032061","endQihao":"2018032073","catId":0,"viewCount":0}]
     */

    private int catId;
    private String code;
    private List<DatasBean> datas;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean   implements Serializable{
        /**
         * id : 5ab00c867011bd57bddb7823
         * lot : 09|11
         * playType : 8001
         * money : 84000
         * addDate : 20180320031622
         * lastUpdateDate : 20180320031622
         * status : 1
         * expert : {"id":9,"name":"彩魄","status":1,"photoUrl":"https://img.xiaobaicp.com/expert/photo5312.png"}
         * startQihao : 2018032069
         * endQihao : 2018032081
         * catId : 0
         * viewCount : 0
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

        public static class ExpertBean  implements Serializable{
            /**
             * id : 9
             * name : 彩魄
             * status : 1
             * photoUrl : https://img.xiaobaicp.com/expert/photo5312.png
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
    }
}
