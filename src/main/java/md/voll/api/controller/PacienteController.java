package md.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import md.voll.api.dtos.DadosAtualizarPaciente;
import md.voll.api.dtos.DadosCadastroPaciente;
import md.voll.api.dtos.DadosDetalhamentoPaciente;
import md.voll.api.dtos.DadosListagemPaciente;
import md.voll.api.models.Paciente;
import md.voll.api.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var Paciente = new Paciente(dados);
        repository.save(Paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(Paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(Paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable){
        Page<DadosListagemPaciente> obj = repository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> listarPorId(@PathVariable Long id){
        var obj = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(obj));
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizarPaciente dados){
        var obj = repository.getReferenceById(dados.id());
        obj.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(obj));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        var obj = repository.getReferenceById(id);
        obj.inativar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> ativar(@PathVariable Long id){
        var obj = repository.getReferenceById(id);
        obj.ativar();
        return ResponseEntity.noContent().build();
    }
}
