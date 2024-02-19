/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.services.BancoService;
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


    public List<Banco> findAllBancos()
    {
       return this.findAll();
    }
    
}
