package ucan.edu.dtos;

import ucan.edu.entities.Cliente;
import ucan.edu.utils.enums.UserRole;

public record SignUpDto(
        String login,
        String password,
        UserRole role,
        Cliente fkCliente)
        {

    
}
