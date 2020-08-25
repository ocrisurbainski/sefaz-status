package br.com.urbainski.backend.service;

import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.model.Servico;
import br.com.urbainski.backend.model.ServicoStatus;
import br.com.urbainski.backend.repository.ServicoStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 23/08/2020
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServicoStatusService {

    private final ServicoStatusRepository repository;

    public ServicoStatus save(ServicoStatus servicoStatus) {

        return repository.save(servicoStatus);
    }

    public Optional<ServicoStatus> findByAutorizadorAndServicoAndDataConsulta(
            Autorizador autorizador, Servico servico, LocalDate dataConsulta) {

        return repository.findByAutorizadorAndServicoAndDataConsulta(autorizador, servico, dataConsulta);
    }

    public Optional<List<ServicoStatus>> findByAutorizadorAndDataConsulta(Autorizador autorizador, LocalDate dataConsulta) {

        return repository.findByAutorizadorAndDataConsulta(autorizador, dataConsulta);
    }

    public Optional<List<ServicoStatus>> findByDataConsulta(LocalDate dataConsulta) {

        return repository.findByDataConsulta(dataConsulta);
    }

    public Optional<LocalDate> findMaxDataConsulta() {

        return repository.findMaxDataConsulta();
    }

}