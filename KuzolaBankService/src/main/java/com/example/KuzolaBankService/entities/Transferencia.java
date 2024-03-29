/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.*;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "kuzola_bank", schema = "public")

public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_transferencia", nullable = false)
    private Integer pkTransferencia;
    @Column(length = 2147483647)
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 11, scale = 3)
    private BigDecimal montante;
    @Column(name = "iban_destinatario", length = 2147483647)
    private String ibanDestinatario;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datahora;

    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(name = "codigo_transferencia", length = 2147483647)
    private String codigoTransferencia;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @Column(length = 2147483647)
    private String operacao;
    @Column(name = "iban_origem", length = 2147483647)
    private String ibanOrigem;

    public Transferencia() {
    }

    public Transferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }

    public Transferencia(Integer pkTransferencia, BigDecimal montante) {
        this.pkTransferencia = pkTransferencia;
        this.montante = montante;
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

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getIbanOrigem() {
        return ibanOrigem;
    }

    public void setIbanOrigem(String ibanOrigem) {
        this.ibanOrigem = ibanOrigem;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "pkTransferencia=" + pkTransferencia +
                ", descricao='" + descricao + '\'' +
                ", montante=" + montante +
                ", ibanDestinatario='" + ibanDestinatario + '\'' +
                ", datahora=" + datahora +
                ", estadoTransferencia='" + estadoTransferencia + '\'' +
                ", codigoTransferencia='" + codigoTransferencia + '\'' +
                ", tipoTransferencia='" + tipoTransferencia + '\'' +
                ", operacao='" + operacao + '\'' +
                ", ibanOrigem='" + ibanOrigem + '\'' +
                '}';
    }
}
