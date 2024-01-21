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
 * @author creuma
 */
@RestController
@RequestMapping("/contaTipo")
public class ContaTipoController extends BaseController{
    
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
