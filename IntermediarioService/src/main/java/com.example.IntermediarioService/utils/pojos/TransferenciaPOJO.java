/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.utils.pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.example.IntermediarioService.entities.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jussyleitecode
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaPOJO
{
    private Integer pkTransferencia;
    private String descricao;
    private BigDecimal montante;
    private String ibanDestinatario;
    private Date datahora;
    private BigInteger fkContaBancariaOrigem;
    private String tipoTransferencia;
    private String estadoTransferencia;
    private String codigoTransferencia;
    private Integer bancoUdentifier;

    private String ibanOrigem;


    public static Transferencia convertingIntoTransferenciaEmis(TransferenciaPOJO transferenciaPOJO)
    {
        Transferencia transferencia = new Transferencia();
        transferencia.setEstadoTransferencia(transferenciaPOJO.getEstadoTransferencia());
        transferencia.setDataHora(transferenciaPOJO.getDatahora());
        transferencia.setDescricao(transferenciaPOJO.getDescricao());
        transferencia.setIbanDestinatario(transferenciaPOJO.getIbanDestinatario());
        transferencia.setibanOrigem(transferenciaPOJO.getIbanOrigem());
        transferencia.setTipoTransferencia(transferenciaPOJO.getTipoTransferencia());
        transferencia.setMontante(transferenciaPOJO.getMontante());
        transferencia.setPkTransferencia(transferenciaPOJO.getPkTransferencia());

        return transferencia;
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

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public BigInteger getFkContaBancariaOrigem() {
        return fkContaBancariaOrigem;
    }

    public void setFkContaBancariaOrigem(BigInteger fkContaBancariaOrigem) {
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

    public Integer getBancoUdentifier() {
        return bancoUdentifier;
    }

    public void setBancoUdentifier(Integer bancoUdentifier) {
        this.bancoUdentifier = bancoUdentifier;
    }

    public String getIbanOrigem() {
        return ibanOrigem;
    }
    public void setIbanOrigem(String ibanOrigem) {
        this.ibanOrigem = ibanOrigem;
    }
}
