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
@Table(catalog = "kuzola_bank", schema = "public")

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_conta", nullable = false)
    private Integer pkConta;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String username;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String password;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    @ManyToOne
    private Cliente fkCliente;
    @JoinColumn(name = "fk_conta_tipo", referencedColumnName = "pk_conta_tipo")
    @ManyToOne
    private ContaTipo fkContaTipo;
    
}
