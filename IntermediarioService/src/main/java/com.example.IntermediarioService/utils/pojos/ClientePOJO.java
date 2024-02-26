package com.example.IntermediarioService.utils.pojos;


public class ClientePOJO {

    private String pkCliente;
    private String nome;
    private String iban;
    private String numeroConta;
    private  String fkBanco;
    private String login;
    private String password;
    private String role;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(String fkBanco) {
        this.fkBanco = fkBanco;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPkCliente() {
        return pkCliente;
    }

    public void setPkCliente(String pkCliente) {
        this.pkCliente = pkCliente;
    }
}
