/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    //@Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(name = "codigo_transferencia", length = 2147483647)
    private String codigoTransferencia;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @JoinColumn(name = "fk_conta_bancaria_origem", referencedColumnName = "pk_conta_bancaria")
    @ManyToOne
    private ContaBancaria fkContaBancariaOrigem;
    
}
