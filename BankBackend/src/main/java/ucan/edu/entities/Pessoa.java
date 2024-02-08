/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ucan.edu.utils.enums.Sexo;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "kuzola_bank", schema = "public")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @Enumerated(value = EnumType.STRING)
    private Sexo sexo;
    
    @JsonIgnore
    @OneToMany(mappedBy = "fkPessoa")
    private List<Cliente> clienteList;
    @JoinColumn(name = "fk_localizacao", referencedColumnName = "pk_localizacao")
    @ManyToOne
    @JsonIgnore
    private Localizacao fkLocalizacao;

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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Localizacao getFkLocalizacao() {
        return fkLocalizacao;
    }

    public void setFkLocalizacao(Localizacao fkLocalizacao) {
        this.fkLocalizacao = fkLocalizacao;
    }
}
