/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.component.UserInfo;
import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.services.TransferenciaService;
import com.example.IntermediarioService.utils.pojos.ClientePojoMini;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJOEmis;
//import com.example.IntermediarioService.utils.pojos.TransferenciaPojo;
import com.example.IntermediarioService.utils.pojos.jsonUtils.CustomJsonPojos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService{

    @Autowired
    BancoServiceImpl bancoServiceImpl;

    @Autowired
    UserInfo userInfo;

    @Autowired
    BancoComponent bancoComponent;

    private final String kuzolaBankIdentificador = "1003";
    private final String waBankIdentificador = "4040";

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

    public static Date convertingLocalDateTimeIntoDate( LocalDateTime localDateTime){

        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
        return date;
    }

    public static LocalDateTime convertingDateIntoLocalDateTime( Date date){

        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return ldt;
    }

    public Transferencia convertingIntoTransferencia(TransferenciaPOJOEmis transferenciaPOJOEmis){

        Transferencia transferencia = new Transferencia();
        transferencia.setEstadoTransferencia("Realizado");
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

    public Transferencia fillingTransferenciaFields(Transferencia transferencia, String ibanOrigem)
    {
        transferencia.setDataHora(new Date());
        transferencia.setEstadoTransferencia("EM PROCESSAMENTO");
        transferencia.setCanal("emis");
        transferencia.setTipoTransferencia("Transferencia Interbancaria");
        transferencia.setibanOrigem(ibanOrigem);

        Banco banco = null;

        Map<String,Integer> identificadorBanco = new HashMap<>();


        if (ibanOrigem.substring(0,4).equals("4040"))
        {
            banco = new Banco(2);
            banco.setCodigoIdentificadorBanco(Integer.parseInt(ibanOrigem.substring(0,4)));
            identificadorBanco.put("UUID",banco.getCodigoIdentificadorBanco());
            bancoComponent.setBancoComponent(identificadorBanco);
        }
        else if (ibanOrigem.substring(0,4).equals("1003"))
        {
            banco =new Banco(1);
            banco.setCodigoIdentificadorBanco(Integer.parseInt(ibanOrigem.substring(0,4)));
            identificadorBanco.put("UUID",banco.getCodigoIdentificadorBanco());
            bancoComponent.setBancoComponent(identificadorBanco);
        }
        transferencia.setFkBanco(banco);
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

    public ClientePojoMini getClientePojoMini(){

        ClientePojoMini clientePojoMini = new ClientePojoMini();
        clientePojoMini.setIban(userInfo.getUserInfo().get("iban"));
        clientePojoMini.setNumeroDeConta(userInfo.getUserInfo().get("accountNumber"));
        return  clientePojoMini;
    }

    public String convertingIntoClientePojoMiniJson(){

        String clientePojoMiniJson = CustomJsonPojos.criarStrToJson(getClientePojoMini());
        System.out.println("Data Json" + clientePojoMiniJson);
        return  clientePojoMiniJson;
    }



    public  Integer isValidInformationIban(String ibanDestinatario, String ibanOrigem, BigDecimal montante){

        //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
        if(isValidTheSizeOfIban(ibanDestinatario)){
            if (!ibanDestinatario.equals(ibanOrigem)){

                if(montante.compareTo(BigDecimal.ZERO) > 0) {
                    return 1;
                }
                else if(montante.compareTo(BigDecimal.ZERO) <= 0)
                {
                    return 4;
                }
            }
            return 2;
        }
        return 3;
    }




}
