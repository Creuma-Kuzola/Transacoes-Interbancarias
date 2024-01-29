package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Conta;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.ContaServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conta")
public class ContaController extends BaseController {

    @Autowired
    ContaServiceImpl contaServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllContas()
    {

        List<Conta> lista = contaServiceImpl.findAll();
        return this.ok("Contas encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findContaByID(@PathVariable Integer id)
    {
        Optional<Conta> consulta = this.contaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Conta encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Conta não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createConta(@RequestBody Conta conta)
    {
        return this.created("Conta adicionada com sucesso.", this.contaServiceImpl.criar(conta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteConta(@PathVariable("id") Integer id)
    {
        return this.ok("Conta eliminada com sucesso.", this.contaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateConta(@PathVariable("id") Integer id, @RequestBody Conta conta)
    {
        return this.ok("Conta editada com sucesso.", (Conta) contaServiceImpl.editar(id, conta));
    }
}
