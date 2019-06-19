package com.ego.commons.pojo;

import javafx.beans.binding.StringBinding;

/**
 * @Auther:S
 * @Date:19/6/19
 */
public class FtpLoggin {
    private String host;
    private int port;
    private String userName;
    private String password;
    private String userHome;

    public FtpLoggin() {
        host="localhost";
        port=21;
        userName="ftp1";
        password="123456";
        userHome="/Users/"+userName+"/ftp";
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }
}
