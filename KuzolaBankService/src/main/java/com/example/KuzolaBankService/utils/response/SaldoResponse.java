package com.example.KuzolaBankService.utils.response;

import com.example.KuzolaBankService.entities.ContaBancaria;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SaldoResponse {

    private BigDecimal saldoContabilistico;
    private BigDecimal saldoDisponivel;


}
