package ucan.edu.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;


public class SaldoContaDTO {
    private Integer numeroDaConta;
    private BigDecimal saldoDisponivel;
    private BigDecimal saldoContabilistico;
    private String titular;

    public SaldoContaDTO() {
    }

    public SaldoContaDTO(Integer numeroDaConta, BigDecimal saldoDisponivel, BigDecimal saldoContabilistico, String titular) {
        this.numeroDaConta = numeroDaConta;
        this.saldoDisponivel = saldoDisponivel;
        this.saldoContabilistico = saldoContabilistico;
        this.titular = titular;
    }

    public Integer getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(Integer numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public BigDecimal getSaldoContabilistico() {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(BigDecimal saldoContabilistico) {
        this.saldoContabilistico = saldoContabilistico;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
