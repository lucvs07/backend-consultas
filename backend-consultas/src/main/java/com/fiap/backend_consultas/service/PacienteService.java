package com.fiap.backend_consultas.service;

import com.fiap.backend_consultas.model.Especialidade;
import com.fiap.backend_consultas.model.Paciente;
import com.fiap.backend_consultas.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente getById(Long id) {
        return repository.getReferenceById(id);
    }
}
