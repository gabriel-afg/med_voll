package md.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import md.voll.api.dtos.DadosAtualizarMedico;
import md.voll.api.dtos.DadosCadastroMedico;
import md.voll.api.dtos.DadosDetalhamentoMedico;
import md.voll.api.dtos.DadosListagemMedico;
import md.voll.api.models.Medico;
import md.voll.api.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable){
        Page<DadosListagemMedico> obj = repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarPorId(@PathVariable Long id){
        var obj = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(obj));
    }

    @PutMapping()
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizarMedico dados){
        var obj = repository.getReferenceById(dados.id());
        obj.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(obj));
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
