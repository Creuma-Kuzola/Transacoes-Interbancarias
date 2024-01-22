/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
@Table(catalog = "transferencia_bd", schema = "public")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_transferencia", nullable = false)
    private Integer pkTransferencia;
    @Column(length = 2147483647)
    private String descricao;
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger montante;
    @Basic(optional = false)
    @Column(name = "iban_destinatario", nullable = false, length = 2147483647)
    private String ibanDestinatario;
    @Basic(optional = false)
    @Column(name = "fk_conta_origem", nullable = false, length = 2147483647)
    private String fkContaOrigem;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "fk_canal", referencedColumnName = "pk_canal")
    @ManyToOne
    private Canal fkCanal;
    @JoinColumn(name = "fk_tipo_transferencia_bancaria", referencedColumnName = "pk_tipo_transferencia")
    @ManyToOne
    private TipoTransferencia fkTipoTransferenciaBancaria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTransferencia")
    @JsonIgnore
    private List<TokenValidacao> tokenValidacaoList;
    
}
