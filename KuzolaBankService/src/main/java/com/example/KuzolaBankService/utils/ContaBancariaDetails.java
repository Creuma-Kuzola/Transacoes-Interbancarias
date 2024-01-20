package com.example.KuzolaBankService.utils;

import com.example.KuzolaBankService.enums.DetalhesBanco;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ContaBancariaDetails {

    private int numeroDeConta;
    private String iban;
    private DetalhesBanco identificadorDoBanco = DetalhesBanco.IDENTIFICADOR_DO_BANCO;
    private HashSet<BigInteger> listaNumerosDeConta = new HashSet<>();

    //O número de conta é composto por 11 números (12345678910)
    // Primeiro random de 1000-9000
    //Segundo random de 10000-

    /**
     *Os outros 4 são código do banco em que a conta está domiciliada
     * Os próximos 11 digítos  número de conta com 11 (onze) caracteres
     * (aqui é que iremos usar a regra do ID de estudante)
     * E dois dígitos de controlo
     * */

    public int gerarNumeroAleatorio(int max, int min){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public BigInteger createNumeroDeConta(HashSet<BigInteger> listaNumerosDeConta){

        int min1 = 1000, max1 = 9000, min2 = 1000000, max2= 9000000;
        String stringFinal="";
        BigInteger numeroFinal = BigInteger.ZERO;
        Integer numeroTemporario1, numeroTemporario2;
        boolean flag = true;

        while (flag) {
            numeroTemporario1 = gerarNumeroAleatorio(max1, min1);
            numeroTemporario2 = gerarNumeroAleatorio(max2, min2);
            stringFinal = numeroTemporario1.toString() + numeroTemporario2.toString();
            numeroFinal =new BigInteger(stringFinal);

            if(!listaNumerosDeConta.contains(numeroFinal)){
                flag = false;
            }
        }
        System.out.println("O número de conta é: "+ numeroFinal);
        return  numeroFinal;
    }


    public String createIban(BigInteger numeroDeConta){
        Integer numeroDeControle = gerarNumeroAleatorio(90,10);
        String iban = String.valueOf(identificadorDoBanco.getIdentificadorDoBanco())+
                numeroDeConta + numeroDeControle;
        System.out.println("O IBAN é: "+ iban);
        return  iban;
    }

}
