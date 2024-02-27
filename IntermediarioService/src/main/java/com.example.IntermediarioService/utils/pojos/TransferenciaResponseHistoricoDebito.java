package com.example.IntermediarioService.utils.pojos;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponseHistoricoDebito {

   private LocalDateTime dataHora;
   private BigDecimal montante;
   private String estadoTransferencia;
   private String ibanDestinatario;
   private Integer pkTransferencia;

    public static TransferenciaResponseHistoricoDebito convertingIntoTransferenciaKuzolaBank(TransferenciaPOJOEmis transferenciaPOJOEmis){

        TransferenciaResponseHistoricoDebito transferenciaResponseHistoricoDebito = new TransferenciaResponseHistoricoDebito();
        transferenciaResponseHistoricoDebito.setPkTransferencia(transferenciaPOJOEmis.getPkTransferencia());
        transferenciaResponseHistoricoDebito.setDataHora(transferenciaPOJOEmis.getDatahora());
        transferenciaResponseHistoricoDebito.setMontante(transferenciaPOJOEmis.getMontante());
        transferenciaResponseHistoricoDebito.setIbanDestinatario(transferenciaPOJOEmis.getIbanDestinatario());
        transferenciaResponseHistoricoDebito.setEstadoTransferencia(transferenciaPOJOEmis.getEstadoTransferencia());

        return  transferenciaResponseHistoricoDebito;
    }

    public static List<TransferenciaResponseHistoricoDebito> convertingIntoListTransferenciaHistorico(List<TransferenciaPOJOEmis> listaTransferenciaPOJOEmis){

        List<TransferenciaResponseHistoricoDebito> listaTransferenciaResponseHistoricoDebitoDebito = new ArrayList<>();

        for (TransferenciaPOJOEmis transferenciaPOJOEmis: listaTransferenciaPOJOEmis){

            TransferenciaResponseHistoricoDebito transferenciaResponseHistoricoDebito = TransferenciaResponseHistoricoDebito.convertingIntoTransferenciaKuzolaBank(transferenciaPOJOEmis);
            listaTransferenciaResponseHistoricoDebitoDebito.add(transferenciaResponseHistoricoDebito);
        }

        return listaTransferenciaResponseHistoricoDebitoDebito;

    }


}
