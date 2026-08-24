package com.fiap.backend_consultas.service;
import com.fiap.backend_consultas.exception.MedicoException;
import com.fiap.backend_consultas.model.Especialidade;
import com.fiap.backend_consultas.model.Medico;
import com.fiap.backend_consultas.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MedicoService {

    public static final String MEDICO_NAO_ENCONTRADO = "Médico não encontrado";
    private final MedicoRepository repository;
    private final EspecialidadeService especialidadeService;

    public MedicoService(MedicoRepository repository, EspecialidadeService especialidadeService) {
        this.repository = repository;
        this.especialidadeService = especialidadeService;
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

    public Medico getByCrm(String crm) {
        return repository.findByCrm(crm)
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

    public List<Medico> listarByEspecialidade(Long especialidadeId) {
        Especialidade especialidade = especialidadeService.getById(especialidadeId);
        return repository.findAllByEspecialidade(especialidade);
    }
}