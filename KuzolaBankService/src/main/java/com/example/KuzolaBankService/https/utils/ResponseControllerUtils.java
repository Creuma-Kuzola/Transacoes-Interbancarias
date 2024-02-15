package com.example.KuzolaBankService.https.utils;

import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.utils.TransferenciaResponseKuzolaBank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseControllerUtils
{
    TransferenciaResponseKuzolaBank transferenciaResponseKuzolaBank = new TransferenciaResponseKuzolaBank();
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

    public ResponseEntity<ResponseBody> createdTransferencia(Transferencia transferencia) {
        String mensagem = "Transferencia efectuada com sucesso";

        ResponseBody responseBody = new ResponseBody();
        responseBody.setTimestamp(LocalDateTime.now() );
        responseBody.setStatus(HttpStatus.CREATED);
        responseBody.setMensagem( mensagem );

        responseBody.setData(transferenciaResponseKuzolaBank.convertingIntoTransferenciaKuzolaBank(transferencia));
        return ResponseEntity.status( HttpStatus.CREATED).body(responseBody );
    }
}
