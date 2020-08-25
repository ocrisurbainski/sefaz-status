package br.com.urbainski.backend.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Cristian Urbainski
 * @since 20/08/2020
 */
public final class JsoupUtils {

    private JsoupUtils() {

    }

    public static Document urlToDocument(String url) {

        try {

            return Jsoup.connect(url).get();
        } catch (Throwable ex) {

            return null;
        }
    }

    public static Document htmlToDocument(String html) {

        return Jsoup.parse(html);
    }

}
