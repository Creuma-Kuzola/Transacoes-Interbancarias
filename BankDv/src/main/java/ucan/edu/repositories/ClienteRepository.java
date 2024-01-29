package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
