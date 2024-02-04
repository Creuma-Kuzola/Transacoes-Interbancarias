/*
 * 
 * 
 */
package com.emis.intermediario.services.implementacao;

import com.emis.intermediario.entities.HistoricoTransferencia;
import com.emis.intermediario.repositories.HistoricoTransferenciaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author jussyleitecode
 */
@Service
public class HistoricoTransferenciaServiceImpl
{

    private final HistoricoTransferenciaRepository historicoTransferenciaRepository;

    public HistoricoTransferenciaServiceImpl(HistoricoTransferenciaRepository historicoTransferenciaRepository)
    {
        this.historicoTransferenciaRepository = historicoTransferenciaRepository;
    }

    public HistoricoTransferencia fazerTransferencia(HistoricoTransferencia h)
    {
        HistoricoTransferencia modelCreated = historicoTransferenciaRepository.save(h);
        return modelCreated;
    }

    public List<HistoricoTransferencia> findAllTransferencias()
    {
        return this.historicoTransferenciaRepository.findAll();
    }

    public HistoricoTransferencia findTransferenciaByBanco(Integer banco)
    {
        HistoricoTransferencia historico = this.historicoTransferenciaRepository.findHistoricoTransferenciaByBanco(banco);
        return historico;
    }
}
