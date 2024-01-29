package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Pessoa;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.PessoaServiceImpl;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/pessoa")
public class PessoaController extends BaseController{

    @Autowired
    PessoaServiceImpl pessoaServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllPessoa()
    {

        List<Pessoa> lista = pessoaServiceImpl.findAll();
        return this.ok("Pessoas encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findPessoaByID(@PathVariable Integer id)
    {
        Optional<Pessoa> consulta = this.pessoaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Pessoa encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Pessoa não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPessoa(@RequestBody Pessoa pessoa)
    {
        return this.created("Pessoa adicionada com sucesso.", this.pessoaServiceImpl.criar(pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deletePessoa(@PathVariable("id") Integer id)
    {
        return this.ok("Pessoa eliminada com sucesso.", this.pessoaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updatePessoa(@PathVariable("id") Integer id, @RequestBody Pessoa pessoa)
    {
        return this.ok("Pessoa editada com sucesso.", (Pessoa) pessoaServiceImpl.editar(id, pessoa));
    }
}
