package com.fiap.backend_consultas.controller;

import com.fiap.backend_consultas.model.Paciente;
import com.fiap.backend_consultas.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public Paciente criar(@RequestBody Paciente paciente) {
        return service.salvar(paciente);
    }

    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    @GetMapping("/by-id/{id}")
    public Paciente getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
