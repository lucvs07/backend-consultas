package com.fiap.backend_consultas.repository;
import com.fiap.backend_consultas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}