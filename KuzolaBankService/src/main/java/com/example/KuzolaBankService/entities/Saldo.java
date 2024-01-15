/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
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
@Table(catalog = "kuzola_bank", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Saldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_saldo", nullable = false)
    private Integer pkSaldo;
    @Column(name = "saldo_contabilistic")
    private BigInteger saldoContabilistic;
    @Column(name = "saldo_disponivel")
    private BigInteger saldoDisponivel;
    @Column(length = 2147483647)
    private String moeda;
    @Column(name = "data_insercao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInsercao;
    @JoinColumn(name = "fk_conta_bancaria", referencedColumnName = "pk_conta_bancaria")
    @ManyToOne
    private ContaBancaria fkContaBancaria;

}
