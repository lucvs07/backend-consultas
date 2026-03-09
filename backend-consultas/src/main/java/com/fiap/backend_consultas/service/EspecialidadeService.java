package com.fiap.backend_consultas.service;
import com.fiap.backend_consultas.exception.EspecialidadeException;
import com.fiap.backend_consultas.model.Especialidade;
import com.fiap.backend_consultas.repository.EspecialidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class EspecialidadeService {
    public static final String ESPECIALIDADE_NAO_ENCONTRADA = "Especialidade Não Encontrada";
    private final EspecialidadeRepository repository;
    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }
    public Especialidade salvar(Especialidade especialidade) {
        return repository.save(especialidade);
    }
    public List<Especialidade> listar() {
        return repository.findAll();
    }
    public Especialidade getById (Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EspecialidadeException(ESPECIALIDADE_NAO_ENCONTRADA));
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Especialidade update(Long id, Especialidade updatedEspecialidade){
        Especialidade especialidade = getById(id);
        processUpdate(updatedEspecialidade, especialidade);
        return repository.save(especialidade);
    }

    private static void processUpdate(Especialidade updatedEspecialidade, Especialidade especialidade) {
        especialidade.setNome(updatedEspecialidade.getNome() != null ? updatedEspecialidade.getNome() : especialidade.getNome());
        especialidade.setDescricao(updatedEspecialidade.getDescricao() != null ? updatedEspecialidade.getDescricao() : especialidade.getDescricao());
    }
}