package br.com.urbainski.backend.extractor;

import br.com.urbainski.backend.util.JsoupUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author Cristian Urbainski
 * @since 21/08/2020
 */
@Component
public class HttpSefazStatusExtractor extends AbstractSefazStatusExtractor {

    private static final String URL_CONSULTA = "http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx";

    @Override
    public Document getDocument() {

        return JsoupUtils.urlToDocument(URL_CONSULTA);
    }

}