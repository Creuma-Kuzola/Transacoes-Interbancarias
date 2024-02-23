/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "kuzola_bank", schema = "public")

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_pessoa", nullable = false)
    private Integer pkPessoa;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nome;
    @Basic(optional = false)
    @Column(name = "data_nascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Basic(optional = false)
    @Column(name = "numero_do_bi", nullable = false, length = 2147483647)
    private String numeroDoBi;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Column(length = 2147483647)
    private String sexo;
    @JoinColumn(name = "fk_localizacao", referencedColumnName = "pk_localizacao")
    @ManyToOne
    private Localizacao fkLocalizacao;

    public Pessoa() {
    }

    public Pessoa(Integer pkPessoa) {
        this.pkPessoa = pkPessoa;
    }

    public Pessoa(Integer pkPessoa, String nome, Date dataNascimento, String numeroDoBi, String nif) {
        this.pkPessoa = pkPessoa;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.numeroDoBi = numeroDoBi;
        this.nif = nif;
    }

    public Integer getPkPessoa() {
        return pkPessoa;
    }

    public void setPkPessoa(Integer pkPessoa) {
        this.pkPessoa = pkPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumeroDoBi() {
        return numeroDoBi;
    }

    public void setNumeroDoBi(String numeroDoBi) {
        this.numeroDoBi = numeroDoBi;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Localizacao getFkLocalizacao() {
        return fkLocalizacao;
    }

    public void setFkLocalizacao(Localizacao fkLocalizacao) {
        this.fkLocalizacao = fkLocalizacao;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "pkPessoa=" + pkPessoa + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", numeroDoBi=" + numeroDoBi + ", nif=" + nif + ", sexo=" + sexo + ", fkLocalizacao=" + fkLocalizacao + '}';
    }


    
}
