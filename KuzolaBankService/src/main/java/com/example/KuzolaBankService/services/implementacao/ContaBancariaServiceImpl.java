/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.enums.DetalhesBanco;
import com.example.KuzolaBankService.repositories.ContaBancariaRepository;
import com.example.KuzolaBankService.services.ContaBancariaService;
import com.example.KuzolaBankService.utils.ContaBancariaDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Cliente;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author creuma
 */
@Service
public class ContaBancariaServiceImpl extends AbstractService<ContaBancaria, Integer>
        implements ContaBancariaService
{
    @Autowired
    ContaBancariaRepository contaBancariaRepository;
    private HashSet<BigInteger> listaNumerosDeConta = new HashSet<>();
    private DetalhesBanco identificadorDoBanco = DetalhesBanco.IDENTIFICADOR_DO_BANCO;
    ContaBancariaDetails contaBancariaDetails = new ContaBancariaDetails();

    public void getAllNumerosDeConta(HashSet<BigInteger> listaNumerosDeConta)
    {
        listaNumerosDeConta = contaBancariaRepository.findAllNumeroConta();
    }

    @Override
    public ContaBancaria creatingContaBancariaByFkCliente(Cliente cliente)
    {

        BigInteger numeroConta = BigInteger.ZERO;
        Date data = new Date();
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setFkCliente(cliente);

        numeroConta = contaBancariaDetails.createNumeroDeConta(listaNumerosDeConta);

        contaBancaria.setNumeroDeConta(numeroConta);
        contaBancaria.setIban(contaBancariaDetails.createIban(numeroConta));
        contaBancaria.setStatus("Activo");
        contaBancaria.setDataCriacao(data);

        return contaBancaria;
    }

    public boolean existsIban(String iban)
    {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = contaBancariaRepository.findByIban(iban);

        return contaBancaria != null;
    }

    public ContaBancaria isAccountStatus(BigInteger numberAccount,String status)
    {
        return contaBancariaRepository.findContaBancariaByStatus(numberAccount,status);
    }

    public ContaBancaria isAccountStatus(String iban, String status)
    {
        return contaBancariaRepository.findContaBancariaByStatus(iban,status);
    }

    public ContaBancaria findContaBancaraByIban(String iban)
    {

        return contaBancariaRepository.findByIban(iban);
    }

    public boolean isValidIban(String iban)
    {

        String codigoBanco = iban.substring(0, 4);
        String idBancoValido = String.valueOf(identificadorDoBanco.getIdentificadorDoBanco());
        return codigoBanco.equals(idBancoValido);
    }

    // -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
    public Integer isValidMontante(ContaBancaria contaBancaria, BigDecimal montante){

        BigDecimal saldoContabilistico = contaBancaria.getSaldoContabilistico(); ;
        System.out.println("saldoContabilistico.compareTo(montante): "+ saldoContabilistico.compareTo(montante));
        return saldoContabilistico.compareTo(montante);
    }

    public boolean isValidTheSizeOfIban(String iban)
    {
        return iban.length() == 17;
    }

    public void debitoSaldoContabilistico (String iban, BigDecimal montante ){

        ContaBancaria contaBancaria =  this.findContaBancaraByIban(iban);
        BigDecimal saldoContabilistico = contaBancaria.getSaldoContabilistico();
        contaBancaria.setSaldoContabilistico(saldoContabilistico.subtract(montante));
    }

    public void debitoSaldoDisponivel (String iban, BigDecimal montante ){

        ContaBancaria contaBancaria =  this.findContaBancaraByIban(iban);
        BigDecimal saldoDisponivel = contaBancaria.getSaldoDisponivel();
        contaBancaria.setSaldoDisponivel(saldoDisponivel.subtract(montante));
    }

    public void creditoSaldoContaBancaria (String iban, BigDecimal montante ){

        ContaBancaria contaBancaria =  this.findContaBancaraByIban(iban);
        BigDecimal saldoDisponivel = contaBancaria.getSaldoDisponivel();
        BigDecimal saldoContabilistico = contaBancaria.getSaldoContabilistico();

        contaBancaria.setSaldoDisponivel(saldoDisponivel.add(montante));
        contaBancaria.setSaldoContabilistico(saldoContabilistico.add(montante));

    }


}
