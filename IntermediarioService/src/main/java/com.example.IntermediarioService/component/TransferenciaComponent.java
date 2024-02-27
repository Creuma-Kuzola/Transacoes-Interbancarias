package com.example.IntermediarioService.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class TransferenciaComponent {
    Map<String, String> transferencia;

    public TransferenciaComponent() {

        transferencia = new HashMap<>();
    }

    public Map<String, String> getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Map<String, String> transferencia) {
        this.transferencia = transferencia;
    }
}
