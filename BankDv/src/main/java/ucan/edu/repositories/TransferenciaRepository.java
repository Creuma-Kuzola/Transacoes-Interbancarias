package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {
}
