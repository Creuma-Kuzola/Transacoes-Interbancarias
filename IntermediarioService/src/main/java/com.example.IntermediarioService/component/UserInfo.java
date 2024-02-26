package com.example.IntermediarioService.component;


import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserInfo {

    HashMap<String, String> userInfo;

    public UserInfo() {

        userInfo = new HashMap<>();
    }

    public HashMap<String, String> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(HashMap<String, String> userInfo) {
        this.userInfo = userInfo;
    }
}
