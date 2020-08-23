package br.com.urbainski.backend.extractor;

import br.com.urbainski.backend.dto.SefazStatusExtractorDTO;
import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.util.*;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
public abstract class AbstractSefazStatusExtractor extends AbstractExtractor<List<SefazStatusExtractorDTO>> {

    private List<Integer> colunsToIgone = Arrays.asList(6);

    public abstract Document getDocument();

    @Override
    public List<SefazStatusExtractorDTO> extract() {

        Elements elements = getElementsTableBody();

        return convertToDto(elements);
    }

    private List<SefazStatusExtractorDTO> convertToDto(Elements elements) {

        List<SefazStatusExtractorDTO> list = new ArrayList<>();

        if (elements == null || elements.isEmpty()) {

            return list;
        }

        Map<Integer, String> mapServicos = new HashMap<>();

        for (int i = 0; i < elements.size(); i++) {

            Element el = elements.get(i);

            if (i == 0) {

                mapServicos = buildServicosMap(el);

                if (mapServicos.isEmpty()) {

                    break;
                }

                continue;
            }

            list.addAll(convertLineAutorizador(el, mapServicos));
        }

        return list;
    }

    private List<SefazStatusExtractorDTO> convertLineAutorizador(Element el, Map<Integer, String> mapServicos) {

        List<SefazStatusExtractorDTO> list = new ArrayList<>();

        Elements elements = el.select(new Evaluator.Tag("td"));

        if (elements == null || elements.isEmpty()) {

            return list;
        }

        String autorizador = elements.get(0).text();

        for (int i = 1; i < elements.size(); i++) {

            if (colunsToIgone.contains(i)) {

                continue;
            }

            ServicoStatusEnum status = convertToServicoStatusEnum(elements.get(i));

            if (status == null) {

                continue;
            }

            SefazStatusExtractorDTO dto = SefazStatusExtractorDTO.builder()
                    .autorizador(autorizador)
                    .servico(mapServicos.get(i))
                    .status(status)
                    .build();

            list.add(dto);
        }

        return list;
    }

    private ServicoStatusEnum convertToServicoStatusEnum(Element el) {

        String text = el.outerHtml();

        if (text.contains("_verde_")) {

            return ServicoStatusEnum.VERDE;
        } else if (text.contains("_amarela_")) {

            return ServicoStatusEnum.AMARELO;
        } else if (text.contains("_vermelho_")) {

            return ServicoStatusEnum.VERMELHO;
        }

        return null;
    }

    private Map<Integer, String> buildServicosMap(Element el) {

        Elements elements = el.select(new Evaluator.Tag("th"));

        if (elements.isEmpty()) {

            return new HashMap<>();
        }

        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < elements.size(); i++) {

            if (i == 0 || colunsToIgone.contains(i)) {

                continue;
            }

            map.put(i, elements.get(i).text());
        }

        return map;
    }

    private Elements getElementsTableBody() {

        Document doc = getDocument();

        if (doc == null) {

            return null;
        }

        Elements elements = doc.select(".tabelaListagemDados");

        if (elements.isEmpty()) {

            return null;
        }

        return elements.get(0).select(new Evaluator.Tag("tr"));
    }

}