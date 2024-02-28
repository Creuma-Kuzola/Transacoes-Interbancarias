package com.example.IntermediarioService.utils.pojos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferenciaHistoricoCreditoComponent {

    private List<TransferenciaResponseHistoricoCredito> transferenciaResponseHistoricoCreditoList;

    public List<TransferenciaResponseHistoricoCredito> getTransferenciaResponseHistoricoList() {
        return transferenciaResponseHistoricoCreditoList;
    }

    public void setTransferenciaResponseHistoricoList(List<TransferenciaResponseHistoricoCredito> transferenciaResponseHistoricoCreditoList) {
        this.transferenciaResponseHistoricoCreditoList = transferenciaResponseHistoricoCreditoList;
    }
}
