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

public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_funcionario", nullable = false)
    private Integer pkFuncionario;
    @Column(length = 2147483647)
    private String nome;
    @Column(length = 2147483647)
    private String sexo;
    @OneToMany(mappedBy = "fkFuncionario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> userList;

    public Funcionario() {
    }

    public Funcionario(Integer pkFuncionario) {
        this.pkFuncionario = pkFuncionario;
    }

    public Integer getPkFuncionario() {
        return pkFuncionario;
    }

    public void setPkFuncionario(Integer pkFuncionario) {
        this.pkFuncionario = pkFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "pkFuncionario=" + pkFuncionario +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
