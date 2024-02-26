/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
@Table(catalog = "kuzola_bank", schema = "public")@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_transferencia", nullable = false)
    private Integer pkTransferencia;
    @Column(length = 2147483647)
    private String descricao;
    @Basic(optional = false)
    @Column(name = "montante", precision = 11, scale = 3, nullable = false)
    private BigDecimal montante;
    @Column(name = "iban_destinatario", length = 2147483647)
    private String ibanDestinatario;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @Column(name = "fk_conta_bancaria_origem")
    private Integer fkContaBancariaOrigem;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(name = "codigo_transferencia", length = 2147483647)
    private String codigoTransferencia;
    @Column(name = "operacao", length = 2147483647)
    private String operacao;

    @Column(name = "iban_origem", length = 2147483647)
    private String ibanOrigem;
    @OneToMany(mappedBy = "fkTransferencia")
    private List<TokenValidacao> tokenValidacaoList;

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

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Integer getFkContaBancariaOrigem() {
        return fkContaBancariaOrigem;
    }

    public void setFkContaBancariaOrigem(Integer fkContaBancariaOrigem) {
        this.fkContaBancariaOrigem = fkContaBancariaOrigem;
    }

    public String getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(String tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public String getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(String estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getCodigoTransferencia() {
        return codigoTransferencia;
    }

    public void setCodigoTransferencia(String codigoTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getIbanOrigem() {
        return ibanOrigem;
    }

    public void setIbanOrigem(String ibanOrigem) {
        this.ibanOrigem = ibanOrigem;
    }
}
