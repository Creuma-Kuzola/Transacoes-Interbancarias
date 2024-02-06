package com.emis.intermediario.https.utils;

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
}
