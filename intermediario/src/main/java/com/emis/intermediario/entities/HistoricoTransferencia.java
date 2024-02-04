/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emis.intermediario.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

/**
 *
 * @author jussyleitecode
 */
@Entity
@Table(name = "historico_transferencia", catalog = "emis", schema = "public")
@NamedQueries(
{
    @NamedQuery(name = "HistoricoTransferencia.findAll", query = "SELECT h FROM HistoricoTransferencia h"),
    @NamedQuery(name = "HistoricoTransferencia.findByPkHistoricoTransferencia", query = "SELECT h FROM HistoricoTransferencia h WHERE h.pkHistoricoTransferencia = :pkHistoricoTransferencia"),
    @NamedQuery(name = "HistoricoTransferencia.findByFkTransferencia", query = "SELECT h FROM HistoricoTransferencia h WHERE h.fkTransferencia = :fkTransferencia"),
    @NamedQuery(name = "HistoricoTransferencia.findByDataHistorico", query = "SELECT h FROM HistoricoTransferencia h WHERE h.dataHistorico = :dataHistorico")
})
public class HistoricoTransferencia implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pk_historico_transferencia", nullable = false)
    private Integer pkHistoricoTransferencia;
    @Column(name = "fk_transferencia")
    private Integer fkTransferencia;
    @Column(name = "data_historico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;
    @JoinColumn(name = "fk_banco", referencedColumnName = "pk_banco")
    @ManyToOne
    private Banco fkBanco;

    public HistoricoTransferencia()
    {
    }

    public HistoricoTransferencia(Integer pkHistoricoTransferencia)
    {
        this.pkHistoricoTransferencia = pkHistoricoTransferencia;
    }

    public Integer getPkHistoricoTransferencia()
    {
        return pkHistoricoTransferencia;
    }

    public void setPkHistoricoTransferencia(Integer pkHistoricoTransferencia)
    {
        this.pkHistoricoTransferencia = pkHistoricoTransferencia;
    }

    public Integer getFkTransferencia()
    {
        return fkTransferencia;
    }

    public void setFkTransferencia(Integer fkTransferencia)
    {
        this.fkTransferencia = fkTransferencia;
    }

    public Date getDataHistorico()
    {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico)
    {
        this.dataHistorico = dataHistorico;
    }

    public Banco getFkBanco()
    {
        return fkBanco;
    }

    public void setFkBanco(Banco fkBanco)
    {
        this.fkBanco = fkBanco;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pkHistoricoTransferencia != null ? pkHistoricoTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoTransferencia))
        {
            return false;
        }
        HistoricoTransferencia other = (HistoricoTransferencia) object;
        if ((this.pkHistoricoTransferencia == null && other.pkHistoricoTransferencia != null) || (this.pkHistoricoTransferencia != null && !this.pkHistoricoTransferencia.equals(other.pkHistoricoTransferencia)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.emis.intermediario.entities.HistoricoTransferencia[ pkHistoricoTransferencia=" + pkHistoricoTransferencia + " ]";
    }
    
}
