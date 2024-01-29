package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Saldo;

public interface SaldoRepository extends JpaRepository<Saldo, Integer> {
}
