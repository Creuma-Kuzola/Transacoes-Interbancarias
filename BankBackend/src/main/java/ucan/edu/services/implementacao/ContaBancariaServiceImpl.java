/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import ucan.edu.component.TransferenciaMessage;
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
    private final Integer BANKNUMBER =  4040;
    private final String ibanCode = "E " + BANKNUMBER;

    @Autowired
    private TransferenciaMessage transferenciaMessage;

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
        System.out.println("BANKNUMBER: " +BANKNUMBER);
        // Exibir o número gerado
        System.out.println("Número Aleatório: " + numberAccount);
        return numberAccount;
    }

    public  Integer  creatNiveRandomNumber()
    {
        Random random = new Random();
        // Gerar um número aleatório com 4 dígitos
        Integer number = random.nextInt(9000) + 100000000;
        System.out.println("nine: " +number);
        // Exibir o número gerado
        return number;
    }

    public String createIban()
    {
        String iban = " ";
        Integer accountCustomerNumber = numberAccount;
        Integer randomNumbers = this.creatNiveRandomNumber();
        System.out.println("BANKNUMBER: " +BANKNUMBER);
        iban = "E "+BANKNUMBER+""+accountCustomerNumber+""+randomNumbers;

        System.out.println(" iban: " +iban);
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

    public ContaBancaria depositeAmountOfMoney(ContaBancaria contaBancaria, BigDecimal quantia)
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

        BigDecimal novoSaldo = contaBancariaFound.get().getSaldoDisponivel();

        novoSaldo.add(quantia);

        contaBancaria.setSaldoContabilistico(novoSaldo);
        contaBancaria.setSaldoDisponivel(novoSaldo);

        ContaBancaria contaBancariaActualizada = contaBancariaRepository.save(contaBancaria);
        return contaBancariaActualizada;
    }

    // -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
    public Integer isSaldoPositiveToTransfer(Integer numberAccount, BigDecimal montante)
    {
        Optional<ContaBancaria> contaBancariaFoundOrigem = contaBancariaRepository
                .findContaBancariaByNumeroDeConta(numberAccount);

        System.out.println("Saldo Disponivel: " +contaBancariaFoundOrigem.get().getSaldoDisponivel());

        return  contaBancariaFoundOrigem.get().getSaldoDisponivel().compareTo(montante);
    }

    public ContaBancaria transferInterbancariaDebito(Integer numberAccount, BigDecimal montante)
    {
        Optional<ContaBancaria> contaBancariaFoundOrigem = contaBancariaRepository
                .findContaBancariaByNumeroDeConta(numberAccount);

        System.out.println("Saldo Disponivel: " +contaBancariaFoundOrigem.get().getSaldoDisponivel());

        //(contaBancariaFoundOrigem.get().getSaldoDisponivel() < montante
        // -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
        if (contaBancariaFoundOrigem.get().getSaldoDisponivel().compareTo( montante) == -1)
        {
            //throw new SaldoContaBancariaInferiorException();
            return null;
        }

        BigDecimal novoSaldoContaBancariaOrigem = contaBancariaFoundOrigem.get().getSaldoDisponivel();
        BigDecimal novoSaldoContaBancariaOrigemFinalResult = novoSaldoContaBancariaOrigem.subtract(montante);

        System.out.println(" novoSaldoContaBancariaOrigem:  transferInterbancariaDebito() " + novoSaldoContaBancariaOrigemFinalResult);

        contaBancariaFoundOrigem.get().setSaldoContabilistico(novoSaldoContaBancariaOrigemFinalResult);
        contaBancariaFoundOrigem.get().setSaldoDisponivel(novoSaldoContaBancariaOrigemFinalResult);
        contaBancariaRepository.save(contaBancariaFoundOrigem.get());

        return contaBancariaFoundOrigem.get();
    }
    //777
    public List<ContaBancaria> transferMoneyToAccountSameBank(ContaBancaria contaBancaria, String iban, BigDecimal montante)
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

        if (contaBancariaFoundOrigem.get().getSaldoDisponivel().compareTo( montante) == -1)
        {
            throw new SaldoContaBancariaInferiorException();
        }

        //get saldoDisponivel da conta do destinatario
        BigDecimal novoSaldoContaDestinatario = contaBancariaFound.get().getSaldoDisponivel();
        novoSaldoContaDestinatario.add(montante);

        //get saldoDisponivel da conta do destinatario
        BigDecimal novoSaldoContaBancariaOrigem = contaBancariaFoundOrigem.get().getSaldoDisponivel();

        BigDecimal novoSaldoContaBancariaOrigemFinalResult = novoSaldoContaBancariaOrigem.subtract(montante);
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

    public ContaBancaria findContaBancaraByIban(String iban)
    {
        return contaBancariaRepository.findByIban(iban);
    }

    public boolean isWakandaBankIban(String iban)
    {
        String codigoBanco = iban.substring(0, 5);
        System.out.println("CodigoBanco"+ codigoBanco+ "Codigo Banco Length: "+ codigoBanco.length());
        String idBancoValido = ibanCode;
        return codigoBanco.equals(idBancoValido);
    }

    public Integer isValidMontante(ContaBancaria contaBancaria, BigDecimal montante){

        BigDecimal saldoContabilistico = contaBancaria.getSaldoContabilistico(); ;
        // System.out.println("saldoContabilistico.compareTo(montante): "+ saldoContabilistico.compareTo(montante));

        return saldoContabilistico.compareTo( montante);
    }

    public boolean isValidTheSizeOfIban(String iban)
    {
        return iban.length() == 17;
    }

    public boolean existsIban(String iban)
    {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = contaBancariaRepository.findByIban(iban);

        return contaBancaria != null;
    }

    public ContaBancaria credito(String iban, BigDecimal montante){

        System.out.println( " iban: " +iban);

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = findContaBancaraByIban(iban);
        contaBancaria.setSaldoContabilistico(contaBancaria.getSaldoContabilistico().add(montante));
        contaBancaria.setSaldoDisponivel(contaBancaria.getSaldoDisponivel().add(montante));

        this.editar(contaBancaria.getPkContaBancaria(), contaBancaria);
        return contaBancaria;
    }


    public ContaBancaria debito(String iban, BigDecimal montante){

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = findContaBancaraByIban(iban);
        contaBancaria.setSaldoContabilistico(contaBancaria.getSaldoContabilistico().subtract(montante));
        contaBancaria.setSaldoDisponivel(contaBancaria.getSaldoDisponivel().subtract(montante));

        this.editar(contaBancaria.getPkContaBancaria(), contaBancaria);
        return contaBancaria;
    }
}