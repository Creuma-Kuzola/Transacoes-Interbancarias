package com.example.KuzolaBankService.utils.response;

import com.example.KuzolaBankService.entities.ContaBancaria;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ContaBancariaResponse {

   private String iban;
   private BigInteger numeroDeConta;

   public static ContaBancariaResponse convertingIntoContaBancariaResponse(ContaBancaria contaBancaria){

       ContaBancariaResponse contaBancariaResponse = new ContaBancariaResponse();
       contaBancariaResponse.setNumeroDeConta(contaBancaria.getNumeroDeConta());
       contaBancariaResponse.setIban(contaBancaria.getIban());

       return contaBancariaResponse;
   }

    public static SaldoResponse convertingIntoSaldoResponse(ContaBancaria contaBancaria){

        SaldoResponse saldoResponse = new SaldoResponse();
        saldoResponse.setSaldoDisponivel(contaBancaria.getSaldoDisponivel());
        saldoResponse.setSaldoContabilistico(contaBancaria.getSaldoContabilistico());

        return saldoResponse;
    }







}
