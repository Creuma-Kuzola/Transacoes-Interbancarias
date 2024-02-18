/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermerdiarioService.dto;

import lombok.*;

import java.math.BigInteger;
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

public class ContaBancaria {

    private Integer pkContaBancaria;
    private String iban;
    private String status;
    private Date dataCriacao;
    private BigInteger numeroDeConta;
    private Cliente fkCliente;

}
