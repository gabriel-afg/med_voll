package md.voll.api.dtos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarPaciente(
        @NotNull
        Long id ,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
