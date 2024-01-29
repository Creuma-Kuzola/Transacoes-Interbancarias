/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.services.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import org.springframework.stereotype.Service;
import ucan.edu.exceptions.NifExistException;
import ucan.edu.repository.EmpresaRepository;

/**
 *
 * @author jussyleitecode
 */
@Service
public class EmpresaServiceImpl extends AbstractService<Empresa, Integer> implements EmpresaService
{

    @Autowired
    EmpresaRepository empresaRepository;

    public Empresa findEmpresaByNif(String nif)
    {
        Empresa result = empresaRepository.findEmpresaByNif(nif);

        if (result != null)
        {
            throw new NifExistException();
        }

        return result;
    }

    public Empresa createEmpresa(Empresa empresa)
    {
        Empresa result = empresaRepository.findEmpresaByNif(empresa.getNif());

        if (result != null)
        {
            throw new NifExistException();
        }

        return this.criar(empresa);
    }

}
