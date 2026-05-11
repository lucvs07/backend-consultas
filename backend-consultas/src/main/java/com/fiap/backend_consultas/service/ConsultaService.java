package com.fiap.backend_consultas.service;

import java.util.List;

import com.fiap.backend_consultas.exception.ConsultaException;
import org.springframework.stereotype.Service;

import com.fiap.backend_consultas.model.Consulta;
import com.fiap.backend_consultas.model.Medico;
import com.fiap.backend_consultas.model.Paciente;
import com.fiap.backend_consultas.repository.ConsultaRepository;
import com.fiap.backend_consultas.repository.MedicoRepository;
import com.fiap.backend_consultas.repository.PacienteRepository;

@Service
public class ConsultaService {

    public static final String CONSULTA_NAO_ENCONTRADA = "Consulta não encontrada";
    private final ConsultaRepository consultaRepository;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository, MedicoService medicoService, PacienteService pacienteService) {
        this.consultaRepository = consultaRepository;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    public List<Consulta> listar() {
        return consultaRepository.findAll();
    }

    public Consulta getById(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ConsultaException(CONSULTA_NAO_ENCONTRADA));
    }

    public Consulta salvar(Consulta consulta) {
        Medico medico = medicoService.getById(consulta.getMedico().getId());
        Paciente paciente = pacienteService.getById(consulta.getPaciente().getId());

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        return consultaRepository.save(consulta);
    }

    public Consulta update(Long id, Consulta consultaAtualizada) {
        Consulta consultaExistente = getById(id);

        processUpdateConsulta(consultaAtualizada, consultaExistente);

        return consultaRepository.save(consultaExistente);
    }

    public void deleteById(Long id) {
        Consulta consulta = getById(id);
        consultaRepository.delete(consulta);
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        return consultaRepository.findByMedicoId(medicoId);
    }

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }

    private void processUpdateConsulta(Consulta consultaAtualizada, Consulta consultaExistente) {
        if (consultaAtualizada.getDataHora() != null) {
            consultaExistente.setDataHora(consultaAtualizada.getDataHora());
        }
        if (consultaAtualizada.getStatus() != null) {
            consultaExistente.setStatus(consultaAtualizada.getStatus());
        }
        if (consultaAtualizada.getValor() != null) {
            consultaExistente.setValor(consultaAtualizada.getValor());
        }
        consultaExistente.setObservacoes(consultaAtualizada.getObservacoes());

        if (consultaAtualizada.getMedico() != null && consultaAtualizada.getMedico().getId() != null) {
            Medico medico = medicoService.getById(consultaAtualizada.getMedico().getId());
            consultaExistente.setMedico(medico);
        }
        if (consultaAtualizada.getPaciente() != null && consultaAtualizada.getPaciente().getId() != null) {
            Paciente paciente = pacienteService.getById(consultaAtualizada.getPaciente().getId());
            consultaExistente.setPaciente(paciente);
        }
    }
}
