package com.example.IntermediarioService.utils.pojos.jsonUtils;

import com.example.IntermediarioService.component.TransferenciaPojoComponent;
import com.example.IntermediarioService.component.TransferenciaResponseComponent;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermediarioService.utils.pojos.TransferenciaResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                + "    \"ibanOrigem\": " +transferenciaPOJO.getIbanOrigem() +",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": \"" + transferenciaPOJO.getCodigoTransferencia() + "\",\n"
                + "    \"bancoUdentifier\":"+transferenciaPOJO.getBancoUdentifier()+"\n"
                + "}";
        return str;
    }

    public static void saveTransferResponseComponent(TransferenciaResponse response, TransferenciaResponseComponent transferenciaResponseComponent) {
        Map<String, String> message = new HashMap<>();
        message.put("descricao",response.getDescricao());
        message.put("status", ""+response.getStatus());
        transferenciaResponseComponent.setTransferenciaResponse(message);
    }

    public static TransferenciaResponse componenteResponseToResponse(TransferenciaResponseComponent transferenciaResponseComponent) {

        TransferenciaResponse transferenciaResponse = new TransferenciaResponse();
        transferenciaResponse.setDescricao(transferenciaResponseComponent.getTransferenciaResponse().get("descricao"));
        transferenciaResponse.setStatus(transferenciaResponseComponent.getTransferenciaResponse().get("status").equals("true") ? true : false);
        return  transferenciaResponse;
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


    public static void  saveTransferComponent(TransferenciaPOJO transferencia, TransferenciaPojoComponent transferenciaPOJOComponent)  {
        Map<String, String> transferenciaItems = new HashMap<>();

        transferenciaItems.put("descricao", transferencia.getDescricao());
        transferenciaItems.put("montante", transferencia.getMontante().toString());
        transferenciaItems.put("ibanDestinatario", transferencia.getIbanDestinatario());
        transferenciaItems.put("datahora","" +
                "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferencia.getDatahora()));
        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("tipoTransferencia", transferencia.getTipoTransferencia());
        transferenciaItems.put("estadoTransferencia", transferencia.getEstadoTransferencia());
        transferenciaItems.put("codigoTransferencia",""+transferencia.getCodigoTransferencia());
        transferenciaItems.put("bancoUdentifier",""+transferencia.getCodigoTransferencia());
        transferenciaItems.put("ibanOrigem",transferencia.getIbanOrigem());
        transferenciaPOJOComponent.setTransferencia(transferenciaItems);
    }

    public  static  TransferenciaPOJO convertToTransferenciaPOJO(TransferenciaPojoComponent transferenciaPOJOComponent) throws ParseException {
        TransferenciaPOJO transferenciaPOJO = new TransferenciaPOJO();

        transferenciaPOJO.setDescricao(transferenciaPOJOComponent.getTransferencia().get("descricao"));
        transferenciaPOJO.setMontante(new BigDecimal(transferenciaPOJOComponent.getTransferencia().get("montante")));
        transferenciaPOJO.setIbanDestinatario(transferenciaPOJOComponent.getTransferencia().get("ibanDestinatario"));
        transferenciaPOJO.setDatahora( new  SimpleDateFormat("yyyy-MM-dd").parse(transferenciaPOJOComponent.getTransferencia().get("datahora"))  );
        transferenciaPOJO.setFkContaBancariaOrigem(new BigInteger(transferenciaPOJOComponent.getTransferencia().get("fkContaBancariaOrigem")));
        transferenciaPOJO.setTipoTransferencia(transferenciaPOJOComponent.getTransferencia().get("tipoTransferencia"));
        transferenciaPOJO.setEstadoTransferencia(transferenciaPOJOComponent.getTransferencia().get("estadoTransferencia"));
        transferenciaPOJO.setCodigoTransferencia(transferenciaPOJOComponent.getTransferencia().get("codigoTransferencia"));
        transferenciaPOJO.setBancoUdentifier(Integer.parseInt(transferenciaPOJOComponent.getTransferencia().get("bancoUdentifier")));
        transferenciaPOJO.setIbanOrigem(transferenciaPOJOComponent.getTransferencia().get("ibanOrigem"));

        return  transferenciaPOJO;
    }
}
