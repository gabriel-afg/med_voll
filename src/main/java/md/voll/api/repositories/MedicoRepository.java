package md.voll.api.repositories;

import md.voll.api.enums.Especialidade;
import md.voll.api.models.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query("select m from Medico m" +
            " where m.ativo=true and m.especialidade = :especialidade" +
            " and m.id not in(select c.medico.id from Consulta c where c.data = :data)" +
            " order by rand() limit 1")
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
