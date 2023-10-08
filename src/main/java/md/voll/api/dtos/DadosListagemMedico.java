package md.voll.api.dtos;

import md.voll.api.enums.Especialidade;
import md.voll.api.models.Medico;

public record DadosListagemMedico(
        String nome,
        String email,
        String crm,
        Especialidade especialidade
){

    public DadosListagemMedico(Medico medico){
        this(
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}
