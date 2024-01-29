package ucan.edu.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_conta", nullable = false)
    private Integer pkConta;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String username;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String password;
    @JoinColumn(name = "fk_cliente", referencedColumnName = "pk_cliente")
    @ManyToOne
    private Cliente fkCliente;
    @JoinColumn(name = "fk_conta_tipo", referencedColumnName = "pk_conta_tipo")
    @ManyToOne
    private ContaTipo fkContaTipo;
}
