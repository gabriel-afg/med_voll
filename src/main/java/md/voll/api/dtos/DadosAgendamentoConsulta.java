package md.voll.api.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import md.voll.api.enums.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade
) {
}
