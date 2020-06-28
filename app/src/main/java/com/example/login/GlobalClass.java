package com.example.login;

import android.app.Application;

public class GlobalClass extends Application {
    private String networkIP = "https:/192.168.1.7:53835/graphql";

    public String getNetworkIP() {
        return networkIP;
    }

    public void setNetworkIP(String networkIP) {
        this.networkIP = networkIP;
    }

}
