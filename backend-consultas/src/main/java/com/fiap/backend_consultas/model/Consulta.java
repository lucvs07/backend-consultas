package com.fiap.backend_consultas.model;

import java.time.LocalDateTime;

import com.fiap.backend_consultas.enums.StatusConsulta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private StatusConsulta status;

    private Double valor;

    private String observacoes;

    public Consulta() {}

    public Consulta(Medico medico, Paciente paciente, LocalDateTime dataHora,
                    StatusConsulta status, Double valor, String observacoes) {
        this.medico = medico;
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.status = status;
        this.valor = valor;
        this.observacoes = observacoes;
    }

    public Long getId() { return id; }
    public Medico getMedico() { return medico; }
    public Paciente getPaciente() { return paciente; }
    public LocalDateTime getDataHora() { return dataHora; }
    public StatusConsulta getStatus() { return status; }
    public Double getValor() { return valor; }
    public String getObservacoes() { return observacoes; }

    public void setId(Long id) { this.id = id; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setStatus(StatusConsulta status) { this.status = status; }
    public void setValor(Double valor) { this.valor = valor; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}