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
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_empresa", nullable = false)
    private Integer pkEmpresa;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nome;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @OneToMany(mappedBy = "fkEmpresa")
    private List<Cliente> clienteList;
    @JoinColumn(name = "fk_localizacao", referencedColumnName = "pk_localizacao")
    @ManyToOne
    private Localizacao fkLocalizacao;
}
