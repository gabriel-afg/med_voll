package md.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import md.voll.api.service.AgendaDeConsultas;
import md.voll.api.dtos.DadosAgendamentoConsulta;
import md.voll.api.dtos.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agendaDeConsultas.agendar(dados);

        return ResponseEntity.ok(dto);
    }
}
