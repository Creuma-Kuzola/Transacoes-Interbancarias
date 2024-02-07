/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
