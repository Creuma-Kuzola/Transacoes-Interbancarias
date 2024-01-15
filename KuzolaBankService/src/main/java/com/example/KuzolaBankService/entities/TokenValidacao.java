/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

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
@Table(name = "token_validacao", catalog = "kuzola_bank", schema = "public")
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
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String designacao;
    @Column(name = "codigo_validacao")
    private Integer codigoValidacao;
    @JoinColumn(name = "fk_historico_transferencia_emis", referencedColumnName = "pk_historico_transferencia_emis")
    @ManyToOne
    private HistoricoTransferenciaEmis fkHistoricoTransferenciaEmis;
    @JoinColumn(name = "fk_transferencia", referencedColumnName = "pk_transferencia")
    @ManyToOne
    private Transferencia fkTransferencia;
    
}
