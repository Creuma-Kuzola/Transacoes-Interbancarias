package com.example.IntermediarioService.utils.pojos;

import org.springframework.stereotype.Component;

@Component

public class SaldoResponseComponent {

    private SaldoResponse saldoResponse;

    public SaldoResponse getSaldoResponse() {
        return saldoResponse;
    }

    public void setSaldoResponse(SaldoResponse saldoResponse) {
        this.saldoResponse = saldoResponse;
    }

    @Override
    public String toString() {
        return "SaldoResponseComponent{" +
                "saldoResponse=" + saldoResponse +
                '}';
    }
}
