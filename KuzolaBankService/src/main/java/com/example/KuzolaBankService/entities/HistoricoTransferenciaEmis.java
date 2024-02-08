/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name = "historico_transferencia_emis", catalog = "kuzola_bank", schema = "public")

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class HistoricoTransferenciaEmis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_historico_transferencia_emis", nullable = false)
    private Integer pkHistoricoTransferenciaEmis;
    @Column(name = "fk_transferencia_bancaria")
    private Integer fkTransferenciaBancaria;
    @OneToMany(mappedBy = "fkHistoricoTransferenciaEmis")
    @JsonIgnore
    private List<TokenValidacao> tokenValidacaoList;
    
}
