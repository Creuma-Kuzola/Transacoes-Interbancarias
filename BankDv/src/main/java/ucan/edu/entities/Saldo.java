package ucan.edu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "saldo")
public class Saldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_saldo", nullable = false)
    private Integer pkSaldo;
    @Column(name = "saldo_contabilistic")
    private BigInteger saldoContabilistic;
    @Column(name = "saldo_disponivel")
    private BigInteger saldoDisponivel;
    @Column(length = 2147483647)
    private String moeda;
    @Column(name = "data_insercao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInsercao;
    @JoinColumn(name = "fk_conta_bancaria", referencedColumnName = "pk_conta_bancaria")
    @ManyToOne
    private ContaBancaria fkContaBancaria;
}
