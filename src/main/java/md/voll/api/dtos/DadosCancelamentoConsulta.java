package md.voll.api.dtos;

import jakarta.validation.constraints.NotNull;
import md.voll.api.enums.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
        ) {}
