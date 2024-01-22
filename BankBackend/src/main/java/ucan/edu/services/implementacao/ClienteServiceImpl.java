/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import ucan.edu.entities.Cliente;
import ucan.edu.services.ClienteService;
import org.springframework.stereotype.Service;
import ucan.edu.entities.Conta;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.repository.ClienteRepository;

/**
 *
 * @author creuma
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer> implements ClienteService
{

    private final ContaBancariaServiceImpl contaBancariaServiceImpl;
    private final ClienteRepository clienteRepository;

    private String respoTemp = " ";

    public ClienteServiceImpl(ContaBancariaServiceImpl ContaBancariaServiceImpl,
            ClienteRepository clienteRepository)
    {
        this.contaBancariaServiceImpl = ContaBancariaServiceImpl;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public String createCustomerAccount(Cliente t)
    {
        ContaBancaria contaBancaria = new ContaBancaria();

        Cliente cliente = clienteRepository.save(t);
        contaBancaria.setFkCliente(cliente);
        //Conta conta = new Conta();
        contaBancariaServiceImpl.createAccount(contaBancaria);

        respoTemp += "Cliente: " + cliente.getFkPessoa().getNome() + " \n"
                + "Numero de conta: " + contaBancaria.getNumeroDeConta() + " \n"
                + "IBAN: " + contaBancaria.getIban() + "\n"
                + "DataAbertura de cont: " + contaBancaria.getDataCriacao();

        return respoTemp;
    }

}
