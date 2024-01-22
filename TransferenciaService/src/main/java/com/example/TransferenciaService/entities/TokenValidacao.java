/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.entities;

import java.io.Serializable;
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
@Table(name = "token_validacao", catalog = "transferencia_bd", schema = "public")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TokenValidacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_token_validacao", nullable = false)
    private Integer pkTokenValidacao;
    @Column(length = 2147483647)
    private String designacao;
    @Basic(optional = false)
    @Column(name = "codigo_validacao", nullable = false)
    private int codigoValidacao;
    @JoinColumn(name = "fk_transferencia", referencedColumnName = "pk_transferencia", nullable = false)
    @ManyToOne(optional = false)
    private Transferencia fkTransferencia;

}
