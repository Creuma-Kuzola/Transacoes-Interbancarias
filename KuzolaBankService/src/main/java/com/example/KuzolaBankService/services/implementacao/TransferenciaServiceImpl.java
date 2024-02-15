/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.services.TransferenciaService;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService {

    @Autowired
    public ContaBancariaServiceImpl contaBancariaService;

    @Autowired
    UserInfo userInfo;


    //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.

    public boolean isTransferenciaInformationValid(String ibanDestino, BigDecimal montante, String ibanOrigem) {

        if (contaBancariaService.isValidIban(ibanDestino)) {
            if (contaBancariaService.isValidTheSizeOfIban(ibanDestino)) {
                if (contaBancariaService.existsIban(ibanDestino)) {
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

    public TransferenciaPOJO convertingIntoTransferenciaPOJO(Transferencia transferencia, String IbanOrigem)
    {
        TransferenciaPOJO transferenciaPOJO = new TransferenciaPOJO();
        transferenciaPOJO.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaPOJO.setDatahora(transferencia.getDatahora());
        transferenciaPOJO.setDescricao(transferencia.getDescricao());
        transferenciaPOJO.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaPOJO.setMontante(transferencia.getMontante());
        transferenciaPOJO.setEstadoTransferencia(transferencia.getEstadoTransferencia());
        transferenciaPOJO.setFkContaBancariaOrigem(transferencia.getFkContaBancariaOrigem().getPkContaBancaria());
        transferenciaPOJO.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        return transferenciaPOJO;
    }

    public void fillingTransactionFields(Transferencia transferencia){

        ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(userInfo.getUserInfo().get("iban"));

        transferencia.setFkContaBancariaOrigem(contaBancaria);
        transferencia.setDatahora(formattingDateTime());
        transferencia.setEstadoTransferencia("Realizado");
        transferencia.setTipoTransferencia("Transferencia Intrabancaria");
    }

    public LocalDateTime formattingDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }




}
