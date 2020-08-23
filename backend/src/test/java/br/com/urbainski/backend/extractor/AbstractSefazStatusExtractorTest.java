package br.com.urbainski.backend.extractor;

import br.com.urbainski.backend.dto.SefazStatusExtractorDTO;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import br.com.urbainski.backend.util.JsoupUtils;
import com.google.common.io.Resources;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Cristian Urbainski
 * @since 21/08/2020
 */
public class AbstractSefazStatusExtractorTest {

    private static List<SefazStatusExtractorDTO> listDto;

    @BeforeAll
    public static void setup() {

        AbstractSefazStatusExtractor extractor = new AbstractSefazStatusExtractor() {

            @Override
            public Document getDocument() {

                try {

                    URL urlFile = Resources.getResource("consulta_disponibilidade.html");
                    String html = Resources.toString(urlFile, StandardCharsets.UTF_8);

                    return JsoupUtils.htmlToDocument(html);
                } catch (Throwable ex) {

                    return null;
                }
            }

        };

        listDto = extractor.extract();
    }

    @Test
    public void test_not_found_html() {

        AbstractSefazStatusExtractor extractor = new AbstractSefazStatusExtractor() {

            @Override
            public Document getDocument() {

                return null;
            }

        };

        List<SefazStatusExtractorDTO> listDto = extractor.extract();

        Assertions.assertEquals(0, listDto.size());
    }

    @Test
    public void test_html_sem_dados() {

        AbstractSefazStatusExtractor extractor = new AbstractSefazStatusExtractor() {

            @Override
            public Document getDocument() {

                try {

                    URL urlFile = Resources.getResource("consulta_disponibilidade_sem_tabela_dados.html");
                    String html = Resources.toString(urlFile, StandardCharsets.UTF_8);

                    return JsoupUtils.htmlToDocument(html);
                } catch (Throwable ex) {

                    return null;
                }
            }

        };

        List<SefazStatusExtractorDTO> listDto = extractor.extract();

        Assertions.assertEquals(0, listDto.size());
    }

    @Test
    public void test_status_verde() {

        SefazStatusExtractorDTO dto = getDto("AM", "Autorização4");

        if (dto == null) {

            Assertions.fail();
            return;
        }

        Assertions.assertEquals(ServicoStatusEnum.VERDE.ordinal(), dto.getStatus().ordinal());
    }

    @Test
    public void test_status_amarela() {

        SefazStatusExtractorDTO dto = getDto("BA", "Autorização4");

        if (dto == null) {

            Assertions.fail();
            return;
        }

        Assertions.assertEquals(ServicoStatusEnum.AMARELO.ordinal(), dto.getStatus().ordinal());
    }

    @Test
    public void test_status_vermelha() {

        SefazStatusExtractorDTO dto = getDto("CE", "Autorização4");

        if (dto == null) {

            Assertions.fail();
            return;
        }

        Assertions.assertEquals(ServicoStatusEnum.VERMELHO.ordinal(), dto.getStatus().ordinal());
    }

    private SefazStatusExtractorDTO getDto(String autorizador, String servico) {

        if (listDto == null || listDto.isEmpty()) {

            return null;
        }

        return listDto.stream()
                .filter(dto -> dto.getAutorizador().equals(autorizador) && dto.getServico().equals(servico))
                .findFirst()
                .orElse(null);
    }

}
