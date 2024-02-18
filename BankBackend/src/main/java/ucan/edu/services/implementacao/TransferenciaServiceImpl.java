/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import ucan.edu.config.component.UserInfo;
import ucan.edu.entities.*;
import ucan.edu.repository.TransferenciaRepository;
import ucan.edu.services.*;
import org.springframework.stereotype.Service;
import ucan.edu.utils.pojos.TransferenciaPOJO;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService{

    @Autowired
    public ContaBancariaServiceImpl contaBancariaService;

    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaRepository transferenciaRepository;

    public  Integer isValidInformationIban(String iban){

        if(contaBancariaService.isValidTheSizeOfIban(iban)){
            if (contaBancariaService.isWakandaBankIban(iban)){
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

    public void fillingTransactionFields(Transferencia transferencia){

        ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(userInfo.getUserInfo().get("iban"));

        transferencia.setFkContaBancariaOrigem(contaBancaria.getPkContaBancaria());
        transferencia.setDatahora(new Date());
        transferencia.setEstadoTransferencia("REALIZADO");
        transferencia.setTipoTransferencia("Transferencia Intrabancaria");
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
        transferenciaPOJO.setFkContaBancariaOrigem(transferencia.getFkContaBancariaOrigem());
        transferenciaPOJO.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        return transferenciaPOJO;
    }

    public  Transferencia criaTransferencia(Transferencia transferencia)
    {
        return  this.criar(transferencia);
    }

    public void transferenciaIntrabancaria(){


    }
    
}
