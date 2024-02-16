/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author creuma
 */
@Entity
@Table(name = "conta_bancaria", catalog = "kuzola_bank", schema = "public")

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_conta_bancaria", nullable = false)
    private Integer pkContaBancaria;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String iban;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String status;
    @Basic(optional = false)
    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    @Basic(optional = false)
    @Column(name = "numero_de_conta", nullable = false)
    private BigInteger numeroDeConta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_contabilistico", precision = 11, scale = 3)
    private BigDecimal saldoContabilistico;
    @Column(name = "saldo_disponivel", precision = 11, scale = 3)
    private BigDecimal saldoDisponivel;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente", nullable = false)
    @ManyToOne(optional = false)
    private Cliente fkCliente;

    public Integer getPkContaBancaria() {
        return pkContaBancaria;
    }

    public void setPkContaBancaria(Integer pkContaBancaria) {
        this.pkContaBancaria = pkContaBancaria;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigInteger getNumeroDeConta() {
        return numeroDeConta;
    }

    public void setNumeroDeConta(BigInteger numeroDeConta) {
        this.numeroDeConta = numeroDeConta;
    }

    public BigDecimal getSaldoContabilistico() {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(BigDecimal saldoContabilistico) {
        this.saldoContabilistico = saldoContabilistico;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public Cliente getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente) {
        this.fkCliente = fkCliente;
    }
}
