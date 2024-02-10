package com.example.KuzolaBankService.dto;

import com.example.KuzolaBankService.entities.Funcionario;
import com.example.KuzolaBankService.enums.UserRole;
import com.example.KuzolaBankService.entities.Cliente;



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
