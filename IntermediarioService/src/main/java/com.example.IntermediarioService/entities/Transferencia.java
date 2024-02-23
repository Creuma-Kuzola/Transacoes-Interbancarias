/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;


/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "transferencia_bd", schema = "public")

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
    @Basic(optional = false)
    @Column(name = "iban_destinatario", nullable = false, length = 2147483647)
    private String ibanDestinatario;
    @Basic(optional = false)
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(length = 2147483647)
    private String canal;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @JoinColumn(name = "fk_banco", referencedColumnName = "pk_banco")
    @ManyToOne
    private Banco fkBanco;

    @Column(name = "iban_origem", length = 2147483647)
    private String ibanOrigem;

    public Transferencia() {
    }

    public Transferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }

    public Transferencia(Integer pkTransferencia, BigDecimal montante, String ibanDestinatario, Date dataHora) {
        this.pkTransferencia = pkTransferencia;
        this.montante = montante;
        this.ibanDestinatario = ibanDestinatario;
        this.dataHora = dataHora;
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

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(String estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getibanOrigem() {
        return ibanOrigem;
    }

    public void setibanOrigem(String ibanOrigem) {
        this.ibanOrigem = ibanOrigem;
    }

    public Banco getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(Banco fkBanco) {
        this.fkBanco = fkBanco;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "pkTransferencia=" + pkTransferencia +
                ", descricao='" + descricao + '\'' +
                ", montante=" + montante +
                ", ibanDestinatario='" + ibanDestinatario + '\'' +
                ", dataHora=" + dataHora +
                ", estadoTransferencia='" + estadoTransferencia + '\'' +
                ", canal='" + canal + '\'' +
                ", tipoTransferencia='" + tipoTransferencia + '\'' +
                ", fkBanco=" + fkBanco +
                ", ibanOrigem='" + ibanOrigem + '\'' +
                '}';
    }
}
