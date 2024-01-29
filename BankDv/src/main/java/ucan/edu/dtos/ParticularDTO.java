package ucan.edu.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author jussyleitecode
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ParticularDTO {

    private Integer pkParticular;
    private String nome;
    private String sexo;
    private Date dataNascimento;
    private String endereco;
    private String profissao;
    private Integer fkConta;
}
