package com.fiap.backend_consultas.service;

import com.fiap.backend_consultas.exception.PacienteException;
import com.fiap.backend_consultas.model.Especialidade;
import com.fiap.backend_consultas.model.Paciente;
import com.fiap.backend_consultas.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PacienteService {
    public static final String PACIENTE_NAO_ENCONTRADO = "Paciente Não Encontrado";
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
        return repository.findById(id)
                .orElseThrow(() -> new PacienteException(PACIENTE_NAO_ENCONTRADO));
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
