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
public class Localizacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_localizacao", nullable = false)
    private Integer pkLocalizacao;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String designacao;
    @OneToMany(mappedBy = "fkLocalizacao")
    @JsonIgnore
    private List<Pessoa> pessoaList;
    @OneToMany(mappedBy = "fkLocalizacao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Empresa> empresaList;

    @Override
    public String toString() {
        return "Localizacao{" +
                "pkLocalizacao=" + pkLocalizacao +
                ", designacao='" + designacao + '\'' +
                '}';
    }
}
