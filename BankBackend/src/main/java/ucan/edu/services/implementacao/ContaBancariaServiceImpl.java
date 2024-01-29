/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import org.springframework.stereotype.Service;
import ucan.edu.exceptions.ContaBancariaNotActivatedException;
import ucan.edu.exceptions.ContaBancariaNotFoundException;
import ucan.edu.repository.ContaBancariaRepository;
import ucan.edu.utils.enums.StatusContaBancaria;

/**
 *
 * @author jussyleite
 */
@Service
public class ContaBancariaServiceImpl extends AbstractService<ContaBancaria, Integer>
        implements ContaBancariaService
{

    private Integer numberAccount;
    private final Integer BANKNUMBER = 0404;

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaServiceImpl(ContaBancariaRepository contaBancariaRepository)
    {
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public Integer creatAccountNumber()
    {
        Random random = new Random();
        // Gerar um número aleatório com 4 dígitos
        numberAccount = random.nextInt(9000) + 1000;
        // Exibir o número gerado
        System.out.println("Número Aleatório: " + numberAccount);
        return numberAccount;
    }

    public String createIban()
    {
        String iban = " ";
        Integer accountCustomerNumber = numberAccount;
        iban = "E " + BANKNUMBER + " " + accountCustomerNumber;
        return iban;
    }

    public boolean activateAccount(ContaBancaria contaBancaria)
    {
        Optional<ContaBancaria> contaBancariaFound = contaBancariaRepository
                .findById(contaBancaria.getPkContaBancaria());

        if (contaBancariaFound.isPresent())
        {
            contaBancaria.setStatus(StatusContaBancaria.ACTIVO);
            contaBancariaRepository.save(contaBancaria);
            return true;
        }
        return false;
    }

    public ContaBancaria depositeAmountOfMoney(ContaBancaria contaBancaria, Integer quantia)
    {
        Optional<ContaBancaria> contaBancariaFound = contaBancariaRepository
                .findContaBancariaByNumeroDeConta(contaBancaria.getNumeroDeConta());

        ContaBancaria contaBancariaIsActive = contaBancariaRepository.
                isContaBancariaActived(contaBancaria.getNumeroDeConta(), StatusContaBancaria.ACTIVO);
        if (!contaBancariaFound.isPresent())
        {
            throw new ContaBancariaNotFoundException();
        } else if (contaBancariaIsActive == null)
        {
            throw new ContaBancariaNotActivatedException();
        }

        Integer novoSaldo = contaBancariaFound.get().getSaldoDisponivel();

        novoSaldo += quantia;

        contaBancaria.setSaldoContabilistico(novoSaldo);
        contaBancaria.setSaldoDisponivel(novoSaldo);

        ContaBancaria contaBancariaActualizada = contaBancariaRepository.save(contaBancaria);
        return contaBancariaActualizada;
    }

    //777
    public ContaBancaria transferMoneyToAccount()
    {

        /// TRANSFERIA BANCO A BANCO :::: ......------ :::::
        return null;
    }

    public ContaBancaria createAccount(ContaBancaria conta)
    {
        try
        {
            Integer accoutNumber = this.creatAccountNumber();
            String iban = createIban();

            conta.setNumeroDeConta(accoutNumber);
            conta.setIban(iban);
            conta.setStatus(StatusContaBancaria.INACTIVO);
            conta.setDataCriacao(new Date());
            //conta.setFk_cliente(fk_cliente);

            ContaBancaria contaCreated = contaBancariaRepository.save(conta);

            return contaCreated;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
