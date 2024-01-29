package ucan.edu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "conta_bancaria")
public class ContaBancaria implements Serializable {

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
    private String status;
    @Basic(optional = false)
    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    @OneToMany(mappedBy = "fkContaBancaria")
    private List<Saldo> saldoList;
    @ManyToOne
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    private Cliente fk_cliente;
}
