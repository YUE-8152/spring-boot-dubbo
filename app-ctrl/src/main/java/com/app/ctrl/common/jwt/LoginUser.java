package com.app.ctrl.common.jwt;

public class LoginUser {
    private String userId;
    private String userName;
    private String password;
    private String mobile;
    private String openId;

    public LoginUser() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public LoginUser(String userId, String userName, String password, String mobile, String openId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.mobile = mobile;
        this.openId = openId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginUser{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", openId='").append(openId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
