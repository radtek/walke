package com.aipiao.bkpkold.bean.match;

import java.io.Serializable;

/**
 * Created by caihui on 2018/4/10.
 */

public class MatchInfo implements Serializable {


    /**
     * code : 3001
     * message : 请求成功
     * title : {"id":881369,"matchTime":"2018-04-11 01:00:00.0","matchState":null,"country":"","city":"","union":"瑞典超","lunCi":"","home":"哥德堡","homeRank":"4","awary":"哈马比","awaryRank":"3","homeScore":null,"awaryScore":null,"startTime":"2018-04-11 01:00:00.0","homePhoto":"http://www.zycpimg.com/img/teamlogo/14605305027332019.jpg","guestPhoto":"http://www.zycpimg.com/img/teamlogo/14605305025912014.jpg","status":"17","gymName":"","tem":"","weather":"0","season_id":"6676","league_id":"1048","homeId":"2019","awaryId":"2014","turn_id":"82741"}
     */

    private int code;
    private String message;
    private TitleBean title;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TitleBean getTitle() {
        return title;
    }

    public void setTitle(TitleBean title) {
        this.title = title;
    }

    public static class TitleBean  implements Serializable{
        /**
         * id : 881369
         * matchTime : 2018-04-11 01:00:00.0
         * matchState : null
         * country :
         * city :
         * union : 瑞典超
         * lunCi :
         * home : 哥德堡
         * homeRank : 4
         * awary : 哈马比
         * awaryRank : 3
         * homeScore : null
         * awaryScore : null
         * startTime : 2018-04-11 01:00:00.0
         * homePhoto : http://www.zycpimg.com/img/teamlogo/14605305027332019.jpg
         * guestPhoto : http://www.zycpimg.com/img/teamlogo/14605305025912014.jpg
         * status : 17
         * gymName :
         * tem :
         * weather : 0
         * season_id : 6676
         * league_id : 1048
         * homeId : 2019
         * awaryId : 2014
         * turn_id : 82741
         */

        private int id;
        private String matchTime;
        private Object matchState;
        private String country;
        private String city;
        private String union;
        private String lunCi;
        private String home;
        private String homeRank;
        private String awary;
        private String awaryRank;
        private Object homeScore;
        private Object awaryScore;
        private String startTime;
        private String homePhoto;
        private String guestPhoto;
        private String status;
        private String gymName;
        private String tem;
        private String weather;
        private String season_id;
        private String league_id;
        private String homeId;
        private String awaryId;
        private String turn_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public Object getMatchState() {
            return matchState;
        }

        public void setMatchState(Object matchState) {
            this.matchState = matchState;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUnion() {
            return union;
        }

        public void setUnion(String union) {
            this.union = union;
        }

        public String getLunCi() {
            return lunCi;
        }

        public void setLunCi(String lunCi) {
            this.lunCi = lunCi;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getHomeRank() {
            return homeRank;
        }

        public void setHomeRank(String homeRank) {
            this.homeRank = homeRank;
        }

        public String getAwary() {
            return awary;
        }

        public void setAwary(String awary) {
            this.awary = awary;
        }

        public String getAwaryRank() {
            return awaryRank;
        }

        public void setAwaryRank(String awaryRank) {
            this.awaryRank = awaryRank;
        }

        public Object getHomeScore() {
            return homeScore;
        }

        public void setHomeScore(Object homeScore) {
            this.homeScore = homeScore;
        }

        public Object getAwaryScore() {
            return awaryScore;
        }

        public void setAwaryScore(Object awaryScore) {
            this.awaryScore = awaryScore;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getHomePhoto() {
            return homePhoto;
        }

        public void setHomePhoto(String homePhoto) {
            this.homePhoto = homePhoto;
        }

        public String getGuestPhoto() {
            return guestPhoto;
        }

        public void setGuestPhoto(String guestPhoto) {
            this.guestPhoto = guestPhoto;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGymName() {
            return gymName;
        }

        public void setGymName(String gymName) {
            this.gymName = gymName;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getSeason_id() {
            return season_id;
        }

        public void setSeason_id(String season_id) {
            this.season_id = season_id;
        }

        public String getLeague_id() {
            return league_id;
        }

        public void setLeague_id(String league_id) {
            this.league_id = league_id;
        }

        public String getHomeId() {
            return homeId;
        }

        public void setHomeId(String homeId) {
            this.homeId = homeId;
        }

        public String getAwaryId() {
            return awaryId;
        }

        public void setAwaryId(String awaryId) {
            this.awaryId = awaryId;
        }

        public String getTurn_id() {
            return turn_id;
        }

        public void setTurn_id(String turn_id) {
            this.turn_id = turn_id;
        }
    }
}
