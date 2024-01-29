package ucan.edu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucan.edu.entities.Localizacao;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {
}
