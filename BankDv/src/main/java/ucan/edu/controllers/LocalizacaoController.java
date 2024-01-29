package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.Localizacao;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.LocalizacaoServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/localizacao")
public class LocalizacaoController extends BaseController {

    @Autowired
    LocalizacaoServiceImpl localizacaoServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllLocalizacao()
    {

        List<Localizacao> lista = localizacaoServiceImpl.findAll();
        return this.ok("Localizacões encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findLocalizacaoByID(@PathVariable Integer id)
    {
        Optional<Localizacao> consulta = this.localizacaoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Localizacao encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Localizacao não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createLocalizacao(@RequestBody Localizacao localizacao)
    {
        return this.created("Localizacao adicionada com sucesso.", this.localizacaoServiceImpl.criar(localizacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteLocalizacao(@PathVariable("id") Integer id)
    {
        return this.ok("Localizacao eliminada com sucesso.", this.localizacaoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateLocalizacao(@PathVariable("id") Integer id, @RequestBody Localizacao localizacao)
    {
        return this.ok("Localizacao editada com sucesso.", (Localizacao) localizacaoServiceImpl.editar(id, localizacao));
    }

}
