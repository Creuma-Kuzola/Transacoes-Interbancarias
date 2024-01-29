package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Empresa;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.EmpresaServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController extends BaseController {

    @Autowired
    EmpresaServiceImpl empresaServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllEmpresa()
    {

        List<Empresa> lista = empresaServiceImpl.findAll();
        return this.ok("Empresa encontrada com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findEmpresaByID(@PathVariable Integer id)
    {
        Optional<Empresa> consulta = this.empresaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Empresa encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Empresa não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createEmpresa(@RequestBody Empresa empresa)
    {
        return this.created("Empresa adicionada com sucesso.", this.empresaServiceImpl.criar(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteEmpresa(@PathVariable("id") Integer id)
    {
        return this.ok("Empresa eliminada com sucesso.", this.empresaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateEmpresa(@PathVariable("id") Integer id, @RequestBody Empresa empresa)
    {
        return this.ok("Empresa editada com sucesso.", (Empresa) empresaServiceImpl.editar(id, empresa));
    }
}
