/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
to edit this template ...
 */
package ucan.edu.services.implementacao;

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ucan.edu.entities.Cliente;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.entities.Pessoa;
import ucan.edu.services.implementacao.ContaBancariaServiceImpl;
import ucan.edu.utils.enums.Sexo;
import ucan.edu.utils.enums.StatusContaBancaria;

/**
 *
 * @author jussyleitecode
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ContaBancariaServiceImplTest
{

    @Autowired
    ContaBancariaServiceImpl contaBancariaServiceImpl;

    @Test
    public void createAccountNumberTest()
    {

        //cenário
        //acção execução
        Integer str = contaBancariaServiceImpl.creatAccountNumber();

        //verificação
        Assertions.assertThat(str).isGreaterThan(0);
    }

    // @Test
    public void activateAccountToStatusActivoAndReturnTrueTest()
    {
        //cenario
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setPkContaBancaria(2);

        //acção execução
        boolean isActivated = contaBancariaServiceImpl.activateAccount(contaBancaria);

        //verificação
        Assertions.assertThat(isActivated).isTrue();
    }

    //@Test
    public void depositeAmountOfMoneyAndReturnTheAccount()
    {
        //Cenario
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setNumeroDeConta(5831);
        contaBancaria.setPkContaBancaria(1);
        contaBancaria.setIban("E 260 5831");
        contaBancaria.setStatus(StatusContaBancaria.ACTIVO);
        contaBancaria.setDataCriacao(new Date());
        contaBancaria.setSaldoContabilistico(100);
        contaBancaria.setSaldoDisponivel(100);
        contaBancaria.setMoeda("KZ");

        Pessoa pessoa = new Pessoa();

        pessoa.setPkPessoa(4);
        pessoa.setNome("Marta Nuenes");
        pessoa.setDataNascimento(new Date());
        pessoa.setNumeroDoBi("192893LAD");
        pessoa.setNif("192893LAD");
        pessoa.setSexo(Sexo.MASCULINO);

        Cliente cliente = new Cliente();

        cliente.setPkCliente(2);
        cliente.setTelefone("93456123");
        cliente.setEmail("martanunues@gmail.com");
        cliente.setFkEmpresa(null);

        //Accao
        Mockito.when(contaBancariaServiceImpl.depositeAmountOfMoney(contaBancaria, 200)).thenReturn(contaBancaria);
    }

}
