package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.ContaTipo;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.ContaTipoServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contaTipo")
public class ContaTipoController extends BaseController {

    @Autowired
    ContaTipoServiceImpl contaTipoServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllContaTipos()
    {

        List<ContaTipo> lista = contaTipoServiceImpl.findAll();
        return this.ok("Conta Tipo encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findContaTipoByID(@PathVariable Integer id)
    {
        Optional<ContaTipo> consulta = this.contaTipoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Conta Tipo encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Conta Tipo não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createContaTipo(@RequestBody ContaTipo contaTipo)
    {
        return this.created("Conta Tipo adicionada com sucesso.", this.contaTipoServiceImpl.criar(contaTipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteContaTipo(@PathVariable("id") Integer id)
    {
        return this.ok("Conta Tipo eliminada com sucesso.", this.contaTipoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateContaTipo(@PathVariable("id") Integer id, @RequestBody ContaTipo contaTipo)
    {
        return this.ok("Conta Tipo editado com sucesso.", (ContaTipo) contaTipoServiceImpl.editar(id, contaTipo));
    }
}
