package md.voll.api.service;

import md.voll.api.dtos.DadosAgendamentoConsulta;
import md.voll.api.dtos.DadosCancelamentoConsulta;
import md.voll.api.dtos.DadosDetalhamentoConsulta;
import md.voll.api.infra.exceptions.ValidacaoException;
import md.voll.api.models.Consulta;
import md.voll.api.models.Medico;
import md.voll.api.repositories.ConsultaRepository;
import md.voll.api.repositories.MedicoRepository;
import md.voll.api.repositories.PacienteRepository;
import md.voll.api.validations.ValidadorAngedamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAngedamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("id do medico informado não existe");
        }

        validadores.forEach(v-> v.validar(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = escolherMedico(dados);

        if(medico == null) {
            throw new ValidacaoException("Nenhum médico disponivel nesse dia e com essa especialidade");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null, null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatoria quando médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public Consulta cancelar(DadosCancelamentoConsulta dados){
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Não existe nenhuma consulta com esse id");
        }

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        LocalDateTime dataCancelamento = LocalDateTime.now();

        if(dataCancelamento.plusDays(1).isAfter(consulta.getData())){
            throw new ValidacaoException("A consulta so pode ser cancelada no minimo 24 horas antes");
        }

        consulta.setCancelada(true);
        consulta.setMotivoCancelamento(dados.motivo());

        return consulta;
    }
}
