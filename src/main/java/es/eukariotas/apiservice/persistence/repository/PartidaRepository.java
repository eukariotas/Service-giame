package es.eukariotas.apiservice.persistence.repository;

import es.eukariotas.apiservice.persistence.entity.Party;
import es.eukariotas.apiservice.persistence.entity.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<Party, Long> {
    List<Party> findAll();
    Optional<Party> findById(Long aLong);
    boolean existsById(Long id);

}