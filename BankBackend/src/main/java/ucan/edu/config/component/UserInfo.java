package ucan.edu.config.component;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserInfo {

    Map<String, String> userInfo;

    public UserInfo() {

        userInfo = new HashMap<>();
    }

    public Map<String, String> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Map<String, String> userInfo) {
        this.userInfo = userInfo;
    }
}