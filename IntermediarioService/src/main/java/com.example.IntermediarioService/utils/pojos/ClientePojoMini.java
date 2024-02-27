package com.example.IntermediarioService.utils.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientePojoMini {
    @JsonProperty("iban")
    private String iban;

    @JsonProperty("numeroDeConta")
    private String numeroDeConta;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNumeroDeConta() {
        return numeroDeConta;
    }

    public void setNumeroDeConta(String numeroDeConta) {
        this.numeroDeConta = numeroDeConta;
    }


}
