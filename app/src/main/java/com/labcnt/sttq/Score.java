package com.labcnt.sttq;

public class Score {

    String userId;
    String userName;
    String userScoreL;
    String userScoreS;
    String userScoreR;
    String userScoreT;

    public Score() {

    }

    public Score(String userId, String userName, String userScoreL, String userScoreS, String userScoreR, String userScoreT) {
        this.userId = userId;
        this.userName = userName;
        this.userScoreL = userScoreL;
        this.userScoreS = userScoreS;
        this.userScoreR = userScoreR;
        this.userScoreT = userScoreT;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserScoreL() {
        return userScoreL;
    }

    public void setUserScoreL(String userScoreL) {
        this.userScoreL = userScoreL;
    }

    public String getUserScoreS() {
        return userScoreS;
    }

    public void setUserScoreS(String userScoreS) {
        this.userScoreS = userScoreS;
    }

    public String getUserScoreR() {
        return userScoreR;
    }

    public void setUserScoreR(String userScoreR) {
        this.userScoreR = userScoreR;
    }

    public String getUserScoreT() {
        return userScoreT;
    }

    public void setUserScoreT(String userScoreT) {
        this.userScoreT = userScoreT;
    }
}

