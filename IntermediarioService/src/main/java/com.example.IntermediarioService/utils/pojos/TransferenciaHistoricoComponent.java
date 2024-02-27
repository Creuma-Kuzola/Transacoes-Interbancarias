package com.example.IntermediarioService.utils.pojos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferenciaHistoricoComponent {

    private List<TransferenciaResponseHistorico> transferenciaResponseHistoricoList;

    public List<TransferenciaResponseHistorico> getTransferenciaResponseHistoricoList() {
        return transferenciaResponseHistoricoList;
    }

    public void setTransferenciaResponseHistoricoList(List<TransferenciaResponseHistorico> transferenciaResponseHistoricoList) {
        this.transferenciaResponseHistoricoList = transferenciaResponseHistoricoList;
    }
}
