package br.com.urbainski.backend.service;

import br.com.urbainski.backend.model.ServicoStatus;
import br.com.urbainski.backend.model.ServicoStatusCount;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import br.com.urbainski.backend.repository.ServicoStatusCountRepository;
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
public class ServicoStatusCountService {

    private final ServicoStatusCountRepository repository;

    public ServicoStatusCount save(ServicoStatusCount servicoStatusCount) {

        return repository.save(servicoStatusCount);
    }

    public Optional<ServicoStatusCount> findByServicoStatusAndStatus(
            ServicoStatus servicoStatus, ServicoStatusEnum status) {

        return repository.findByServicoStatusAndStatus(servicoStatus, status);
    }

}