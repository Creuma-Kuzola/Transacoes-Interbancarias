/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import org.springframework.stereotype.Service;
import ucan.edu.exceptions.ContaUsernameExistsException;
import ucan.edu.repository.ContaRepository;

/**
 *
 * @author jussyleitecode
 */
@Service
public class ContaServiceImpl extends AbstractService<Conta, Integer> implements ContaService
{

    @Autowired
    ContaRepository contaRepository;

    public Conta createAccount(Conta conta)
    {
        Conta contaExist = null; // = contaRepository.findContaByUsername(conta.getUsername());

        if (contaExist != null)
        {
            throw new ContaUsernameExistsException();
        }
        return this.criar(conta);
    }

}
