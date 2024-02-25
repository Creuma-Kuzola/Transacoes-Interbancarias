/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.utils.pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.entities.Transferencia;

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
    @JsonProperty("pkTransferencia")
    private Integer pkTransferencia;
    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("montante")
    private BigDecimal montante;

    @JsonProperty("ibanDestinatario")
    private String ibanDestinatario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("datahora")
    private Date datahora;

    @JsonProperty("fkContaBancariaOrigem")
    private Integer fkContaBancariaOrigem;

    @JsonProperty("tipoTransferencia")
    private String tipoTransferencia;

    @JsonProperty("estadoTransferencia")
    private String estadoTransferencia;

    @JsonProperty("codigoTransferencia")
    private String codigoTransferencia;

    @JsonProperty("bancoUdentifier")
    private Integer bancoUdentifier;
    @JsonProperty("ibanOrigem")
    private String ibanOrigem;

    public static Transferencia convertingIntoTransferencia(TransferenciaPOJO transferenciaPOJO){
        System.out.println("Iban Destina em transf: "+ transferenciaPOJO.getIbanDestinatario());
        Transferencia transferencia = new Transferencia();
        transferencia.setDescricao(transferenciaPOJO.getDescricao());
        transferencia.setMontante(transferenciaPOJO.getMontante());
        transferencia.setIbanDestinatario(transferenciaPOJO.getIbanDestinatario());
        System.out.println("Iban Destina em transf 2: "+ transferencia.getIbanDestinatario());
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"pkTransferencia\": ").append(pkTransferencia).append(",");
        sb.append("\"descricao\": \"").append(descricao).append("\",");
        sb.append("\"montante\": ").append(montante).append(",");
        sb.append("\"ibanDestinatario\": \"").append(ibanDestinatario).append("\",");
        sb.append("\"datahora\": \"").append(datahora).append("\",");
        sb.append("\"fkContaBancariaOrigem\": ").append(fkContaBancariaOrigem).append(",");
        sb.append("\"tipoTransferencia\": \"").append(tipoTransferencia).append("\",");
        sb.append("\"estadoTransferencia\": \"").append(estadoTransferencia).append("\",");
        sb.append("\"codigoTransferencia\": \"").append(codigoTransferencia).append("\"");
        sb.append("}");
        return sb.toString();
    }

}
