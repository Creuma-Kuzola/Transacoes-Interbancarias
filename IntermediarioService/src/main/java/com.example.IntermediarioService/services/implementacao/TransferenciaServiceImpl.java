/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.component.UserInfo;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.services.TransferenciaService;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJOEmis;
//import com.example.IntermediarioService.utils.pojos.TransferenciaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.IntermediarioService.services.implementacao.AbstractService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

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

    @Autowired
    BancoServiceImpl bancoServiceImpl;

    @Autowired
    UserInfo userInfo;

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

    public Date convertingLocalDateTimeIntoDate( LocalDateTime localDateTime){

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
        return date;
    }

    public LocalDateTime convertingDateIntoLocalDateTime( Date date){

        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return ldt;
    }

    public Transferencia convertingIntoTransferencia(TransferenciaPOJOEmis transferenciaPOJOEmis){

        Transferencia transferencia = new Transferencia();
        transferencia.setEstadoTransferencia("REALIZADO");
        transferencia.setTipoTransferencia(transferenciaPOJOEmis.getTipoTransferencia());
        transferencia.setDescricao(transferenciaPOJOEmis.getDescricao());
        transferencia.setCanal("Banco Kuzola");
        transferencia.setibanOrigem(transferenciaPOJOEmis.getIbanOrigem());
        transferencia.setMontante(transferenciaPOJOEmis.getMontante());
        transferencia.setIbanDestinatario(transferenciaPOJOEmis.getIbanDestinatario());
        transferencia.setDataHora(convertingLocalDateTimeIntoDate(transferenciaPOJOEmis.getDatahora()));
        transferencia.setFkBanco(bancoServiceImpl.findByPkBanco(1));

        return  transferencia;

    }

    public Transferencia salvarTransferencia(Transferencia transferencia){
       return this.criar(transferencia);
    }

    public boolean isTransferenciaInterbancariaKuzolaBank(String ibanOrigem, String ibanDestino){

        return isKuzolaBankIban(ibanOrigem) && isKuzolaBankIban(ibanDestino);

    }

    public boolean isTransferenciaIntrabancariaWakandaBank(String ibanOrigem, String ibanDestino){

        return isWakandaBankIban(ibanOrigem) && isWakandaBankIban(ibanDestino);

    }

    public boolean isTransferenciaInterBancaria(String ibanOrigem, String ibanDestino){

        if(isKuzolaBankIban(ibanOrigem) && isWakandaBankIban(ibanDestino))
        {
            return true;
        }
        else if(isWakandaBankIban(ibanOrigem) && isKuzolaBankIban(ibanDestino))
        {
            return true;
        }
        return false;
    }


    public void fillingTransactionFields(Transferencia transferencia, String ibanOrigem){

        transferencia.setDataHora(new Date());
        //transferencia.setEstadoTransferencia("Realizado");
        transferencia.setTipoTransferencia("Transferencia Intrabancaria");
        transferencia.setibanOrigem(ibanOrigem);
    }

    public void saveUserInfoTemporary(Cliente cliente, String username) {
        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("iban",cliente.getIban());
        map.put("accountNumber",""+cliente.getNumeroDeConta());
        map.put("pkCliente",""+cliente.getPkCliente());
        userInfo.setUserInfo(map);

        System.out.println("IBAN:"+userInfo.getUserInfo().get("iban"));
        System.out.println("numeroDaConta:"+userInfo.getUserInfo().get("accountNumber"));
    }

}
