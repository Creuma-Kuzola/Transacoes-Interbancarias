package com.example.KuzolaBankService.config.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransferenciaMessage {
    Map<String, String> message;
    public TransferenciaMessage() {
        message = new HashMap<>();
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
