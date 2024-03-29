package com.example.KuzolaBankService.utils.pojos;

import com.example.KuzolaBankService.entities.Transferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaPOJO {
    @JsonProperty("pkTransferencia")
    private Integer pkTransferencia;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("montante")
    private BigDecimal montante;

    @JsonProperty("ibanDestinatario")
    private String ibanDestinatario;

    @JsonProperty("dataHora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
    
    @JsonProperty("tipoTransferencia")
    private String tipoTransferencia;

    @JsonProperty("estadoTransferencia")
    private String estadoTransferencia;

    @JsonProperty("codigoTransferencia")
    private String codigoTransferencia;

    @JsonProperty("ibanOrigem")
    private String ibanOrigem;


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
        return dataHora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.dataHora = datahora;
    }

    public String getibanOrigem() {
        return ibanOrigem;
    }

    public void setibanOrigem(String ibanOrigem) {
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
        sb.append("\"datahora\": \"").append(dataHora).append("\",");
        sb.append("\"ibanOrigem\": ").append(ibanOrigem).append(",");
        sb.append("\"tipoTransferencia\": \"").append(tipoTransferencia).append("\",");
        sb.append("\"estadoTransferencia\": \"").append(estadoTransferencia).append("\",");
        sb.append("\"codigoTransferencia\": \"").append(codigoTransferencia).append("\"");
        sb.append("}");
        return sb.toString();
    }



}
