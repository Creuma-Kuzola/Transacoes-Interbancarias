package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
