package ucan.edu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "historico_transferencia_emis")


public class HistoricoTransferenciaEmis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_historico_transferencia_emis", nullable = false)
    private Integer pkHistoricoTransferenciaEmis;
    @Column(name = "fk_transferencia_bancaria")
    private Integer fkTransferenciaBancaria;
    @OneToMany(mappedBy = "fkHistoricoTransferenciaEmis")
    private List<TokenValidacao> tokenValidacaoList;
}
