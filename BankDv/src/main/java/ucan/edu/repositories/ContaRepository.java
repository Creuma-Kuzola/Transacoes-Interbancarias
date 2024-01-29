package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
