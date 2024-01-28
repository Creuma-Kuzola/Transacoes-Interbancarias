/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.controllers;

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
@RequestMapping("/empresa")
public class EmpresaController extends BaseController
{

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
    
    @GetMapping("/nif/{nif}")
    public ResponseEntity<ResponseBody> findEmpresByNif(@PathVariable String nif)
    {
        Empresa result = this.empresaServiceImpl.findEmpresaByNif(nif);
        return  result != null
                ? this.ok(" empresa", result)
                :
                this.naoEncontrado("Nenhuma empresa", this);
        
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createEmpresa(@RequestBody Empresa empresa)
    {
        return this.created("Empresa adicionada com sucesso.", this.empresaServiceImpl.createEmpresa(empresa));
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
