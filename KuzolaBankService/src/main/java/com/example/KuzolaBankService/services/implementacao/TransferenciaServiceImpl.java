/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.config.component.TransferenciaComponent;
import com.example.KuzolaBankService.config.component.TransferenciaMessage;
import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.enums.DetalhesBanco;
import com.example.KuzolaBankService.repositories.ContaBancariaRepository;
import com.example.KuzolaBankService.repositories.TransferenciaRepository;
import com.example.KuzolaBankService.services.TransferenciaService;
import com.example.KuzolaBankService.utils.pojos.TransferenciaCustomPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.Transferencia;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    ContaBancariaRepository contaBancariaRepository;

    @Autowired
    TransferenciaComponent transferenciaComponent;

    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaRepository transferenciaRepository;

    //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.

    public Integer getCondigoTransferencia()
    {
        Random random = new Random();
        // Gerar um número aleatório com 4 dígitos
        Integer codigo = random.nextInt(9000) + 100000;
        //System.out.println("BANKNUMBER: " +BANKNUMBER);
        // Exibir o número gerado
        System.out.println("Número Aleatório: " + codigo);
        return codigo;
    }

    public  Integer isValidInformationIban(String iban){

        if(contaBancariaService.isValidTheSizeOfIban(iban)){
            if (contaBancariaService.isKuzolaBankIban(iban)){
                return 1;
            }
            return 2;
        }
        return 3;
    }

    public boolean isTransferenciaInformationValid(String ibanDestino, BigDecimal montante, String ibanOrigem) {

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

    public Transferencia buildTransferencia(TransferenciaComponent transferenciaComponent) throws ParseException
    {
        Transferencia transferencia = new Transferencia();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(transferenciaComponent.getTransferenciaResponse().get("datahora"), formatter);

        BigInteger numeroDeConta = new BigInteger(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem"));
        ContaBancaria contaBancaria = contaBancariaRepository.findByNumeroDeConta(numeroDeConta);

        transferencia.setDescricao(transferenciaComponent.getTransferenciaResponse().get("descricao"));
        transferencia.setMontante(new BigDecimal(transferenciaComponent.getTransferenciaResponse().get("montante")));
        transferencia.setDatahora(localDateTime);
        transferencia.setIbanDestinatario(transferenciaComponent.getTransferenciaResponse().get("ibanDestinatario"));
        transferencia.setFkContaBancariaOrigem(contaBancaria);

        transferencia.setTipoTransferencia(transferenciaComponent.getTransferenciaResponse().get("tipoTransferencia"));
        transferencia.setEstadoTransferencia("REALIZADO");
        transferencia.setCodigoTransferencia(transferenciaComponent.getTransferenciaResponse().get("codigoTransferencia"));
        return transferencia;
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

        System.out.println("tranferencias:---::  "+transferencia);
    }

    public TransferenciaCustomPOJO convertToTransferenciaCustomPOJO(Transferencia transferencia) {

        TransferenciaCustomPOJO transferenciaCustomPOJO = new TransferenciaCustomPOJO();

        transferenciaCustomPOJO.setDatahora(new Date());
        transferenciaCustomPOJO.setFkContaBancariaOrigem(new BigInteger(userInfo.getUserInfo().get("accountNumber")));
        transferenciaCustomPOJO.setDescricao(transferencia.getDescricao());
        transferenciaCustomPOJO.setMontante(transferencia.getMontante());
        transferenciaCustomPOJO.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaCustomPOJO.setTipoTransferencia("INTERBANCARIA");
        transferenciaCustomPOJO.setEstadoTransferencia("EM PROCESSAMENTO");
        transferenciaCustomPOJO.setCodigoTransferencia(""+this.getCondigoTransferencia());
        transferenciaCustomPOJO.setBancoUdentifier(1003);

        System.out.println(" Descricao: "+transferenciaCustomPOJO.getDescricao());

        System.out.println("transferenciaCustomPOJO: " +transferenciaCustomPOJO.toString());

        //System.out.println("data: " + transferenciaCustomPOJO.getDatahora());

        return transferenciaCustomPOJO;
    }


    public void saveTransferComponent(TransferenciaCustomPOJO transferencia)  {
        Map<String, String> transferenciaItems = new HashMap<>();

        transferenciaItems.put("descricao", transferencia.getDescricao());
        transferenciaItems.put("montante", transferencia.getMontante().toString());
        transferenciaItems.put("ibanDestinatario", transferencia.getIbanDestinatario());
        transferenciaItems.put("datahora","" +
                "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferencia.getDatahora()));
        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("tipoTransferencia", transferencia.getTipoTransferencia());
        transferenciaItems.put("estadoTransferencia", transferencia.getEstadoTransferencia());
        transferenciaItems.put("codigoTransferencia", transferencia.getCodigoTransferencia());
        transferenciaComponent.setTransferenciaResponse(transferenciaItems);
        System.out.println("data: " + transferenciaComponent.getTransferenciaResponse().get("datahora"));
    }

    public LocalDateTime formattingDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }

    public Transferencia criaTransferencia(Transferencia transferencia)
    {
        return this.criar(transferencia);
    }
    public List<Transferencia> findAllDesc(){

        return transferenciaRepository.findAllDesc();
    }

    public List<Transferencia> findAllTransacoesDebitadas(Integer fkContaBancariaOrigem){
        return  transferenciaRepository.findAllTransacoesDebitadas(fkContaBancariaOrigem);
    }

    public List<Transferencia> findAllTransacoesCreditadas(String ibanDestino){

        return transferenciaRepository.findAllTransacoesCreditadas(ibanDestino);
    }

}
