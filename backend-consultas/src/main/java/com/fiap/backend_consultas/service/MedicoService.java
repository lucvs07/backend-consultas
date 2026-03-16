package com.fiap.backend_consultas.service;
import com.fiap.backend_consultas.exception.MedicoException;
import com.fiap.backend_consultas.model.Medico;
import com.fiap.backend_consultas.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MedicoService {

    public static final String MEDICO_NAO_ENCONTRADO = "Médico não encontrado";
    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public Medico salvar(Medico medico) {
        return repository.save(medico);
    }

    public List<Medico> listar() {
        return repository.findAll();
    }
    public Medico getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicoException(MEDICO_NAO_ENCONTRADO));
    }

    public Medico update(Long id, Medico updatedMedico) {
        Medico savedMedico = getById(id);
        savedMedico.setNome(updatedMedico.getNome() != null ? updatedMedico.getNome() : savedMedico.getNome());
        savedMedico.setCrm(updatedMedico.getCrm() != null ? updatedMedico.getCrm() : savedMedico.getCrm());
        savedMedico.setEspecialidade(updatedMedico.getEspecialidade() != null ? updatedMedico.getEspecialidade() : savedMedico.getEspecialidade());
        savedMedico.setAtivo(updatedMedico.getAtivo() != null ? updatedMedico.getAtivo() : savedMedico.getAtivo());
        return repository.save(savedMedico);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}