package com.example.KuzolaBankService.enums;

public enum DetalhesBanco {

    IDENTIFICADOR_DO_BANCO(1003);

    private int identificadorDoBanco;

    DetalhesBanco(int identificadorDoBanco ){
        this.identificadorDoBanco = identificadorDoBanco;
    }

    public int getIdentificadorDoBanco() {
        return identificadorDoBanco;
    }
}
