/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "bank404", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByPkFuncionario", query = "SELECT f FROM Funcionario f WHERE f.pkFuncionario = :pkFuncionario"),
    @NamedQuery(name = "Funcionario.findByEmail", query = "SELECT f FROM Funcionario f WHERE f.email = :email"),
    @NamedQuery(name = "Funcionario.findByNumeroTelefone", query = "SELECT f FROM Funcionario f WHERE f.numeroTelefone = :numeroTelefone")})

public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_funcionario", nullable = false)
    private Integer pkFuncionario;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String email;
    @Basic(optional = false)
    @Column(name = "numero_telefone", nullable = false, length = 2147483647)
    private String numeroTelefone;
    @JoinColumn(name = "fk_pessoa", referencedColumnName = "pk_pessoa")
    @ManyToOne
    private Pessoa fkPessoa;

    public Funcionario() {
    }

    public Funcionario(Integer pkFuncionario) {
        this.pkFuncionario = pkFuncionario;
    }

    public Funcionario(Integer pkFuncionario, String email, String numeroTelefone) {
        this.pkFuncionario = pkFuncionario;
        this.email = email;
        this.numeroTelefone = numeroTelefone;
    }

    public Integer getPkFuncionario() {
        return pkFuncionario;
    }

    public void setPkFuncionario(Integer pkFuncionario) {
        this.pkFuncionario = pkFuncionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public Pessoa getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(Pessoa fkPessoa) {
        this.fkPessoa = fkPessoa;
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
        return "ucan.edu.entities.Funcionario[ pkFuncionario=" + pkFuncionario + " ]";
    }
    
}
