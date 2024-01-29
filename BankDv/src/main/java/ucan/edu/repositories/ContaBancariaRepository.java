package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.ContaBancaria;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {
}
