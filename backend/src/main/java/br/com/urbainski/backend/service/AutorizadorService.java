package br.com.urbainski.backend.service;

import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.repository.AutorizadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 23/08/2020
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AutorizadorService {

    private final AutorizadorRepository repository;

    public Autorizador verificarCadastar(String nomeAutorizador) {

        Optional<Autorizador> op = repository.findByNome(nomeAutorizador);

        if (op.isPresent()) {

            return op.get();
        }

        return cadastrar(nomeAutorizador);
    }

    private Autorizador cadastrar(String nomeAutorizador) {

        Autorizador autorizador = Autorizador.builder().nome(nomeAutorizador).build();

        return repository.save(autorizador);
    }

}