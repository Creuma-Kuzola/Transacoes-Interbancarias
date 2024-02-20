/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.repositories.BancoRepository;
import com.example.IntermediarioService.services.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.IntermediarioService.services.implementacao.AbstractService;

import java.util.List;

/**
 *
 * @author creuma
 */
@Service
public class BancoServiceImpl extends AbstractService<Banco, Integer>
implements BancoService{


    @Autowired
    BancoRepository bancoRepository;
    public List<Banco> findAllBancos()
    {
       return this.findAll();
    }

    public Banco findByPkBanco(Integer pkBanco){
        return bancoRepository.findByPkBanco(pkBanco);
    }
    
}
