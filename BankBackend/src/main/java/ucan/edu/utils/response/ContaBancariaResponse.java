package ucan.edu.utils.response;

import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.Transferencia;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ContaBancariaResponse {

   private String iban;
   private Integer numeroDeConta;

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getNumeroDeConta() {
        return numeroDeConta;
    }

    public void setNumeroDeConta(Integer numeroDeConta) {
        this.numeroDeConta = numeroDeConta;
    }
}
