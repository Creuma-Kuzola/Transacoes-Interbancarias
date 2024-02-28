package com.example.IntermediarioService.utils.pojos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferenciaHistoricoDebitoComponent {

    private List<TransferenciaResponseHistoricoDebito> transferenciaResponseHistoricoDebitoList;

    public List<TransferenciaResponseHistoricoDebito> getTransferenciaResponseHistoricoList() {
        return transferenciaResponseHistoricoDebitoList;
    }

    public void setTransferenciaResponseHistoricoList(List<TransferenciaResponseHistoricoDebito> transferenciaResponseHistoricoDebitoList) {
        this.transferenciaResponseHistoricoDebitoList = transferenciaResponseHistoricoDebitoList;
    }
}
