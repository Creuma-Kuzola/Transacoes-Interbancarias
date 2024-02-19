/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.services.TransferenciaService;
import com.example.IntermediarioService.utils.pojos.TransferenciaPojo;
import org.springframework.stereotype.Service;
import com.example.IntermediarioService.services.implementacao.AbstractService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService{

    /*public LocalDateTime convertingToLocalDateTime(Date date){
        LocalDateTime ldt = LocalDateTime.ofInstant(date,
                ZoneId.systemDefault());
        LocalDateTime.
        return  ldt;
    }*/

    private final String kuzolaBankIdentificador = "1003";
    private final String waBankIdentificador = "404";

    public static LocalDateTime formattingDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }


    public boolean isValidTheSizeOfIban(String iban)
    {
        return iban.length() == 17;
    }

    public boolean isKuzolaBankIban(String iban)
    {
        String codigoBanco = iban.substring(0, 4);
        String idBancoValido = kuzolaBankIdentificador;
        return codigoBanco.equals(idBancoValido);
    }

    public boolean isWakandaBankIban(String iban)
    {
        String codigoBanco = iban.substring(0, 4);
        String idBancoValido = waBankIdentificador;
        return codigoBanco.equals(idBancoValido);
    }

    /*public Integer findingTheTypeOfTransferencia(TransferenciaPojo transferenciaPojo){

        if(isWakandaBankIban(transferenciaPojo.))


    }*/



}
