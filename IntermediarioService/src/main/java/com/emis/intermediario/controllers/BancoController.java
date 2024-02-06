/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emis.intermediario.controllers;

import com.emis.intermediario.entities.Banco;
import com.emis.intermediario.https.utils.ResponseBody;
import com.emis.intermediario.services.implementacao.BancoServiceImpl;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jussyleitecode
 */
@RestController
@RequestMapping("/api/emis/banco/")
public class BancoController extends BaseController
{

    private final BancoServiceImpl bancoServiceImpl;

    public BancoController(BancoServiceImpl bancoServiceImpl)
    {
        this.bancoServiceImpl = bancoServiceImpl;
    }

    @PostMapping
    public ResponseEntity<ResponseBody> save(@RequestBody Banco banco)
    {
        Banco bancoCreated = this.bancoServiceImpl.saveBanco(banco);

        return bancoCreated != null
                ? this.created("Banco registrado com sucesso!", bancoCreated)
                : this.ok("Não foi possível registrar o banco!", bancoCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findBancoById(@PathVariable("id") Integer id)
    {
        Optional<Banco> banco = this.bancoServiceImpl.findBancoById(id);
        return banco != null
                ? this.ok("Encontrado com sucesso!", banco)
                : this.naoEncontrado("Nenhum banco encontrado", banco);

    }

}
