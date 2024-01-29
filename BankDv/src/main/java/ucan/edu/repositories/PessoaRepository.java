package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
}
