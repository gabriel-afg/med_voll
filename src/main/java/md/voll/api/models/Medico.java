package md.voll.api.models;

import jakarta.persistence.*;
import lombok.*;
import md.voll.api.enums.Especialidade;

@Entity(name = "Medico")
@Table(name = "tb_medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
}
