package com.example.KuzolaBankService.controllers;



import com.example.KuzolaBankService.config.component.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class UserSessionInfoController {
    @Autowired
    UserInfo userInfo;
    @GetMapping
    public String getSession()
    {
        return " " +userInfo.getUserInfo().values();
    }
    @GetMapping("/{key}")
    public  String getSessionByKey(@PathVariable("key") String key)
    {
        return  " " +userInfo.getUserInfo().get(key);
    }
}
