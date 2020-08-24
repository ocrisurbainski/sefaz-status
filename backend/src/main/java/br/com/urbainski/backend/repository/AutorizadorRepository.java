package br.com.urbainski.backend.repository;

import br.com.urbainski.backend.model.Autorizador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
@Repository
public interface AutorizadorRepository extends CrudRepository<Autorizador, Long> {

    Optional<Autorizador> findByNome(String nome);

}