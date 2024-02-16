/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.controllers;

import ucan.edu.dtos.SaldoContaDTO;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import ucan.edu.services.implementacao.*;
import ucan.edu.https.utils.ResponseBody;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jussyleitecode
 */
@RestController
@RequestMapping("/ContaBancaria")
public class ContaBancariaController extends BaseController
{
    @Autowired
    ContaBancariaServiceImpl contaBancariServiceImpl;
    @GetMapping
    public ResponseEntity<ResponseBody> findAllContaBancaria()
    {
        List<ContaBancaria> lista = contaBancariServiceImpl.findAll();
        return this.ok("Contas Bancarias encontradas com sucesso!", lista);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findContaBancariaByID(@PathVariable Integer id)
    {
        Optional<ContaBancaria> consulta = this.contaBancariServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Conta Bancaria encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Conta Bancaria não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createContaBancaria(@RequestBody ContaBancaria contaBancaria)
    {
        return this.created("Conta Bancaria adicionada com sucesso.", this.contaBancariServiceImpl.criar(contaBancaria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteContaBancaria(@PathVariable("id") Integer id)
    {
        return this.ok("Conta Bancaria eliminada com sucesso.", this.contaBancariServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateContaBancaria(@PathVariable("id") Integer id, @RequestBody ContaBancaria contaBancaria)
    {
        return this.ok("Conta Bancaria editada com sucesso.", (ContaBancaria) contaBancariServiceImpl.editar(id, contaBancaria));
    }

    @PutMapping
    public ResponseEntity<ResponseBody> activarAccount(@RequestBody ContaBancaria contaBancaria)
    {
        boolean isContaAcativated = contaBancariServiceImpl.activateAccount(contaBancaria);
        return isContaAcativated
                ? this.ok("Conta Bancaria activada com sucesso!", this)
                : this.naoEncontrado("Erro ao activar a conta bancaria!", this);
    }

    @PutMapping("/deposite/{quantidade}")
    public ResponseEntity<ResponseBody> depositeAmountOfMoney(@RequestBody ContaBancaria contaBancaria,
            @PathVariable("quantidade") Integer quantidade)
    {
        ContaBancaria conta = contaBancariServiceImpl.depositeAmountOfMoney(contaBancaria, quantidade);
        return this.ok("Deposito feito com sucesso !", conta);
    }

    @PatchMapping("/transfer/{montante}/{iban}")
    public ResponseEntity<ResponseBody> transferMoney(@RequestBody ContaBancaria contaBancaria,
            @PathVariable("iban") String iban,
            @PathVariable("montante") Integer montante)
    {
        List<ContaBancaria> contaBancarias = contaBancariServiceImpl.transferMoneyToAccountSameBank(contaBancaria, iban, montante);
        return this.ok("Transferencia feita com sucesso!", contaBancarias);
    }

    @GetMapping("/saldo/{numeberAccount}")
    public ResponseEntity<ResponseBody> findSaldoThrouhBanco(@PathVariable("numeberAccount") Integer numeberAccount)
    {
        SaldoContaDTO saldoContaDTO = contaBancariServiceImpl.findSaldoContaByNumeroDeConta(numeberAccount);
        return  saldoContaDTO == null
                ? this.naoEncontrado("Numero de conta inexsistente",this)
                : this.ok("Saldo da conta: " +numeberAccount+ " ", saldoContaDTO);
    }

    @GetMapping("/saldoEmis/{numeberAccount}")
    public ResponseEntity<ResponseBody> findSaldoThrouhEmisBanco(@PathVariable("numeberAccount") Integer numeberAccount)
    {
        SaldoContaDTO saldoContaDTO = contaBancariServiceImpl.findSaldoContaByNumeroDeConta(numeberAccount);
        return  saldoContaDTO == null
                ? this.naoEncontrado("Numero de conta inexsistente",this)
                : this.ok("Saldo da conta: " +numeberAccount+ " ", saldoContaDTO);
    }

}
