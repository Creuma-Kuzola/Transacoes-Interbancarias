/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "transferencia_bd", schema = "public")

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_cliente", nullable = false)
    private Integer pkCliente;
    @Column(length = 2147483647)
    private String nome;
    @Column(length = 2147483647)
    private String iban;
    @Column(name = "numero_de_conta", length = 2147483647)
    private String numeroDeConta;
    @JoinColumn(name = "fk_banco", referencedColumnName = "pk_banco")
    @ManyToOne
    private Banco fkBanco;
    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> userList;

    public Cliente() {
    }

    public Cliente(Integer pkCliente) {
        this.pkCliente = pkCliente;
    }

    public Integer getPkCliente() {
        return pkCliente;
    }

    public void setPkCliente(Integer pkCliente) {
        this.pkCliente = pkCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNumeroDeConta() {
        return numeroDeConta;
    }

    public void setNumeroDeConta(String numeroDeConta) {
        this.numeroDeConta = numeroDeConta;
    }

    public Banco getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(Banco fkBanco) {
        this.fkBanco = fkBanco;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "pkCliente=" + pkCliente +
                ", nome='" + nome + '\'' +
                ", iban='" + iban + '\'' +
                ", numeroDeConta='" + numeroDeConta + '\'' +
                ", fkBanco=" + fkBanco +
                '}';
    }
}
