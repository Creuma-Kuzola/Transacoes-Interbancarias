package com.example.KuzolaBankService.utils;

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
   private BigDecimal saldoContabilistico;
   private BigDecimal saldoDisponivel;

   public static ContaBancariaResponse convertingIntoContaBancariaResponse(ContaBancaria contaBancaria){

       ContaBancariaResponse contaBancariaResponse = new ContaBancariaResponse();
       contaBancariaResponse.setNumeroDeConta(contaBancaria.getNumeroDeConta());
       contaBancariaResponse.setIban(contaBancaria.getIban());
       contaBancariaResponse.setSaldoContabilistico(contaBancaria.getSaldoContabilistico());
       contaBancariaResponse.setSaldoDisponivel(contaBancaria.getSaldoDisponivel());

       return contaBancariaResponse;
   }


}
