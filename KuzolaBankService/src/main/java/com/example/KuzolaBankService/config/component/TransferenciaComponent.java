package com.example.KuzolaBankService.config.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TransferenciaComponent {
    Map<String, String> transferencia;
    //777
    public TransferenciaComponent() {
        transferencia = new HashMap<>();
    }

    public Map<String, String> getTransferenciaResponse() {
        return transferencia;
    }

    public void setTransferenciaResponse(Map<String, String> transferenciaResponse) {
        this.transferencia = transferenciaResponse;
    }
}
