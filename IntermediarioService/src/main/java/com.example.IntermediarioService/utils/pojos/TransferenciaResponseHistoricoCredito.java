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
public class TransferenciaResponseHistoricoCredito {

   private LocalDateTime dataHora;
   private BigDecimal montante;
   private String estadoTransferencia;
   private String ibanOrigem;
   private Integer pkTransferencia;

    public static TransferenciaResponseHistoricoCredito convertingIntoTransferenciaKuzolaBank(TransferenciaPOJOEmis transferenciaPOJOEmis){

        TransferenciaResponseHistoricoCredito transferenciaResponseHistoricoCredito = new TransferenciaResponseHistoricoCredito();
        transferenciaResponseHistoricoCredito.setPkTransferencia(transferenciaPOJOEmis.getPkTransferencia());
        transferenciaResponseHistoricoCredito.setDataHora(transferenciaPOJOEmis.getDatahora());
        transferenciaResponseHistoricoCredito.setMontante(transferenciaPOJOEmis.getMontante());
        transferenciaResponseHistoricoCredito.setIbanOrigem(transferenciaPOJOEmis.getIbanOrigem());
        transferenciaResponseHistoricoCredito.setEstadoTransferencia(transferenciaPOJOEmis.getEstadoTransferencia());

        return  transferenciaResponseHistoricoCredito;
    }

    public static List<TransferenciaResponseHistoricoCredito> convertingIntoListTransferenciaHistorico(List<TransferenciaPOJOEmis> listaTransferenciaPOJOEmis){

        List<TransferenciaResponseHistoricoCredito> listaTransferenciaResponseHistoricoCredito = new ArrayList<>();

        for (TransferenciaPOJOEmis transferenciaPOJOEmis: listaTransferenciaPOJOEmis){

            TransferenciaResponseHistoricoCredito transferenciaResponseHistoricoCredito = TransferenciaResponseHistoricoCredito.convertingIntoTransferenciaKuzolaBank(transferenciaPOJOEmis);
            listaTransferenciaResponseHistoricoCredito.add(transferenciaResponseHistoricoCredito);
        }

        return listaTransferenciaResponseHistoricoCredito;

    }


}
