package com.example.IntermediarioService.utils.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;


public class SaldoResponse {

    @JsonProperty("saldoContabilistico")
    private BigDecimal saldoContabilistico;

    @JsonProperty("saldoDisponivel")
    private BigDecimal saldoDisponivel;

    public BigDecimal getSaldoContabilistico() {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(BigDecimal saldoContabilistico) {
        this.saldoContabilistico = saldoContabilistico;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    @Override
    public String toString() {
        return "SaldoResponse{" +
                "saldoContabilistico=" + saldoContabilistico +
                ", saldoDisponivel=" + saldoDisponivel +
                '}';
    }
}
