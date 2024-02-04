/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emis.intermediario.services.implementacao;

import com.emis.intermediario.entities.Banco;
import com.emis.intermediario.repositories.BancoRepository;
import com.emis.intermediario.services.BancoService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author jussyleitecode
 */
@Service
public class BancoServiceImpl implements BancoService<Banco, Integer>
{

    private final BancoRepository bancoRepository;

    public BancoServiceImpl(BancoRepository bancoRepository)
    {
        this.bancoRepository = bancoRepository;
    }

    public Banco saveBanco(Banco banco)
    {
        banco.setDataCriacaoBanco(new Date());
        return this.bancoRepository.save(banco);
    }

    public Optional<Banco> findBancoById(Integer banco)
    {
        return bancoRepository.findById(banco);
    }

    public List<Banco> findAllBancos()
    {
        return this.bancoRepository.findAll();
    }

    public Banco findBancoComMaxTransferencias()
    {
        return null;
    }
}
