/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ucan.edu.config.component.ClienteComponent;
import ucan.edu.entities.Cliente;
import ucan.edu.entities.User;
import ucan.edu.kafka.KafkaTransferenciaProducer;
import ucan.edu.repository.UserRepository;
import ucan.edu.services.ClienteService;
import org.springframework.stereotype.Service;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.repository.ClienteRepository;
import ucan.edu.utils.enums.UserRole;
import ucan.edu.utils.jsonUtils.CustomJsonPojos;
import ucan.edu.utils.pojos.ClientePOJO;

/**
 *
 * @author jussyleitecode
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer> implements ClienteService
{

    private final ContaBancariaServiceImpl contaBancariaServiceImpl;
    private final ClienteRepository clienteRepository;
    @Autowired
    private ClienteComponent clienteComponent;
    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;

    @Autowired
    private UserRepository userRepository;

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
        contaBancaria.setSaldoContabilistico(BigDecimal.ZERO);
        contaBancaria.setSaldoDisponivel(BigDecimal.ZERO);
        contaBancaria.setMoeda("KZ");
       
        ContaBancaria contaBancariaCreated = contaBancariaServiceImpl.createAccount(contaBancaria);

        if (cliente.getFkPessoa() != null)
        {
            respoTemp += "Cliente: " + cliente.getFkPessoa().getNome() + " \n"
                    + "  Numero de conta: " + contaBancaria.getNumeroDeConta() + " \n"
                    + "IBAN: " + contaBancaria.getIban() + "\n"
                    + "DataAbertura de cont: " + contaBancaria.getDataCriacao();
        } else
        {
            respoTemp += "Cliente: " + cliente.getFkEmpresa().getNome() + " \n"
                    + "Numero de conta: " + contaBancaria.getNumeroDeConta() + " \n"
                    + "IBAN: " + contaBancaria.getIban() + "\n"
                    + "DataAbertura de cont: " + contaBancaria.getDataCriacao();
        }
        saveClientPOJO(contaBancaria, cliente);
        //Send cliente data to intermediario
        return respoTemp;
    }

    private void saveClientPOJO(ContaBancaria contaBancaria, Cliente cliente)
    {
        Map<String, String> clienteMap = new HashMap<>();

        System.out.println("pkCliente: " +cliente.getPkCliente());

        clienteMap.put("pkCliente",""+cliente.getPkCliente());
        clienteMap.put("nome",cliente.getFkPessoa().getNome());
        clienteMap.put("iban", contaBancaria.getIban());
        clienteMap.put("numeroConta",""+contaBancaria.getNumeroDeConta());
        clienteMap.put("fkBanco", "2");

        clienteComponent.setClienteComponent(clienteMap);
    }

    public void saveClientPOJO()
    {

    }





}
