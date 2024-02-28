package ucan.edu.utils.jsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import ucan.edu.config.component.TransferenciaComponent;
import ucan.edu.services.implementacao.TransferenciaServiceImpl;
import ucan.edu.utils.pojos.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomJsonPojos {


    @Autowired
    private static TransferenciaServiceImpl transferenciaServiceImpl;

      //userInfo.getUserInfo().get("accountNumber") +
    public static String criarStrToJson(TransferenciaPOJO transferenciaPOJO)
    {
        System.out.println(" dt:CustomJsonPojos " +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferenciaPOJO.getDatahora()));
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

    public static void saveTransferComponent(TransferenciaPOJO transferencia, TransferenciaComponent transferenciaComponent)  {
        Map<String, String> transferenciaItems = new HashMap<>();

        transferenciaItems.put("descricao", transferencia.getDescricao());
        transferenciaItems.put("montante", transferencia.getMontante().toString());
        transferenciaItems.put("ibanDestinatario", transferencia.getIbanDestinatario());
        transferenciaItems.put("datahora","" +
                "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferencia.getDatahora()));
        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("ibanOrigem",transferencia.getIbanOrigem());
        transferenciaItems.put("tipoTransferencia", "INTERBANCARIA");
        transferenciaItems.put("estadoTransferencia", "EM PROCESSAMENTO");
        transferenciaItems.put("codigoTransferencia",""+transferencia.getCodigoTransferencia());
        transferenciaItems.put("bancoUdentifier",""+transferencia.getBancoUdentifier());

        transferenciaComponent.setTransferenciaResponse(transferenciaItems);

        System.out.println("data: " + transferenciaComponent.getTransferenciaResponse().get("datahora"));
    }





}
