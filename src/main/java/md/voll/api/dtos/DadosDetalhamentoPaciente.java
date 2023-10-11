package md.voll.api.dtos;

import md.voll.api.enums.Especialidade;
import md.voll.api.models.Endereco;
import md.voll.api.models.Paciente;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Endereco endereco
) {
    public DadosDetalhamentoPaciente(Paciente Paciente){
        this(
                Paciente.getId(),
                Paciente.getNome(),
                Paciente.getEmail(),
                Paciente.getCpf(),
                Paciente.getTelefone(),
                Paciente.getEndereco()
        );
    }
}
