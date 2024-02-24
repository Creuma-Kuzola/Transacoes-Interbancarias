package com.example.IntermediarioService.utils.pojos.jsonUtils;

import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermediarioService.utils.pojos.TransferenciaResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomJsonPojos {

      //userInfo.getUserInfo().get("accountNumber") +
    public static String criarStrToJson(TransferenciaPOJO transferenciaPOJO)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJO.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" + transferenciaPOJO.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJO.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJO.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferenciaPOJO.getDatahora()) + "\",\n"
                + "    \"fkContaBancariaOrigem\": " +transferenciaPOJO.getFkContaBancariaOrigem() +",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": \"" + transferenciaPOJO.getCodigoTransferencia() + "\",\n"
                + "    \"bancoUdentifier\":"+transferenciaPOJO.getBancoUdentifier()+"\n"
                + "}";
        return str;
    }

    public LocalDateTime formattingDateTime(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }

    public LocalDateTime convertingDateIntoLocalDateTime(Date date){

        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return formattingDateTime(ldt);
    }

    public String criarStrToJson(Transferencia transferencia)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferencia.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" + transferencia.getDescricao() + "\",\n"
                + "    \"montante\": " + transferencia.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferencia.getIbanDestinatario() + "\",\n"
                + "    \"dataHora\":\"" + convertingDateIntoLocalDateTime(transferencia.getDataHora()) + "\",\n"
                + "    \"estadoTransferencia\": " +transferencia.getEstadoTransferencia() +",\n"
                + "    \"tipoTransferencia\": \"" + transferencia.getTipoTransferencia() + "\",\n"
                + "    \"ibanOrigem\":"+transferencia.getibanOrigem()+"\n"
                + "}";
        return str;
    }

    public  static String TransferenciaResponse(TransferenciaResponse response)
    {
        return "{ \"descricao\": \""+response.getDescricao()+"\", " +
                "\"status\": \"" +response.getStatus()+ "\" }";
    }
}
