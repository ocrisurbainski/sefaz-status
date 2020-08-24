package br.com.urbainski.backend.job;

import br.com.urbainski.backend.dto.SefazStatusExtractorDTO;
import br.com.urbainski.backend.extractor.HttpSefazStatusExtractor;
import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.model.Servico;
import br.com.urbainski.backend.model.ServicoStatus;
import br.com.urbainski.backend.model.ServicoStatusCount;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import br.com.urbainski.backend.service.AutorizadorService;
import br.com.urbainski.backend.service.ServicoService;
import br.com.urbainski.backend.service.ServicoStatusCountService;
import br.com.urbainski.backend.service.ServicoStatusService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 23/08/2020
 */
@Component
@DisallowConcurrentExecution
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsultaSefazStatusJob implements Job {

    private final HttpSefazStatusExtractor extractor;

    private final AutorizadorService autorizadorService;

    private final ServicoService servicoService;

    private final ServicoStatusService servicoStatusService;

    private final ServicoStatusCountService servicoStatusCountService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<SefazStatusExtractorDTO> list = extractor.extract();

        save(list);
    }

    @Transactional
    private void save(List<SefazStatusExtractorDTO> list) {

        Map<String, Autorizador> mapCacheAutorizador = new HashMap<>();
        Map<String, Servico> mapCacheServico = new HashMap<>();

        for (SefazStatusExtractorDTO dto : list) {

            Autorizador autorizador = verficarCadastrarAutorizador(dto.getAutorizador(), mapCacheAutorizador);

            Servico servico = verificarCadastrarServico(dto.getServico(), mapCacheServico);

            LocalDate dataConsulta = LocalDate.now();

            ServicoStatus servicoStatus = verificarCadastrarServicoStatus(autorizador, servico, dataConsulta, dto.getStatus());

            ServicoStatusCount servicoStatusCount = verificarCadastrarServicoStatusCount(servicoStatus);
        }
    }

    private ServicoStatusCount verificarCadastrarServicoStatusCount(ServicoStatus servicoStatus) {

        Optional<ServicoStatusCount> optionalServicoStatusCount = servicoStatusCountService
                .findByServicoStatusAndStatus(servicoStatus, servicoStatus.getStatus());

        ServicoStatusCount servicoStatusCount = null;

        if (optionalServicoStatusCount.isPresent()) {

            servicoStatusCount = optionalServicoStatusCount.get();
        } else {

            servicoStatusCount = ServicoStatusCount.builder()
                    .servicoStatus(servicoStatus)
                    .status(servicoStatus.getStatus())
                    .count(0L)
                    .build();
        }

        servicoStatusCount.setCount(servicoStatusCount.getCount() + 1L);

        return servicoStatusCountService.save(servicoStatusCount);
    }

    private ServicoStatus verificarCadastrarServicoStatus(
            Autorizador autorizador, Servico servico, LocalDate dataConsulta, ServicoStatusEnum status) {

        Optional<ServicoStatus> optionalServicoStatus = servicoStatusService
                .findByAutorizadorAndServicoAndDataConsulta(autorizador, servico, dataConsulta);

        ServicoStatus servicoStatus = null;

        if (optionalServicoStatus.isPresent()) {

            servicoStatus = optionalServicoStatus.get();
        } else {

            servicoStatus = ServicoStatus.builder()
                    .autorizador(autorizador)
                    .servico(servico)
                    .dataConsulta(dataConsulta)
                    .build();
        }

        servicoStatus.setStatus(status);

        return servicoStatusService.save(servicoStatus);
    }

    private Autorizador verficarCadastrarAutorizador(String nome, Map<String, Autorizador> mapCacheAutorizador) {

        Autorizador autorizador = mapCacheAutorizador.get(nome);

        if (autorizador == null) {

            autorizador = autorizadorService.verificarCadastar(nome);

            mapCacheAutorizador.put(nome, autorizador);
        }

        return autorizador;
    }

    private Servico verificarCadastrarServico(String nome, Map<String, Servico> mapCacheServico) {

        Servico servico = mapCacheServico.get(nome);

        if (servico == null) {

            servico = servicoService.verificarCadastar(nome);

            mapCacheServico.put(nome, servico);
        }

        return servico;
    }

}