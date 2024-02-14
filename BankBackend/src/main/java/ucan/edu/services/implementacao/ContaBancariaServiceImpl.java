/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ucan.edu.dtos.SaldoContaDTO;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import org.springframework.stereotype.Service;
import ucan.edu.exceptions.ContaBancariaNotActivatedException;
import ucan.edu.exceptions.ContaBancariaNotFoundException;
import ucan.edu.exceptions.ContaBancariaWithInvalidIbanException;
import ucan.edu.exceptions.SaldoContaBancariaInferiorException;
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

    public SaldoContaDTO findSaldoContaByNumeroDeConta(Integer numberAccount)
    {
        ContaBancaria conta  = contaBancariaRepository.findSaldoContaBancariaByNumeroDeConta(numberAccount);

        if (conta == null)
        {
            return null;
        }
        else
        {
            SaldoContaDTO saldoContaDTO = new SaldoContaDTO(conta.getNumeroDeConta(),
                    conta.getSaldoDisponivel(), conta.getSaldoContabilistico(),
                    conta.getFkCliente().getFkPessoa() == null
                            ?
                            conta.getFkCliente().getFkEmpresa().getNome()
                            :
                            conta.getFkCliente().getFkPessoa().getNome());
            return saldoContaDTO;
        }

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

    public ContaBancaria transferInterbancariaDebito(Integer numberAccount, Integer montante)
    {
        Optional<ContaBancaria> contaBancariaFoundOrigem = contaBancariaRepository
                .findContaBancariaByNumeroDeConta(numberAccount);

        System.out.println("Saldo Disponivel: " +contaBancariaFoundOrigem.get().getSaldoDisponivel());

       if (contaBancariaFoundOrigem.get().getSaldoDisponivel() < montante)
        {
            throw new SaldoContaBancariaInferiorException();
        }

        Integer novoSaldoContaBancariaOrigem = contaBancariaFoundOrigem.get().getSaldoDisponivel();
        Integer novoSaldoContaBancariaOrigemFinalResult = novoSaldoContaBancariaOrigem - montante;
        System.out.println(" novoSaldoContaBancariaOrigem:  transferInterbancariaDebito() " + novoSaldoContaBancariaOrigemFinalResult);

        contaBancariaFoundOrigem.get().setSaldoContabilistico(novoSaldoContaBancariaOrigemFinalResult);
        contaBancariaFoundOrigem.get().setSaldoDisponivel(novoSaldoContaBancariaOrigemFinalResult);
        contaBancariaRepository.save(contaBancariaFoundOrigem.get());

        return contaBancariaFoundOrigem.get();
    }
    //777
    public List<ContaBancaria> transferMoneyToAccountSameBank(ContaBancaria contaBancaria, String iban, Integer montante)
    {
        Optional<ContaBancaria> contaBancariaFoundOrigem = contaBancariaRepository
                .findContaBancariaByNumeroDeConta(contaBancaria.getNumeroDeConta());

        Optional<ContaBancaria> contaBancariaFound = contaBancariaRepository
                .findContaBancariaByIban(iban);

        ContaBancaria contaBancariaIsActive = contaBancariaRepository.
                isContaBancariaActived(contaBancaria.getNumeroDeConta(), StatusContaBancaria.ACTIVO);

        ContaBancaria contaBancariaIsActiveOrigem = contaBancariaRepository.
                isContaBancariaByIbanActived(iban,
                        StatusContaBancaria.ACTIVO);

        if (!contaBancariaFound.isPresent())
        {
            throw new ContaBancariaWithInvalidIbanException();
        } else if (!contaBancariaFoundOrigem.isPresent())
        {
            throw new ContaBancariaNotFoundException();
        } else if (contaBancariaIsActiveOrigem == null)
        {
            throw new ContaBancariaNotActivatedException();
        } else if (contaBancariaIsActive == null)
        {
            throw new ContaBancariaNotActivatedException();
        }

        if (contaBancariaFoundOrigem.get().getSaldoDisponivel() < montante)
        {
            throw new SaldoContaBancariaInferiorException();
        }

        //get saldoDisponivel da conta do destinatario
        Integer novoSaldoContaDestinatario = contaBancariaFound.get().getSaldoDisponivel();
        novoSaldoContaDestinatario += montante;

        //get saldoDisponivel da conta do destinatario
        Integer novoSaldoContaBancariaOrigem = contaBancariaFoundOrigem.get().getSaldoDisponivel();

        Integer novoSaldoContaBancariaOrigemFinalResult = novoSaldoContaBancariaOrigem - montante;
        System.out.println(" novoSaldoContaBancariaOrigem: " + novoSaldoContaBancariaOrigemFinalResult);

        contaBancariaFound.get().setSaldoContabilistico(novoSaldoContaDestinatario);
        contaBancariaFound.get().setSaldoDisponivel(novoSaldoContaDestinatario);

        contaBancariaFoundOrigem.get().setSaldoContabilistico(novoSaldoContaBancariaOrigemFinalResult);
        contaBancariaFoundOrigem.get().setSaldoDisponivel(novoSaldoContaBancariaOrigemFinalResult);

        List<ContaBancaria> listaContaBancariaList = new ArrayList<>();

        listaContaBancariaList.add(contaBancariaFoundOrigem.get());
        listaContaBancariaList.add(contaBancariaFound.get());

        List<ContaBancaria> contaBancariaLista = contaBancariaRepository.saveAll(listaContaBancariaList);

        return contaBancariaLista;
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
