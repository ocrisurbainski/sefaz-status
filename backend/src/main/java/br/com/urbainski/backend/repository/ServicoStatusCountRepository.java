package br.com.urbainski.backend.repository;

import br.com.urbainski.backend.model.ServicoStatus;
import br.com.urbainski.backend.model.ServicoStatusCount;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 23/08/2020
 */
@Repository
public interface ServicoStatusCountRepository extends CrudRepository<ServicoStatusCount, Long> {

    Optional<ServicoStatusCount> findByServicoStatusAndStatus(ServicoStatus servicoStatus, ServicoStatusEnum status);

}