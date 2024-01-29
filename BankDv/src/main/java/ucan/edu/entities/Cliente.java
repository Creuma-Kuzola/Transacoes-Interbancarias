package ucan.edu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_cliente", nullable = false)
    private Integer pkCliente;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String telefone;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String email;
    @JoinColumn(name = "fk_empresa", referencedColumnName = "pk_empresa")
    @ManyToOne
    private Empresa fkEmpresa;
    @JoinColumn(name = "fk_pessoa", referencedColumnName = "pk_pessoa")
    @ManyToOne
    private Pessoa fkPessoa;
    @OneToMany(mappedBy = "fkCliente")
    @JsonIgnore
    private List<Conta> contaList;
}
