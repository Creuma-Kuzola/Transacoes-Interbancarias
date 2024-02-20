/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.entities;

import java.io.Serializable;
import java.util.Date;
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
@AllArgsConstructor
@NoArgsConstructor

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

    @Override
    public String toString() {
        return "Pessoa{" +
                "pkPessoa=" + pkPessoa +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", numeroDoBi='" + numeroDoBi + '\'' +
                ", nif='" + nif + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fkLocalizacao=" + fkLocalizacao +
                '}';
    }
}
