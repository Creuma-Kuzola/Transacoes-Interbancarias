package com.example.KuzolaBankService.utils.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jussyleitecode
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaCustomPOJO
{

    private Integer pkTransferencia;
    private String descricao;
    private BigDecimal montante;
    private String ibanDestinatario;
    private Date datahora;
    private BigInteger fkContaBancariaOrigem;
    private String tipoTransferencia;
    private String estadoTransferencia;
    private String codigoTransferencia;

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

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public BigInteger getFkContaBancariaOrigem() {
        return fkContaBancariaOrigem;
    }

    public void setFkContaBancariaOrigem(BigInteger fkContaBancariaOrigem) {
        this.fkContaBancariaOrigem = fkContaBancariaOrigem;
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

    @Override
    public String toString() {
        return "TransferenciaCustomPOJO{" +
                "pkTransferencia=" + pkTransferencia +
                ", descricao='" + descricao + '\'' +
                ", montante=" + montante +
                ", ibanDestinatario='" + ibanDestinatario + '\'' +
                ", datahora=" + datahora +
                ", fkContaBancariaOrigem=" + fkContaBancariaOrigem +
                ", tipoTransferencia='" + tipoTransferencia + '\'' +
                ", estadoTransferencia='" + estadoTransferencia + '\'' +
                ", codigoTransferencia='" + codigoTransferencia + '\'' +
                '}';
    }
}
