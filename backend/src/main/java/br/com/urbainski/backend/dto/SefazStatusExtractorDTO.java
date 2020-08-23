package br.com.urbainski.backend.dto;

import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
@Getter
@Builder
public class SefazStatusExtractorDTO {

    private String autorizador;
    private String servico;
    private ServicoStatusEnum status;

}