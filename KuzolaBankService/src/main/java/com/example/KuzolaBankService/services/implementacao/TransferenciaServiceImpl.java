/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.Transferencia;

import java.math.BigDecimal;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService{

   @Autowired
   public ContaBancariaServiceImpl contaBancariaService;

   //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.

    public boolean isTransferenciaInformationValid(String ibanDestino, BigDecimal montante, String ibanOrigem){

        if(contaBancariaService.isValidIban(ibanDestino))
        {
            if (contaBancariaService.isValidTheSizeOfIban(ibanDestino)) {
                if (contaBancariaService.existsIban(ibanDestino)){
                    if (ibanDestino != ibanOrigem) {

                        ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(ibanOrigem);

                        if (contaBancariaService.isValidMontante(contaBancaria, montante) != -1) {
                            return true;
                        }

                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
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
