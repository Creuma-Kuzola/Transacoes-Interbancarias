package com.example.KuzolaBankService.config.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TransferenciaResponseComponent {
    Map<String, String> transferenciaResponse;

    public TransferenciaResponseComponent() {
        transferenciaResponse = new HashMap<>();
    }

    public Map<String, String> getTransferenciaResponse() {
        return transferenciaResponse;
    }

    public void setTransferenciaResponse(Map<String, String> transferenciaResponse) {
        this.transferenciaResponse = transferenciaResponse;
    }
}
