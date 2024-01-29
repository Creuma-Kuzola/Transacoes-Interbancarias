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
@Table(name = "localizacao")
public class Localizacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_localizacao", nullable = false)
    private Integer pkLocalizacao;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String designacao;
    @OneToMany(mappedBy = "fkLocalizacao")
    private List<Pessoa> pessoaList;
    @OneToMany(mappedBy = "fkLocalizacao")
    private List<Empresa> empresaList;
}
