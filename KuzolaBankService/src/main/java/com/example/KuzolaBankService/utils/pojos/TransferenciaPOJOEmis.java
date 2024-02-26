package com.example.KuzolaBankService.utils.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaPOJOEmis {
    @JsonProperty("pkTransferencia")
    private Integer pkTransferencia;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("montante")
    private BigDecimal montante;

    @JsonProperty("ibanDestinatario")
    private String ibanDestinatario;

    @JsonProperty("datahora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime datahora;

    @JsonProperty("ibanOrigem")
    private String ibanOrigem;

    @JsonProperty("tipoTransferencia")
    private String tipoTransferencia;

    @JsonProperty("estadoTransferencia")
    private String estadoTransferencia;

    @JsonProperty("codigoTransferencia")
    private String codigoTransferencia;


    public static TransferenciaPOJOEmis convertingIntoTransferenciaEmis(TransferenciaPOJO transferenciaPOJO)
    {
        TransferenciaPOJOEmis transferenciaPOJOEmis = new TransferenciaPOJOEmis();
        transferenciaPOJOEmis.setCodigoTransferencia(transferenciaPOJO.getCodigoTransferencia());
        transferenciaPOJOEmis.setEstadoTransferencia(transferenciaPOJO.getEstadoTransferencia());
        transferenciaPOJOEmis.setDatahora(transferenciaPOJO.getDatahora());
        transferenciaPOJOEmis.setDescricao(transferenciaPOJO.getDescricao());
        transferenciaPOJOEmis.setIbanDestinatario(transferenciaPOJO.getIbanDestinatario());
        transferenciaPOJOEmis.setIbanOrigem(transferenciaPOJO.getibanOrigem());
        transferenciaPOJOEmis.setTipoTransferencia(transferenciaPOJO.getTipoTransferencia());
        transferenciaPOJOEmis.setMontante(transferenciaPOJO.getMontante());
        return transferenciaPOJOEmis;
    }


    // Getters and setters
    public Integer getPkTransferencia() {
        return pkTransferencia;
    }

    public void setPkTransferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public String getIbanDestinatario() {
        return ibanDestinatario;
    }

    public void setIbanDestinatario(String ibanDestinatario) {
        this.ibanDestinatario = ibanDestinatario;
    }

    public LocalDateTime getDatahora() {
        return datahora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
    }

    public String getIbanOrigem() {
        
        return ibanOrigem;
    }

    public void setIbanOrigem(String ibanOrigem) {
        this.ibanOrigem = ibanOrigem;
    }

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(String estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getCodigoTransferencia() {
        return codigoTransferencia;
    }

    public void setCodigoTransferencia(String codigoTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
    }

    /*public Transferencia convertingIntoTransferencia(TransferenciaPOJO transferenciaPOJO){
        Transferencia transferencia = new Transferencia();
        transferencia.
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"pkTransferencia\": ").append(pkTransferencia).append(",");
        sb.append("\"descricao\": \"").append(descricao).append("\",");
        sb.append("\"montante\": ").append(montante).append(",");
        sb.append("\"ibanDestinatario\": \"").append(ibanDestinatario).append("\",");
        sb.append("\"datahora\": \"").append(datahora).append("\",");
        sb.append("\"ibanOrigem\": ").append(ibanOrigem).append(",");
        sb.append("\"tipoTransferencia\": \"").append(tipoTransferencia).append("\",");
        sb.append("\"estadoTransferencia\": \"").append(estadoTransferencia).append("\",");
        sb.append("\"codigoTransferencia\": \"").append(codigoTransferencia).append("\"");
        sb.append("}");
        return sb.toString();
    }
    
}
