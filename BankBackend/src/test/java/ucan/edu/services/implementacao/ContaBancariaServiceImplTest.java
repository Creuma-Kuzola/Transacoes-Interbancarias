/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
to edit this template ...
 */
package ucan.edu.services.implementacao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ucan.edu.services.implementacao.ContaBancariaServiceImpl;

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
    ContaBancariaServiceImpl ContaBancariaServiceImpl;

    @Test
    public void createAccountNumberTest()
    {

        //cenário
        //acção execução
        Integer str = ContaBancariaServiceImpl.creatAccountNumber();

        //verificação
        Assertions.assertThat(str).isGreaterThan(0);
    }
}
