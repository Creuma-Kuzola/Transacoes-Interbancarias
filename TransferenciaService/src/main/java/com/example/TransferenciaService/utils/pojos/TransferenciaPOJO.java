/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.utils.pojos;

import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jussyleitecode
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaPOJO
{

    private Integer pkTransferencia;
    private String descricao;
    private BigInteger montante;
    private String ibanDestinatario;
    private Date datahora;
    private Integer fkContaBancariaOrigem;
    private String tipoTransferencia;
    private String estadoTransferencia;
    private String codigoTransferencia;

}
