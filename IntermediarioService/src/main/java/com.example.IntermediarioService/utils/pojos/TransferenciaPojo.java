/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.utils.pojos;

import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import com.example.IntermediarioService.utils.pojos.TransferenciaPOJO;
import org.apache.kafka.common.protocol.types.Field;

/**
 *
 * @author jussyleitecode
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaPojo
{
    private final String kuzolaBankIdentificador = "1003";
    private final String wakandaBankIdentificador = "";

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



    public static TransferenciaPojo convertingIntoTransferenciaPojo(TransferenciaPOJO transferenciaPOJO){

        TransferenciaPojo transferenciaPojo = new TransferenciaPojo();

        transferenciaPojo.setCodigoTransferencia(transferenciaPOJO.getCodigoTransferencia());
        transferenciaPojo.setEstadoTransferencia(transferenciaPOJO.getEstadoTransferencia());
        transferenciaPojo.setPkTransferencia(transferenciaPOJO.getPkTransferencia());
        transferenciaPojo.setDatahora(TransferenciaServiceImpl.formattingDateTime());
        transferenciaPojo.setIbanDestinatario(transferenciaPOJO.getIbanDestinatario());
        transferenciaPojo.setibanOrigem(transferenciaPOJO.getFkContaBancariaOrigem().toString());
        transferenciaPojo.setMontante(transferenciaPOJO.getMontante());
        return transferenciaPojo;
    }



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
}
