/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import ucan.edu.utils.enums.StatusContaBancaria;

/**
 *
 * @author jussyleitecode
 */
@Entity
@Table(name = "conta_bancaria", catalog = "bank404", schema = "public")
@NamedQueries(
        {
            @NamedQuery(name = "ContaBancaria.findAll", query = "SELECT c FROM ContaBancaria c"),
            @NamedQuery(name = "ContaBancaria.findByPkContaBancaria", query = "SELECT c FROM ContaBancaria c WHERE c.pkContaBancaria = :pkContaBancaria"),
            @NamedQuery(name = "ContaBancaria.findByNumeroDeConta", query = "SELECT c FROM ContaBancaria c WHERE c.numeroDeConta = :numeroDeConta"),
            @NamedQuery(name = "ContaBancaria.findByIban", query = "SELECT c FROM ContaBancaria c WHERE c.iban = :iban"),
            @NamedQuery(name = "ContaBancaria.findByStatus", query = "SELECT c FROM ContaBancaria c WHERE c.status = :status"),
            @NamedQuery(name = "ContaBancaria.findByDataCriacao", query = "SELECT c FROM ContaBancaria c WHERE c.dataCriacao = :dataCriacao")
        })
public class ContaBancaria implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_conta_bancaria", nullable = false)
    private Integer pkContaBancaria;
    @Basic(optional = false)
    @Column(name = "numero_de_conta", nullable = false)
    private int numeroDeConta;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String iban;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    @Enumerated(value = EnumType.STRING)
    private StatusContaBancaria status;
    @Basic(optional = false)
    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    private BigDecimal saldoContabilistico;
    @Column(name = "saldo_disponivel", precision = 11, scale = 3)
    private BigDecimal saldoDisponivel;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente", nullable = false)
    @Column(length = 2147483647)
    private String moeda;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    @ManyToOne
    private Cliente fkCliente;

    public ContaBancaria()
    {
    }

    public ContaBancaria(Integer pkContaBancaria)
    {
        this.pkContaBancaria = pkContaBancaria;
    }

    public Integer getPkContaBancaria()
    {
        return pkContaBancaria;
    }

    public void setPkContaBancaria(Integer pkContaBancaria)
    {
        this.pkContaBancaria = pkContaBancaria;
    }

    public int getNumeroDeConta()
    {
        return numeroDeConta;
    }

    public void setNumeroDeConta(int numeroDeConta)
    {
        this.numeroDeConta = numeroDeConta;
    }

    public String getIban()
    {
        return iban;
    }

    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public StatusContaBancaria getStatus()
    {
        return status;
    }

    public void setStatus(StatusContaBancaria status)
    {
        this.status = status;
    }

    public Date getDataCriacao()
    {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao)
    {
        this.dataCriacao = dataCriacao;
    }

    public Cliente getFkCliente()
    {
        return fkCliente;
    }

    public void setFkCliente(Cliente fkCliente)
    {
        this.fkCliente = fkCliente;
    }

    public BigDecimal getSaldoContabilistico()
    {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(BigDecimal saldoContabilistico)
    {
        this.saldoContabilistico = saldoContabilistico;
    }

    public BigDecimal getSaldoDisponivel()
    {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel)
    {
        this.saldoDisponivel = saldoDisponivel;
    }

    public String getMoeda()
    {
        return moeda;
    }

    public void setMoeda(String moeda)
    {
        this.moeda = moeda;
    }
    
    

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pkContaBancaria != null ? pkContaBancaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContaBancaria))
        {
            return false;
        }
        ContaBancaria other = (ContaBancaria) object;
        if ((this.pkContaBancaria == null && other.pkContaBancaria != null) || (this.pkContaBancaria != null && !this.pkContaBancaria.equals(other.pkContaBancaria)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ucan.edu.entities.ContaBancaria[ pkContaBancaria=" + pkContaBancaria + " ]";
    }

}
