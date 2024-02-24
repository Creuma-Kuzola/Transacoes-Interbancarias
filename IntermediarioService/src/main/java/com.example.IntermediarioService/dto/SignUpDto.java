package com.example.IntermediarioService.dto;


import com.example.IntermediarioService.entities.Funcionario;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.utils.pojos.UserRole;


public record SignUpDto(
        String login,
        String password,
        UserRole role,
        Cliente fkCliente,
        Funcionario fkFuncionario
        )

        {

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public UserRole getRole()
    {
        return role;
    }

    public Cliente getFkCliente()
    {
        return fkCliente;
    }

    @Override
    public Funcionario fkFuncionario() {
        return fkFuncionario;
    }



   }
