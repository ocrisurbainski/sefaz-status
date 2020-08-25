package br.com.urbainski.backend.repository;

import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
@Repository
public interface AutorizadorRepository extends CrudRepository<Autorizador, Long> {

    Optional<Autorizador> findByNome(String nome);

    @Query("SELECT a FROM ServicoStatusCount ssc "
            + "INNER JOIN ServicoStatus ss on ss.id = ssc.servicoStatus "
            + "INNER JOIN Autorizador a on a.id = ss.autorizador "
            + "WHERE ssc.status = ?2 AND ss.dataConsulta = ?1 "
            + "GROUP BY a")
    Optional<Autorizador> findTopStatus(LocalDate dataConsulta, ServicoStatusEnum status, PageRequest page);
}