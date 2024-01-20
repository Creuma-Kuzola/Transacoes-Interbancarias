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
@Table(name = "conta_bancaria", catalog = "kuzola_bank", schema = "public")


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

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
    @Column(name = "numero_de_conta")
    private BigInteger numeroDeConta;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente", nullable = false)
    @ManyToOne(optional = false)
    private Cliente fkCliente;

}
