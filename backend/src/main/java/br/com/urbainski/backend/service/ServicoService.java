package br.com.urbainski.backend.service;

import br.com.urbainski.backend.model.Servico;
import br.com.urbainski.backend.repository.ServicoRepository;
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
public class ServicoService {

    private final ServicoRepository repository;

    public Servico verificarCadastar(String nomeServico) {

        Optional<Servico> op = repository.findByNome(nomeServico);

        if (op.isPresent()) {

            return op.get();
        }

        return cadastrar(nomeServico);
    }

    private Servico cadastrar(String nomeServico) {

        Servico servico = Servico.builder().nome(nomeServico).build();

        return repository.save(servico);
    }

}