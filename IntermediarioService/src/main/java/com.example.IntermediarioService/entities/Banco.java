/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.entities;

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
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b"),
    @NamedQuery(name = "Banco.findByPkBanco", query = "SELECT b FROM Banco b WHERE b.pkBanco = :pkBanco"),
    @NamedQuery(name = "Banco.findByNome", query = "SELECT b FROM Banco b WHERE b.nome = :nome"),
    @NamedQuery(name = "Banco.findByCodigoIdentificadorBanco", query = "SELECT b FROM Banco b WHERE b.codigoIdentificadorBanco = :codigoIdentificadorBanco"),
    @NamedQuery(name = "Banco.findBySiglaBanco", query = "SELECT b FROM Banco b WHERE b.siglaBanco = :siglaBanco")})
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
    @OneToMany(mappedBy = "fkBanco")
    private List<Transferencia> transferenciaList;
    @OneToMany(mappedBy = "fkBanco")
    private List<Cliente> clienteList;

    public Banco() {
    }

    /*public Banco(Integer codigoIdentificadorBanco) {
        this.codigoIdentificadorBanco = codigoIdentificadorBanco;
    }*/

  public Banco(Integer pkBanco) {
        this.pkBanco = pkBanco;
    }

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
    public int hashCode() {
        int hash = 0;
        hash += (pkBanco != null ? pkBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.pkBanco == null && other.pkBanco != null) || (this.pkBanco != null && !this.pkBanco.equals(other.pkBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.TransferenciaService.entities.Banco[ pkBanco=" + pkBanco + " ]";
    }
    
}
