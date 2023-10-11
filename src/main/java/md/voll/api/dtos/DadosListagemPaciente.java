package md.voll.api.dtos;

import md.voll.api.enums.Especialidade;
import md.voll.api.models.Paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String cpf

){

    public DadosListagemPaciente(Paciente Paciente){
        this(
                Paciente.getId(),
                Paciente.getNome(),
                Paciente.getEmail(),
                Paciente.getCpf()
        );
    }
}
