package com.example.IntermediarioService.utils.pojos.jsonUtils;

import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermediarioService.utils.pojos.TransferenciaResponse;

import java.text.SimpleDateFormat;

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

    public  static String TransferenciaResponse(TransferenciaResponse response)
    {
        return "{ \"descricao\": \""+response.getDescricao()+"\", " +
                "\"status\": \"" +response.getStatus()+ "\" }";
    }
}
