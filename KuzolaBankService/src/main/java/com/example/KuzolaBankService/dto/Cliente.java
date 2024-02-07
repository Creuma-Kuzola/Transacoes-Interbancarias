/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.dto;

import lombok.*;

/**
 *
 * @author creuma
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Cliente {

    private Integer pkCliente;
    private String telefone;
    private String email;

}
