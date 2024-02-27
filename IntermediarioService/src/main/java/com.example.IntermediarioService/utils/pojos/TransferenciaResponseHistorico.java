package com.example.IntermediarioService.utils.pojos;


import com.example.IntermediarioService.entities.Transferencia;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponseHistorico {

   private LocalDateTime dataHora;
   private BigDecimal montante;
   private String estadoTransferencia;
   private String IbanDestinatario;
   private Integer pkTransferencia;

    public static TransferenciaResponseHistorico convertingIntoTransferenciaKuzolaBank(TransferenciaPOJOEmis transferenciaPOJOEmis){

        TransferenciaResponseHistorico transferenciaResponseHistoricoDebito = new TransferenciaResponseHistorico();
        transferenciaResponseHistoricoDebito.setPkTransferencia(transferenciaPOJOEmis.getPkTransferencia());
        transferenciaResponseHistoricoDebito.setDataHora(transferenciaPOJOEmis.getDatahora());
        transferenciaResponseHistoricoDebito.setMontante(transferenciaPOJOEmis.getMontante());
        transferenciaResponseHistoricoDebito.setIbanDestinatario(transferenciaPOJOEmis.getIbanDestinatario());
        transferenciaResponseHistoricoDebito.setEstadoTransferencia(transferenciaPOJOEmis.getEstadoTransferencia());

        return  transferenciaResponseHistoricoDebito;
    }

    public static List<TransferenciaResponseHistorico> convertingIntoListTransferenciaHistorico(List<TransferenciaPOJOEmis> listaTransferenciaPOJOEmis){

        List<TransferenciaResponseHistorico> listaTransferenciaResponseHistoricoDebito = new ArrayList<>();

        for (TransferenciaPOJOEmis transferenciaPOJOEmis: listaTransferenciaPOJOEmis){

            TransferenciaResponseHistorico transferenciaResponseHistorico = TransferenciaResponseHistorico.convertingIntoTransferenciaKuzolaBank(transferenciaPOJOEmis);
            listaTransferenciaResponseHistoricoDebito.add(transferenciaResponseHistorico);
        }

        return listaTransferenciaResponseHistoricoDebito;

    }


}
