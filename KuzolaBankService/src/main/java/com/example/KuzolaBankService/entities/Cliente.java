/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
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
@Table(catalog = "kuzola_bank", schema = "public")

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
    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ContaBancaria> contaBancariaList;
    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> usersList;

    public Long getPkCliente() {
        return pkCliente;
    }

    public void setPkCliente(Long pkCliente) {
        this.pkCliente = pkCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Empresa getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Empresa fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Pessoa getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(Pessoa fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public List<User> getUsersList() {
        return new ArrayList<>();
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }


    public List<ContaBancaria> getContaBancariaList() {
        return new ArrayList<>();
    }

    public void setContaBancariaList(List<ContaBancaria> contaBancariaList) {
        this.contaBancariaList = contaBancariaList;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "pkCliente=" + pkCliente +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", fkEmpresa=" + fkEmpresa +
                ", fkPessoa=" + fkPessoa +
                ", contaBancariaList=" + contaBancariaList +
                '}';
    }
}
