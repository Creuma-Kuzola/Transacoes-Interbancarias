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
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByPkCliente", query = "SELECT c FROM Cliente c WHERE c.pkCliente = :pkCliente"),
    @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome"),
    @NamedQuery(name = "Cliente.findByIban", query = "SELECT c FROM Cliente c WHERE c.iban = :iban"),
    @NamedQuery(name = "Cliente.findByNumeroDeConta", query = "SELECT c FROM Cliente c WHERE c.numeroDeConta = :numeroDeConta")})
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
    @OneToMany(mappedBy = "fkCliente")
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
    public int hashCode() {
        int hash = 0;
        hash += (pkCliente != null ? pkCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.pkCliente == null && other.pkCliente != null) || (this.pkCliente != null && !this.pkCliente.equals(other.pkCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.TransferenciaService.entities.Cliente[ pkCliente=" + pkCliente + " ]";
    }
    
}
