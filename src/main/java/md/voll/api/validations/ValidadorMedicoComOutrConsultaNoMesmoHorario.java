package md.voll.api.validations;

import md.voll.api.dtos.DadosAgendamentoConsulta;
import md.voll.api.infra.exceptions.ValidacaoException;
import md.voll.api.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutrConsultaNoMesmoHorario implements ValidadorAngedamentoDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Medico ja possui uma consulta marcada nessa mesma data e horario");
        }
    }
}
