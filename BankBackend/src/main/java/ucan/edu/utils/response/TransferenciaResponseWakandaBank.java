package ucan.edu.utils.response;

import lombok.*;
import ucan.edu.entities.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponseWakandaBank {

   private Date dataHora;
   private BigDecimal montante;
   private String estadoTransferencia;
   private String IbanDestinatario;
   private Integer pkTransferencia;

    public static TransferenciaResponseWakandaBank convertingIntoTransferenciaKuzolaBank(Transferencia transferencia){

        TransferenciaResponseWakandaBank transferenciaResponseWakandaBankBank = new TransferenciaResponseWakandaBank();
        transferenciaResponseWakandaBankBank.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaResponseWakandaBankBank.setDataHora(transferencia.getDatahora());
        transferenciaResponseWakandaBankBank.setMontante(transferencia.getMontante());
        transferenciaResponseWakandaBankBank.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaResponseWakandaBankBank.setEstadoTransferencia(transferencia.getEstadoTransferencia());

        return  transferenciaResponseWakandaBankBank;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public String getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(String estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getIbanDestinatario() {
        return IbanDestinatario;
    }

    public void setIbanDestinatario(String ibanDestinatario) {
        IbanDestinatario = ibanDestinatario;
    }

    public Integer getPkTransferencia() {
        return pkTransferencia;
    }

    public void setPkTransferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }
}
