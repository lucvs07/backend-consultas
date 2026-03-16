package com.fiap.backend_consultas.controller;
import com.fiap.backend_consultas.model.Medico;
import com.fiap.backend_consultas.service.MedicoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medico> listar() {
        return service.listar();
    }

    @GetMapping("/by-id/{id}")
    public Medico getByiD(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Medico criar(@RequestBody Medico medico) {
        return service.salvar(medico);
    }

    @PutMapping("/update-by-id/{id}")
    public Medico updateById(@PathVariable Long id, @RequestBody Medico medico) {
        return service.update(id, medico);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}