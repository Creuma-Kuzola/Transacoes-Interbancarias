/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;


/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "transferencia_bd", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByPkFuncionario", query = "SELECT f FROM Funcionario f WHERE f.pkFuncionario = :pkFuncionario"),
    @NamedQuery(name = "Funcionario.findByNome", query = "SELECT f FROM Funcionario f WHERE f.nome = :nome"),
    @NamedQuery(name = "Funcionario.findBySexo", query = "SELECT f FROM Funcionario f WHERE f.sexo = :sexo")})
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
    @OneToMany(mappedBy = "fkFuncionario")
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
    public int hashCode() {
        int hash = 0;
        hash += (pkFuncionario != null ? pkFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.pkFuncionario == null && other.pkFuncionario != null) || (this.pkFuncionario != null && !this.pkFuncionario.equals(other.pkFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.TransferenciaService.entities.Funcionario[ pkFuncionario=" + pkFuncionario + " ]";
    }
    
}
