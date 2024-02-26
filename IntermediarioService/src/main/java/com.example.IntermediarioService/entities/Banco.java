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

public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_banco", nullable = false)
    private Integer pkBanco;
    @Column(length = 2147483647)
    private String nome;
    @Column(name = "codigo_identificador_banco")
    private Integer codigoIdentificadorBanco;
    @Column(name = "sigla_banco", length = 2147483647)
    private String siglaBanco;
    @OneToMany(mappedBy = "fkBanco", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transferencia> transferenciaList;
    @OneToMany(mappedBy = "fkBanco", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cliente> clienteList;

    public Banco() {
    }

    public Banco(Integer codigoIdentificadorBanco) {
        this.codigoIdentificadorBanco = codigoIdentificadorBanco;
    }

  /*public Banco(Integer pkBanco) {
        this.pkBanco = pkBanco;
    } */

    public Integer getPkBanco() {
        return pkBanco;
    }

    public void setPkBanco(Integer pkBanco) {
        this.pkBanco = pkBanco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigoIdentificadorBanco() {
        return codigoIdentificadorBanco;
    }

    public void setCodigoIdentificadorBanco(Integer codigoIdentificadorBanco) {
        this.codigoIdentificadorBanco = codigoIdentificadorBanco;
    }

    public String getSiglaBanco() {
        return siglaBanco;
    }

    public void setSiglaBanco(String siglaBanco) {
        this.siglaBanco = siglaBanco;
    }

    public List<Transferencia> getTransferenciaList() {
        return transferenciaList;
    }

    public void setTransferenciaList(List<Transferencia> transferenciaList) {
        this.transferenciaList = transferenciaList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "pkBanco=" + pkBanco +
                ", nome='" + nome + '\'' +
                ", codigoIdentificadorBanco=" + codigoIdentificadorBanco +
                ", siglaBanco='" + siglaBanco + '\'' +
                '}';
    }
}
