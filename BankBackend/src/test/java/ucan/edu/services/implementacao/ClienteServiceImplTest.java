/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ucan.edu.entities.Cliente;
import ucan.edu.entities.Pessoa;
import ucan.edu.repository.ClienteRepository;
import ucan.edu.utils.enums.Sexo;

/**
 *
 * @author jussyleitecode
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("application_test")
public class ClienteServiceImplTest
{

    @Autowired
    private ContaBancariaServiceImpl contaBancariaServiceImpl;
    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    
    @Test
    public void createCustomerAccountTest()
    {
        //cenario
        Cliente cliente = new Cliente();

        Pessoa pessoa = new Pessoa();

        pessoa.setPkPessoa(1);
        cliente.setFkPessoa(pessoa);
        cliente.setEmail("cliente1@gmail.com");
        cliente.setTelefone("9342343");

        //Ação/ execução
        String reponse = clienteServiceImpl.createCustomerAccount(cliente);

        //verificação
        Assertions.assertThat(reponse).isNotEmpty();
    }

}
