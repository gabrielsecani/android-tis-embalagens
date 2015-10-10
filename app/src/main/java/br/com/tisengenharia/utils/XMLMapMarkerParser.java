package br.com.tisengenharia.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.tisengenharia.tisapp.R;
import br.com.tisengenharia.tisapp.model.PontoDeTroca;

/**
 * Created by Gabriel Lucas de Toledo Ribeiro on 04/10/2015.
 * <p/>
 * Sample of XML:
 * <br>http://www.rotadareciclagem.com.br/site.html?method=carregaEntidades&latMax=-18.808692664927005&lngMax=-31.80579899520876&latMin=-25.010725932824087&lngMin=-54.17868717880251&zoomAtual=7
 * <p><pre>
 * <markers>
 *     <marker lat="-20.5504415" lng="-47.4095209" id="5731" prefixo="pev">
 *         <![CDATA[ PEV - SUPERCENTER FRANCA ]]>
 *     </marker>
 *     <marker lat="-21.541816" lng="-42.18319" id="-5" prefixo="comercio">
 *         <![CDATA[ ]]>
 *     </marker>
 * </markers>
 * </pre></p>
 */
public class XMLMapMarkerParser {

    // We don't use namespaces
    private static final String ns = null;

    public List<PontoDeTroca> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            if (in == null)
                throw new IOException("InputStream must have a value of InputStream valid!");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readMarkers(parser);
        } finally {
            in.close();
        }
    }

    private List<PontoDeTroca> readMarkers(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<PontoDeTroca> entries = new ArrayList<PontoDeTroca>();

        parser.require(XmlPullParser.START_TAG, ns, "markers");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the markers tag
            // <markers>
            if (name.equals("markers")) {
                entries.add(readMarker(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // Parses the contents of an PontoDeTroca. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private PontoDeTroca readMarker(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "marker");

        double latitude = 0, longitude = 0;
        int id = 0;
        String prefixo = null;
        String cData = null;

        while (parser.nextToken() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals("marker")) {
                // Read the data from attributes
                // <marker lat="-20.5504415" lng="-47.4095209" id="5731" prefixo="pev">
                // <![CDATA[ PEV - SUPERCENTER FRANCA ]]>
                // </marker>
                for (int i = 0; i < parser.getAttributeCount(); i++) {
                    if (parser.getAttributeName(i).equals("lat")) {
                        latitude = Double.parseDouble(parser.getAttributeValue(i));
                    } else if (parser.getAttributeName(i).equals("lng")) {
                        longitude = Double.parseDouble(parser.getAttributeValue(i));
                    } else if (parser.getAttributeName(i).equals("id")) {
                        id = Integer.parseInt(parser.getAttributeValue(i));
                    } else if (parser.getAttributeName(i).equals("prefixo")) {
                        prefixo = parser.getAttributeValue(i);
                    }
                    cData = readText(parser);
                }
            } else {
                skip(parser);
            }
        }
        return new PontoDeTroca(latitude, longitude, id, prefixo, cData, R.drawable.marcadorpadrao);
    }

//    // Processes title tags in the feed.
//    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "title");
//        String title = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "title");
//        return title;
//    }
//
//    // Processes link tags in the feed.
//    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
//        parser.require(XmlPullParser.START_TAG, ns, "link");
//        String tag = parser.getName();
//        String relType = parser.getAttributeValue(null, "rel");
//        if (tag.equals("link")) {
//            if (relType.equals("alternate")) {
//                link = parser.getAttributeValue(null, "href");
//                parser.nextTag();
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "link");
//        return link;
//    }
//
//    // Processes summary tags in the feed.
//    private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "summary");
//        String summary = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "summary");
//        return summary;
//    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
