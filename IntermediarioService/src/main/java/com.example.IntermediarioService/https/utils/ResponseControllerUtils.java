package com.example.IntermediarioService.https.utils;

import com.example.IntermediarioService.utils.pojos.TransferenciaHistoricoComponent;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJOEmis;
import com.example.IntermediarioService.utils.pojos.TransferenciaResponseHistorico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.IntermediarioService.https.utils.ResponseBody;

//import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseControllerUtils
{
    public ResponseEntity<ResponseBody> created(String mensagem, Object data) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.CREATED);
        responseBody.setData( data );
        responseBody.setMensagem( mensagem );
        return ResponseEntity.status( HttpStatus.CREATED).body(responseBody );
    }
    
    public ResponseEntity<ResponseBody> ok( String mensagem, Object data) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.OK);
        responseBody.setData( data );
        responseBody.setMensagem( mensagem );
        return ResponseEntity.ok( responseBody );
    }
    
    public ResponseEntity<ResponseBody> naoEncontrado( String mensagem, Object data) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.NOT_FOUND);
        responseBody.setData( data );
        responseBody.setMensagem( mensagem );
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body(responseBody );
    }

    public ResponseEntity<ResponseBody> historicoDebito(List<TransferenciaResponseHistorico> listaEmis) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.OK);
        responseBody.setMensagem( "Historico de Debito" );
        responseBody.setData(listaEmis);
        return ResponseEntity.status( HttpStatus.OK).body(responseBody );
    }

}
