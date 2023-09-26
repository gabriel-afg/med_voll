package md.voll.api.dtos;

import md.voll.api.enums.Especialidade;

public record DadosCadastroMedico(
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco
) {
}
