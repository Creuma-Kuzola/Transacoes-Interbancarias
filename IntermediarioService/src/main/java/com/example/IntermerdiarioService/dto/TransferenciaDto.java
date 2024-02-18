/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermerdiarioService.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author creuma
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class TransferenciaDto {

    private Integer pkTransferencia;
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal montante;
    private String ibanDestinatario;
    private Date datahora;
    private String estadoTransferencia;
    private String codigoTransferencia;
    private String tipoTransferencia;
    private ContaBancaria fkContaBancariaOrigem;
}
