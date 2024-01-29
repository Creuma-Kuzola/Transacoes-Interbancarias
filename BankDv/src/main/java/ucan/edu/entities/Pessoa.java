package ucan.edu.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "pessoa")

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_pessoa", nullable = false)
    private Integer pkPessoa;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nome;
    @Basic(optional = false)
    @Column(name = "data_nascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Basic(optional = false)
    @Column(name = "numero_do_bi", nullable = false, length = 2147483647)
    private String numeroDoBi;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nif;
    @Column(length = 2147483647)
    private String sexo;

    @JsonIgnore
    @OneToMany(mappedBy = "fkPessoa")
    private List<Cliente> clienteList;
    @JoinColumn(name = "fk_localizacao", referencedColumnName = "pk_localizacao")
    @ManyToOne
    @JsonIgnore
    private Localizacao fkLocalizacao;
}
