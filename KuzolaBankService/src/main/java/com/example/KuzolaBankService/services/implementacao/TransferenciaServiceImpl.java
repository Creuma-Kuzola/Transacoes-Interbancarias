/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService{

   @Autowired
   public ContaBancariaServiceImpl contaBancariaService;

    public boolean isContaBancariaValid(String iban){

        if(contaBancariaService.isValidIban(iban))
        {
            if (contaBancariaService.isValidTheSizeOfIban(iban)) {
                return contaBancariaService.existsIban(iban);
            }
        }
        return false;
    }

    public TransferenciaDto convertTransferenciaIntoTransferenciaDto(Transferencia transferencia)
    {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setMontante(transferencia.getMontante());
        transferenciaDto.setDescricao(transferencia.getDescricao());
        transferenciaDto.setIbanDestinatario(transferencia.getIbanDestinatario());

        return transferenciaDto;
    }

}
