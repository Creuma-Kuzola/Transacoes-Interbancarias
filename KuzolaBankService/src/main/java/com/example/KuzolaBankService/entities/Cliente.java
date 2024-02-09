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
import org.aspectj.lang.annotation.Around;

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
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_cliente", nullable = false)
    private Long pkCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String telefone;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String email;
    @JoinColumn(name = "fk_empresa", referencedColumnName = "pk_empresa")
    @ManyToOne
    private Empresa fkEmpresa;
    @JoinColumn(name = "fk_pessoa", referencedColumnName = "pk_pessoa")
    @ManyToOne
    private Pessoa fkPessoa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCliente")
    @JsonIgnore
    private List<ContaBancaria> contaBancariaList;
    @OneToMany(mappedBy = "fkCliente")
    @JsonIgnore
    private List<Conta> contaList;
    
}
