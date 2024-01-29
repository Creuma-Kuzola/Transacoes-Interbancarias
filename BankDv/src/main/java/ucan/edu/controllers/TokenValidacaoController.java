package ucan.edu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucan.edu.entities.TokenValidacao;
import ucan.edu.https.utils.ResponseBody;
import ucan.edu.services.implementacao.TokenValidacaoServiceImpl;

import java.util.List;
import java.util.Optional;

public class TokenValidacaoController extends BaseController {

    @Autowired
    TokenValidacaoServiceImpl tokenValidacaoServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllTokenValidacaos()
    {

        List<TokenValidacao> lista = tokenValidacaoServiceImpl.findAll();
        return this.ok("Os TokenValidacao encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findTokenValidacaoByID(@PathVariable Integer id)
    {
        Optional<TokenValidacao> consulta = this.tokenValidacaoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("TokenValidacao encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("TokenValidacao não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTokenValidacao(@RequestBody TokenValidacao tokenValidacao)
    {
        return this.created("TokenValidacao adicionado com sucesso.", this.tokenValidacaoServiceImpl.criar(tokenValidacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTokenValidacao(@PathVariable("id") Integer id)
    {
        return this.ok("TokenValidacao eliminado com sucesso.", this.tokenValidacaoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTokenValidacao(@PathVariable("id") Integer id, @RequestBody TokenValidacao tokenValidacao)
    {
        return this.ok("TokenValidacao editado com sucesso.", (TokenValidacao) tokenValidacaoServiceImpl.editar(id, tokenValidacao));
    }
}
