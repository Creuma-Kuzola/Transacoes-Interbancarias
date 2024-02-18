/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;


/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "transferencia_bd", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Transferencia.findAll", query = "SELECT t FROM Transferencia t"),
    @NamedQuery(name = "Transferencia.findByPkTransferencia", query = "SELECT t FROM Transferencia t WHERE t.pkTransferencia = :pkTransferencia"),
    @NamedQuery(name = "Transferencia.findByDescricao", query = "SELECT t FROM Transferencia t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "Transferencia.findByMontante", query = "SELECT t FROM Transferencia t WHERE t.montante = :montante"),
    @NamedQuery(name = "Transferencia.findByIbanDestinatario", query = "SELECT t FROM Transferencia t WHERE t.ibanDestinatario = :ibanDestinatario"),
    @NamedQuery(name = "Transferencia.findByDataHora", query = "SELECT t FROM Transferencia t WHERE t.dataHora = :dataHora"),
    @NamedQuery(name = "Transferencia.findByEstadoTransferencia", query = "SELECT t FROM Transferencia t WHERE t.estadoTransferencia = :estadoTransferencia"),
    @NamedQuery(name = "Transferencia.findByCanal", query = "SELECT t FROM Transferencia t WHERE t.canal = :canal"),
    @NamedQuery(name = "Transferencia.findByTipoTransferencia", query = "SELECT t FROM Transferencia t WHERE t.tipoTransferencia = :tipoTransferencia"),
    @NamedQuery(name = "Transferencia.findByContaBancariaOrigem", query = "SELECT t FROM Transferencia t WHERE t.contaBancariaOrigem = :contaBancariaOrigem")})
public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_transferencia", nullable = false)
    private Integer pkTransferencia;
    @Column(length = 2147483647)
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 11, scale = 3)
    private BigDecimal montante;
    @Basic(optional = false)
    @Column(name = "iban_destinatario", nullable = false, length = 2147483647)
    private String ibanDestinatario;
    @Basic(optional = false)
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(length = 2147483647)
    private String canal;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @Column(name = "conta_bancaria_origem", length = 2147483647)
    private String contaBancariaOrigem;
    @JoinColumn(name = "fk_banco", referencedColumnName = "pk_banco")
    @ManyToOne
    private Banco fkBanco;

    public Transferencia() {
    }

    public Transferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }

    public Transferencia(Integer pkTransferencia, BigDecimal montante, String ibanDestinatario, Date dataHora) {
        this.pkTransferencia = pkTransferencia;
        this.montante = montante;
        this.ibanDestinatario = ibanDestinatario;
        this.dataHora = dataHora;
    }

    public Integer getPkTransferencia() {
        return pkTransferencia;
    }

    public void setPkTransferencia(Integer pkTransferencia) {
        this.pkTransferencia = pkTransferencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public String getIbanDestinatario() {
        return ibanDestinatario;
    }

    public void setIbanDestinatario(String ibanDestinatario) {
        this.ibanDestinatario = ibanDestinatario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(String estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getContaBancariaOrigem() {
        return contaBancariaOrigem;
    }

    public void setContaBancariaOrigem(String contaBancariaOrigem) {
        this.contaBancariaOrigem = contaBancariaOrigem;
    }

    public Banco getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(Banco fkBanco) {
        this.fkBanco = fkBanco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkTransferencia != null ? pkTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transferencia)) {
            return false;
        }
        Transferencia other = (Transferencia) object;
        if ((this.pkTransferencia == null && other.pkTransferencia != null) || (this.pkTransferencia != null && !this.pkTransferencia.equals(other.pkTransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.TransferenciaService.entities.Transferencia[ pkTransferencia=" + pkTransferencia + " ]";
    }
    
}
