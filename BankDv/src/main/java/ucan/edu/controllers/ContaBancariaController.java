package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.ContaBancariaServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ContaBancaria")
public class ContaBancariaController extends BaseController {

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

}
