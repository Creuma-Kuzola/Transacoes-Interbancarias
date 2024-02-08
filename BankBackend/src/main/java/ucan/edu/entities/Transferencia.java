/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author creuma
 */
@Entity
@Table(catalog = "kuzola_bank", schema = "public")@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_transferencia", nullable = false)
    private Integer pkTransferencia;
    @Column(length = 2147483647)
    private String descricao;
    @Basic(optional = false)
    @Column(nullable = false)
    private BigInteger montante;
    @Column(name = "iban_destinatario", length = 2147483647)
    private String ibanDestinatario;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datahora;
    @Column(name = "fk_conta_bancaria_origem")
    private Integer fkContaBancariaOrigem;
    @Column(name = "tipo_transferencia", length = 2147483647)
    private String tipoTransferencia;
    @Column(name = "estado_transferencia", length = 2147483647)
    private String estadoTransferencia;
    @Column(name = "codigo_transferencia", length = 2147483647)
    private String codigoTransferencia;
    @OneToMany(mappedBy = "fkTransferencia")
    private List<TokenValidacao> tokenValidacaoList;
    
    
    
}
