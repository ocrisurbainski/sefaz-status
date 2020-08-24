package br.com.urbainski.backend.repository;

import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.model.Servico;
import br.com.urbainski.backend.model.ServicoStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
@Repository
public interface ServicoStatusRepository extends CrudRepository<ServicoStatus, Long> {

    Optional<ServicoStatus> findByAutorizadorAndServicoAndDataConsulta(
            Autorizador autorizador, Servico servico, LocalDate dataConsulta);

}