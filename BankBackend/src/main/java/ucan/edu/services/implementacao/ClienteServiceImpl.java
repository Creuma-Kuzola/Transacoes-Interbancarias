/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.math.BigInteger;
import ucan.edu.entities.Cliente;
import ucan.edu.services.ClienteService;
import org.springframework.stereotype.Service;
import ucan.edu.entities.Conta;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.Saldo;
import ucan.edu.repository.ClienteRepository;

/**
 *
 * @author jussyleitecode
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer> implements ClienteService
{

    private final ContaBancariaServiceImpl contaBancariaServiceImpl;
    private final ClienteRepository clienteRepository;

    private String respoTemp = "";

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
        ContaBancaria contaBancariaCreated = contaBancariaServiceImpl.createAccount(contaBancaria);

        Saldo saldo = new Saldo();

        saldo.setFkContaBancaria(contaBancaria);
        saldo.setMoeda("kz");
        saldo.setSaldoContabilistic(BigInteger.ONE);
        saldo.setSaldoDisponivel(BigInteger.ONE);

        respoTemp += "Cliente: " + cliente.getFkPessoa().getNome() + " \n"
                + "Numero de conta: " + contaBancaria.getNumeroDeConta() + " \n"
                + "IBAN: " + contaBancaria.getIban() + "\n"
                + "DataAbertura de cont: " + contaBancaria.getDataCriacao();

        return respoTemp;
    }

}
