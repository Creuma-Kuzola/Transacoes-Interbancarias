package ucan.edu.dtos;


public class SaldoContaDTO {
    private Integer numeroDaConta;
    private Integer saldoDisponivel;
    private Integer saldoContabilistico;
    private String titular;

    public SaldoContaDTO() {
    }

    public SaldoContaDTO(Integer numeroDaConta, Integer saldoDisponivel, Integer saldoContabilistico, String titular) {
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

    public Integer getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(Integer saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public Integer getSaldoContabilistico() {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(Integer saldoContabilistico) {
        this.saldoContabilistico = saldoContabilistico;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
