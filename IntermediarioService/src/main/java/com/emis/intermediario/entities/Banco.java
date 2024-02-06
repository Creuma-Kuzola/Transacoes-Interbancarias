/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emis.intermediario.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

/**
 *
 * @author jussyleitecode
 */
@Entity
@Table(catalog = "emis", schema = "public")
@NamedQueries(
{
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b"),
    @NamedQuery(name = "Banco.findByPkBanco", query = "SELECT b FROM Banco b WHERE b.pkBanco = :pkBanco"),
    @NamedQuery(name = "Banco.findByNomeBanco", query = "SELECT b FROM Banco b WHERE b.nomeBanco = :nomeBanco"),
    @NamedQuery(name = "Banco.findByDataCriacaoBanco", query = "SELECT b FROM Banco b WHERE b.dataCriacaoBanco = :dataCriacaoBanco"),
    @NamedQuery(name = "Banco.findByContactoTelefonico", query = "SELECT b FROM Banco b WHERE b.contactoTelefonico = :contactoTelefonico"),
    @NamedQuery(name = "Banco.findByEmail", query = "SELECT b FROM Banco b WHERE b.email = :email"),
    @NamedQuery(name = "Banco.findByContactoTelef\u00f3nico", query = "SELECT b FROM Banco b WHERE b.contactoTelef\u00f3nico = :contactoTelef\u00f3nico")
})
public class Banco implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pk_banco", nullable = false)
    private Integer pkBanco;
    @Column(name = "nome_banco", length = 2147483647)
    private String nomeBanco;
    @Column(name = "data_criacao_banco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacaoBanco;
    @Column(name = "contacto_telefonico", length = 2147483647)
    private String contactoTelefonico;
    @Column(length = 2147483647)
    private String email;
    @Column(name = "contacto_telef\u00f3nico", length = 2147483647)
    private String contactoTelefónico;
    @OneToMany(mappedBy = "fkBanco")
    private List<HistoricoTransferencia> historicoTransferenciaList;

    public Banco()
    {
    }

    public Banco(Integer pkBanco)
    {
        this.pkBanco = pkBanco;
    }

    public Integer getPkBanco()
    {
        return pkBanco;
    }

    public void setPkBanco(Integer pkBanco)
    {
        this.pkBanco = pkBanco;
    }

    public String getNomeBanco()
    {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco)
    {
        this.nomeBanco = nomeBanco;
    }

    public Date getDataCriacaoBanco()
    {
        return dataCriacaoBanco;
    }

    public void setDataCriacaoBanco(Date dataCriacaoBanco)
    {
        this.dataCriacaoBanco = dataCriacaoBanco;
    }

    public String getContactoTelefonico()
    {
        return contactoTelefonico;
    }

    public void setContactoTelefonico(String contactoTelefonico)
    {
        this.contactoTelefonico = contactoTelefonico;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getContactoTelefónico()
    {
        return contactoTelefónico;
    }

    public void setContactoTelefónico(String contactoTelefónico)
    {
        this.contactoTelefónico = contactoTelefónico;
    }

    public List<HistoricoTransferencia> getHistoricoTransferenciaList()
    {
        return historicoTransferenciaList;
    }

    public void setHistoricoTransferenciaList(List<HistoricoTransferencia> historicoTransferenciaList)
    {
        this.historicoTransferenciaList = historicoTransferenciaList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pkBanco != null ? pkBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banco))
        {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.pkBanco == null && other.pkBanco != null) || (this.pkBanco != null && !this.pkBanco.equals(other.pkBanco)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.emis.intermediario.entities.Banco[ pkBanco=" + pkBanco + " ]";
    }
    
}
