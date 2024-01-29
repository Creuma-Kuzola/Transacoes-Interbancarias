package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Saldo;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.SaldoServiceImpl;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/saldo")
public class SaldoController extends BaseController {

    @Autowired
    SaldoServiceImpl saldoServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllSaldos()
    {

        List<Saldo> lista = saldoServiceImpl.findAll();
        return this.ok("Saldos encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findSaldoByID(@PathVariable Integer id)
    {
        Optional<Saldo> consulta = this.saldoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Saldo encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Saldo não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createSaldo(@RequestBody Saldo saldo)
    {
        return this.created("Saldo adicionado com sucesso.", this.saldoServiceImpl.criar(saldo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteSaldo(@PathVariable("id") Integer id)
    {
        return this.ok("Saldo eliminado com sucesso.", this.saldoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateSaldo(@PathVariable("id") Integer id, @RequestBody Saldo saldo)
    {
        return this.ok("Saldo editado com sucesso.", (Saldo) saldoServiceImpl.editar(id, saldo));
    }
}
