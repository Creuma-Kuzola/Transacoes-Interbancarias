/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.utils.custom.model;

import java.util.Date;
import ucan.edu.entities.Empresa;
import ucan.edu.entities.Pessoa;
import ucan.edu.utils.enums.StatusContaBancaria;

/**
 *
 * @author jussyleitecode
 */
public class ContaBancariaClienteResponse
{

    private Integer pkPessoa;
    private String nome;
    private Date dataNascimento;
    private String numeroDoBi;
    private String nif;
    private String sexo;

    private Integer pkCliente;
    private String telefone;
    private String email;
    private Pessoa fkPessoa;

    private Integer pkContaBancaria;
    private int numeroDeConta;
    private String iban;
    private StatusContaBancaria status;
    private Date dataCriacao;

}
