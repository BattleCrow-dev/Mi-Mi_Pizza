package com.battlecrow.mimipizza;

public class UserData {
    private final String accountLogin;
    private final String accountPassword;
    private final boolean admin;
    private boolean logined;

    public UserData() {
        this.accountLogin = "";
        this.accountPassword = "";
        this.admin = false;
        this.logined = false;
    }
    public UserData(String accountLogin, String accountPassword, boolean isAdmin, boolean logined) {
        this.accountLogin = accountLogin;
        this.accountPassword = accountPassword;
        this.admin = isAdmin;
        this.logined = logined;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public boolean isAdmin() {
        return admin;
    }
    public boolean isLogined() {
        return logined;
    }
    public void setLogined(boolean value) {
        logined = value;
    }
}
