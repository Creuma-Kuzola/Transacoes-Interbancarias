package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Cliente;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.ClienteServiceImpl;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cliente")
public class ClienteController extends BaseController{

    @Autowired
    ClienteServiceImpl clienteServiceImpl;


    public void criarCliente()
    {

    }

    @GetMapping
    public ResponseEntity<ResponseBody> findAllClientes()
    {

        List<Cliente> lista = clienteServiceImpl.findAll();
        return this.ok("Cliente encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findClienteByID(@PathVariable Integer id)
    {
        Optional<Cliente> consulta = this.clienteServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Cliente encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Cliente não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createCliente(@RequestBody Cliente cliente)
    {
        return this.created("Cliente adicionado com sucesso.", this.clienteServiceImpl.criar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteCliente(@PathVariable("id") Integer id)
    {
        return this.ok("Cliente eliminado com sucesso.", this.clienteServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente)
    {
        return this.ok("Cliente editado com sucesso.", (Cliente) clienteServiceImpl.editar(id, cliente));
    }
}
