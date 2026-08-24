package com.fiap.backend_consultas.repository;
import com.fiap.backend_consultas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);

    List<Medico> findByEspecialidadeId(Long especialidadeId);

    boolean existsByCrm(String crm);

    boolean existsByCrmAndIdNot(String crm, Long id);
}