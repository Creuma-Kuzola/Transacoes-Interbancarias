package com.example.KuzolaBankService.utils;

import com.example.KuzolaBankService.entities.Transferencia;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponseKuzolaBank {

   private LocalDateTime dataHora;
   private BigDecimal montante;
   private String estadoTransferencia;
   private String IbanDestinatario;
   private Integer pkTransferencia;

    public TransferenciaResponseKuzolaBank convertingIntoTransferenciaKuzolaBank(Transferencia transferencia){

        TransferenciaResponseKuzolaBank transferenciaResponseKuzolaBank = new TransferenciaResponseKuzolaBank();
        transferenciaResponseKuzolaBank.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaResponseKuzolaBank.setDataHora(transferencia.getDatahora());
        transferenciaResponseKuzolaBank.setMontante(transferencia.getMontante());
        transferenciaResponseKuzolaBank.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaResponseKuzolaBank.setEstadoTransferencia(transferencia.getEstadoTransferencia());

        return  transferenciaResponseKuzolaBank;
    }
}
