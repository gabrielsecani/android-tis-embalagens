package br.com.tisengenharia.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 11/10/2015.
 * <p/>
 * Sample of XML:
 * <br>http://www.rotadareciclagem.com.br/siteAjax.html?method=info&id=13413&tipo=comercio
 */
public class XMLBaloonParser {

    // We don't use namespaces
    private static final String ns = null;

    public String parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            if (in == null)
                throw new IOException("InputStream must have a value of InputStream valid!");
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            StringBuilder sb = new StringBuilder();
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.next() == XmlPullParser.TEXT)
                    sb.append(parser.getText());
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }
    }
}