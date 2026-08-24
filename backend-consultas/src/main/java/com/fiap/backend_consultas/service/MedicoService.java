package com.fiap.backend_consultas.service;
import com.fiap.backend_consultas.exception.DadosInvalidosException;
import com.fiap.backend_consultas.exception.MedicoException;
import com.fiap.backend_consultas.exception.RecursoDuplicadoException;
import com.fiap.backend_consultas.model.Especialidade;
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

    public Medico getByCrm(String crm) {
        return repository.findByCrm(crm)
                .orElseThrow(() -> new MedicoException(MEDICO_NAO_ENCONTRADO));
    }

    public Medico update(Long id, Medico updatedMedico) {
        Medico savedMedico = getById(id);
        normalizar(updatedMedico);
        validarObrigatorios(updatedMedico);
        validarCrmUnico(updatedMedico.getCrm(), id);
        savedMedico.setNome(updatedMedico.getNome() != null ? updatedMedico.getNome() : savedMedico.getNome());
        savedMedico.setCrm(updatedMedico.getCrm() != null ? updatedMedico.getCrm() : savedMedico.getCrm());
        savedMedico.setEspecialidade(updatedMedico.getEspecialidade() != null ? updatedMedico.getEspecialidade() : savedMedico.getEspecialidade());
        savedMedico.setAtivo(updatedMedico.getAtivo() != null ? updatedMedico.getAtivo() : savedMedico.getAtivo());
        savedMedico.setValorConsulta(updatedMedico.getValorConsulta() != null ? updatedMedico.getValorConsulta() : savedMedico.getValorConsulta());
        return repository.save(savedMedico);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Medico> listarByEspecialidade(Long especialidadeId) {
        return repository.findByEspecialidadeId(especialidadeId);
    }

    private void normalizar(Medico medico) {
        if (medico.getNome() != null) {
            medico.setNome(medico.getNome().trim());
        }
        if (medico.getCrm() != null) {
            medico.setCrm(medico.getCrm().trim());
        }
    }

    private void validarObrigatorios(Medico medico) {
        if (medico.getNome() == null || medico.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome do médico é obrigatório.");
        }
        if (medico.getCrm() == null || medico.getCrm().isBlank()) {
            throw new DadosInvalidosException("CRM é obrigatório.");
        }
        if (medico.getEspecialidade() == null || medico.getEspecialidade().getId() == null) {
            throw new DadosInvalidosException("Especialidade é obrigatória.");
        }
    }

    private void validarCrmUnico(String crm, Long idAtual) {
        boolean existe = idAtual == null
                ? repository.existsByCrm(crm)
                : repository.existsByCrmAndIdNot(crm, idAtual);
        if (existe) {
            throw new RecursoDuplicadoException("CRM já cadastrado.");
        }
    }
}