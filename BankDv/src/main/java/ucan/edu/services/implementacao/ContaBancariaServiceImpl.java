package ucan.edu.services.implementacao;

import org.springframework.stereotype.Service;
import ucan.edu.entities.ContaBancaria;
import ucan.edu.repositories.ContaBancariaRepository;
import ucan.edu.services.ContaBancariaService;

import java.util.Random;
/**
 *
 * @author jussyleite
 */
@Service
public class ContaBancariaServiceImpl extends AbstractService<ContaBancaria, Integer> implements ContaBancariaService {

    private Integer numberAccount;

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaServiceImpl(ContaBancariaRepository contaBancariaRepository)
    {
        this.contaBancariaRepository = contaBancariaRepository;
    }

    public Integer creatAccountNumber()
    {
        Random random = new Random();
        // Gerar um número aleatório com 4 dígitos
        numberAccount = random.nextInt(9000) + 1000;
        // Exibir o número gerado
        System.out.println("Número Aleatório: " + numberAccount);
        return numberAccount;
    }

    public String createIban()
    {
        return " ";
    }

    public ContaBancaria createAccount(ContaBancaria conta)
    {
        try
        {
            Integer accoutNumber = this.creatAccountNumber();
            conta.setNumeroDeConta(accoutNumber);

            //conta.setSaldoList(0.0);
            conta.setStatus("");
            ContaBancaria contaCreated = contaBancariaRepository.save(conta);

            return contaCreated;
        } catch (Exception e)
        {
        }
        return null;
    }

}
