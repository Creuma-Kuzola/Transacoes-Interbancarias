package com.example.KuzolaBankService.utils.jsonUtils;


import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.utils.pojos.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class CustomJsonPojos {

      //userInfo.getUserInfo().get("accountNumber") +
    public static String criarStrToJson(TransferenciaPOJO transferenciaPOJO)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJO.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" + transferenciaPOJO.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJO.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJO.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + transferenciaPOJO.getDatahora() + "\",\n"
                + "    \"ibanOrigem\": " +transferenciaPOJO.getibanOrigem() +",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": " + transferenciaPOJO.getCodigoTransferencia() + "\n"
                + "}";
        return str;
    }

    public static String criarStrToJson(TransferenciaCustomPOJO transferenciaPOJO)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJO.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" + transferenciaPOJO.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJO.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJO.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferenciaPOJO.getDatahora()) + "\",\n"
                + "    \"fkContaBancariaOrigem\": " +transferenciaPOJO.getFkContaBancariaOrigem() +",\n"
                + "    \"ibanOrigem\": " +transferenciaPOJO.getIbanOrigem() +",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": \"" + transferenciaPOJO.getCodigoTransferencia() + "\",\n"
                + "    \"bancoUdentifier\":"+transferenciaPOJO.getBancoUdentifier()+"\n"
                + "}";
        return str;
    }


    public static String criarStrToJson(TransferenciaPOJOEmis transferenciaPOJOEmis)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJOEmis.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" + transferenciaPOJOEmis.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJOEmis.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJOEmis.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + transferenciaPOJOEmis.getDatahora() + "\",\n"
                + "    \"ibanOrigem\": " +transferenciaPOJOEmis.getIbanOrigem() +",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJOEmis.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJOEmis.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": " + transferenciaPOJOEmis.getCodigoTransferencia() + "\n"
                + "}";
        return str;
    }

    public static String criarStrToJson(ContaBancaria contaBancaria)
    {
        return "{ \"saldoContabilistico\": \""+contaBancaria.getSaldoContabilistico()+"\", " +
                "\"saldoDisponivel\": \"" +contaBancaria.getSaldoDisponivel()+ "\" }";
    }

    public  static String TransferenciaResponse(TransferenciaResponse response)
    {
        return "{ \"descricao\": \""+response.getDescricao()+"\", " +
                "\"status\": \"" +response.getStatus()+ "\" }";
    }

    public  static String clientePOJOjson(ClientePOJO clientePOJO)
    {
        return "{ \"pkCliente\": \""+clientePOJO.getPkCliente()+"\", " +
                "\"nome\": \"" +clientePOJO.getNome()+ "\"," +
                "\"iban\": \"" +clientePOJO.getIban()+ "\"," +
                "\"numeroConta\": \"" +clientePOJO.getNumeroConta()+ "\"," +
                "\"fkBanco\": \"" +clientePOJO.getFkBanco()+ "\"," +
                "\"login\": \"" +clientePOJO.getLogin()+ "\"," +
                "\"password\": \"" +clientePOJO.getPassword()+ "\"," +
                "\"role\": \"" +clientePOJO.getRole()+ "\"" +
                " }";
    }
}
