package com.fiap.backend_consultas.service;

import com.fiap.backend_consultas.exception.DadosInvalidosException;
import com.fiap.backend_consultas.exception.PacienteException;
import com.fiap.backend_consultas.exception.RecursoDuplicadoException;
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
        normalizar(paciente);
        validarObrigatorios(paciente);
        validarDuplicidade(paciente);
        if (paciente.getAtivo() == null) {
            paciente.setAtivo(true);
        }
        return repository.save(paciente);
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PacienteException(PACIENTE_NAO_ENCONTRADO));
    }

    public Paciente getByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new PacienteException(PACIENTE_NAO_ENCONTRADO));
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Paciente update(Long id, Paciente updatedPaciente){
        Paciente paciente = getById(id);
        normalizar(updatedPaciente);
        validarObrigatorios(updatedPaciente);
        validarDuplicidade(updatedPaciente);
        processUpdate(updatedPaciente, paciente);
        return repository.save(paciente);
    }

    private static void processUpdate(Paciente updatedPaciente, Paciente paciente) {
        paciente.setNome(updatedPaciente.getNome() != null ? updatedPaciente.getNome() : paciente.getNome());
        paciente.setCpf(updatedPaciente.getCpf() != null ? updatedPaciente.getCpf() : paciente.getCpf());
        paciente.setEmail(updatedPaciente.getEmail() != null ? updatedPaciente.getEmail() : paciente.getEmail());
        paciente.setTelefone(updatedPaciente.getTelefone() != null ? updatedPaciente.getTelefone() : paciente.getTelefone());
        paciente.setDataNascimento(updatedPaciente.getDataNascimento() != null ? updatedPaciente.getDataNascimento() : paciente.getDataNascimento());
        paciente.setAtivo(updatedPaciente.getAtivo() != null ? updatedPaciente.getAtivo() : paciente.getAtivo());
    }

    private void normalizar(Paciente paciente) {
        if (paciente.getNome() != null) {
            paciente.setNome(paciente.getNome().trim());
        }
        if (paciente.getCpf() != null) {
            paciente.setCpf(paciente.getCpf().replaceAll("\\D", ""));
        }
        if (paciente.getEmail() != null) {
            paciente.setEmail(paciente.getEmail().trim());
        }
        if (paciente.getTelefone() != null && paciente.getTelefone().isBlank()) {
            paciente.setTelefone(null);
        }
    }

    private void validarObrigatorios(Paciente paciente) {
        if (paciente.getNome() == null || paciente.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do paciente é obrigatório.");
        }
        if (paciente.getCpf() == null || paciente.getCpf().length() != 11) {
            throw new DadosInvalidosException("CPF deve conter 11 dígitos.");
        }
        if (paciente.getEmail() == null || paciente.getEmail().isBlank() || !paciente.getEmail().contains("@")) {
            throw new DadosInvalidosException("E-mail válido é obrigatório.");
        }
    }

    private void validarDuplicidade(Paciente paciente) {
        if (repository.existsByCpf(paciente.getCpf())) {
            throw new RecursoDuplicadoException("CPF já cadastrado.");
        }
        if (repository.existsByEmailIgnoreCase(paciente.getEmail())) {
            throw new RecursoDuplicadoException("E-mail já cadastrado.");
        }
    }
}
