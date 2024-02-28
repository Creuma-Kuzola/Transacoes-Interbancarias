package com.example.IntermediarioService.utils.pojos;

import com.example.IntermediarioService.entities.Transferencia;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaComponentResponse {

    private Transferencia transferencia;

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    @Override
    public String toString() {
        return "TransferenciaComponentResponse{" +
                "transferencia=" + transferencia +
                '}';
    }
}
