package br.com.urbainski.backend.endpoint;

import br.com.urbainski.backend.model.Autorizador;
import br.com.urbainski.backend.model.ServicoStatus;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import br.com.urbainski.backend.service.AutorizadorService;
import br.com.urbainski.backend.service.ServicoStatusCountService;
import br.com.urbainski.backend.service.ServicoStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Cristian Urbainski
 * @since 24/08/2020
 */
@RestController
@RequestMapping("/v1/servico-status")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServicoStatusEndpoint {

    private final ServicoStatusService servicoStatusService;

    private final ServicoStatusCountService servicoStatusCountService;

    private final AutorizadorService autorizadorService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {

        LocalDate dataConsulta = LocalDate.now();
        Optional<List<ServicoStatus>> op = servicoStatusService.findByDataConsulta(dataConsulta);

        if (op.isEmpty()) {

            Optional<LocalDate> optionalData = servicoStatusService.findMaxDataConsulta();

            if (optionalData.isEmpty()) {

                return ResponseEntity.noContent().build();
            }

            dataConsulta = optionalData.get();
            op = servicoStatusService.findByDataConsulta(dataConsulta);

            if (op.isEmpty()) {

                return ResponseEntity.noContent().build();
            }
        }

        Map<String, Object> dados = converter(op.get(), dataConsulta);
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/{nomeAutorizador}")
    public ResponseEntity<Map<String, Object>> findAllByAutorizador(
            @PathVariable("nomeAutorizador") String nomeAutorizador,
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        Optional<Autorizador> optionalAutorizador = autorizadorService.findByNome(nomeAutorizador);

        if (optionalAutorizador.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat.format(
                    "{0} não foi reconhecido como um autorizador válido.", nomeAutorizador));
        }

        Autorizador autorizador = optionalAutorizador.get();
        Optional<List<ServicoStatus>> op = Optional.empty();

        LocalDate dataConsulta = LocalDate.now();

        if (data == null) {

            op = servicoStatusService.findByAutorizadorAndDataConsulta(autorizador, dataConsulta);

            if (op.isEmpty()) {

                Optional<LocalDate> optionalData = servicoStatusService.findMaxDataConsulta();

                if (optionalData.isEmpty()) {

                    return ResponseEntity.noContent().build();
                }

                dataConsulta = optionalData.get();
                op = servicoStatusService.findByAutorizadorAndDataConsulta(autorizador, dataConsulta);

                if (op.isEmpty()) {

                    return ResponseEntity.noContent().build();
                }
            }
        } else {

            dataConsulta = data;
            op = servicoStatusService.findByAutorizadorAndDataConsulta(autorizador, dataConsulta);

            if (op.isEmpty()) {

                return ResponseEntity.noContent().build();
            }
        }

        Map<String, Object> dados = converter(op.get(), dataConsulta);
        return ResponseEntity.ok(dados);
    }

    @GetMapping("/top/indisponibilidade")
    public ResponseEntity<Map<String, String>> findTopIndisponibilidade() {

        LocalDate dataConsulta = LocalDate.now();

        Optional<Autorizador> optional = autorizadorService.findTopStatus(dataConsulta, ServicoStatusEnum.VERMELHO);

        if (optional.isEmpty()) {

            Optional<LocalDate> optionalData = servicoStatusService.findMaxDataConsulta();

            if (optionalData.isEmpty()) {

                return ResponseEntity.noContent().build();
            }

            dataConsulta = optionalData.get();

            optional = autorizadorService.findTopStatus(dataConsulta, ServicoStatusEnum.VERMELHO);

            if (optional.isEmpty()) {

                return ResponseEntity.noContent().build();
            }
        }

        Map<String, String> values = new HashMap<>();
        values.put("autorizadora", optional.get().getNome());

        return ResponseEntity.ok(values);
    }

    private Map<String, Object> converter(List<ServicoStatus> list, LocalDate dataConsulta) {

        Map<String, Object> dados = new HashMap<>();
        dados.put("data", dataConsulta);

        for (ServicoStatus servicoStatus : list) {

            String autorizador = servicoStatus.getAutorizador().getNome();

            Map<String, Object> dadosAutorizador = new HashMap<>();

            if (dados.containsKey(autorizador)) {

                dadosAutorizador = (Map<String, Object>) dados.get(autorizador);
            } else {

                dados.put(autorizador, dadosAutorizador);
            }

            dadosAutorizador.put(servicoStatus.getServico().getNome(), servicoStatus.getStatus().name());
        }

        return dados;
    }

}