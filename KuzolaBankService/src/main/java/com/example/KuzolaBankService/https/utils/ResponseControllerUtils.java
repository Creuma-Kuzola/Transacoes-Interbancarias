package com.example.KuzolaBankService.https.utils;

import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.utils.response.TransferenciaResponseKuzolaBank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseControllerUtils
{
    public ResponseEntity<ResponseBody> created( String mensagem, Object data) {
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

    public ResponseEntity<ResponseBody> transferenciaEfectuada(Transferencia transferencia) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.OK);
        responseBody.setMensagem( "Transferencia efectuada com sucesso" );
        responseBody.setData(TransferenciaResponseKuzolaBank.convertingIntoTransferenciaKuzolaBank(transferencia));
        return ResponseEntity.status( HttpStatus.OK).body(responseBody );
    }

    public ResponseEntity<ResponseBody> erro (String message) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.BAD_REQUEST);
        responseBody.setMensagem( message);

        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body(responseBody );
    }

}
