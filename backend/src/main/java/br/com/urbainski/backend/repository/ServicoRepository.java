package br.com.urbainski.backend.repository;

import br.com.urbainski.backend.model.Servico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
@Repository
public interface ServicoRepository extends CrudRepository<Servico, Long> {

    Optional<Servico> findByNome(String nome);

}