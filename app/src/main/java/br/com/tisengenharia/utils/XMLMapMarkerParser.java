package br.com.tisengenharia.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
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

    public static List<PontoDeTroca> makeATest() {

        List<PontoDeTroca> items = null;


        StringReader sr = new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<markers>\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5504415\" lng=\"-47.4095209\" id=\"5731\" prefixo=\"pev\"><![CDATA[PEV - SUPERCENTER FRANCA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.541816\" lng=\"-42.18319\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1200732\" lng=\"-42.94264720000001\" id=\"14895\" prefixo=\"associacao\"><![CDATA[RECICLAU]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.9250757\" lng=\"-50.5344471\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8481435\" lng=\"-43.80759820000003\" id=\"12907\" prefixo=\"cooperativa\"><![CDATA[UTC de Lima Duarte]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6586228\" lng=\"-42.34792970000001\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.0784452\" lng=\"-46.2042268\" id=\"9070\" prefixo=\"comercio\"><![CDATA[Verde Renova Comércio de Material Reciclável]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.629314156307736\" lng=\"-49.658203125\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7616301\" lng=\"-48.8180957\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1588479\" lng=\"-51.3752935\" id=\"6819\" prefixo=\"cooperativa\"><![CDATA[Cooperlix - Cooperativa de Trabalho, Produção e Reciclagem de Presidente Prudente]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1208486\" lng=\"-42.9430186\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.0870051\" lng=\"-49.3259918\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2785284\" lng=\"-45.91795769999999\" id=\"14199\" prefixo=\"pev\"><![CDATA[PEV - Unilever Pouso Alegre]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.9495638\" lng=\"-48.4788627\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4369402\" lng=\"-49.971683\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9459949\" lng=\"-44.1904513\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2412751\" lng=\"-45.0013831\" id=\"-10\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7789438\" lng=\"-42.147476900000015\" id=\"12223\" prefixo=\"comercio\"><![CDATA[Reciclagem Simões e Souza]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.270905\" lng=\"-40.298327\" id=\"-8\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7784459\" lng=\"-45.971547499999986\" id=\"7010\" prefixo=\"cooperativa\"><![CDATA[Usina de Reciclagem de Poço Fundo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8506582\" lng=\"-42.0185016\" id=\"6576\" prefixo=\"cooperativa\"><![CDATA[Cooperativa da Região dos Lagos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2914106\" lng=\"-49.55593920000001\" id=\"13769\" prefixo=\"associacao\"><![CDATA[Assemare - Associação de Gália]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.350123\" lng=\"-43.119878\" id=\"-5\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.023927\" lng=\"-47.3726953\" id=\"4540\" prefixo=\"cooperativa\"><![CDATA[Ascalt - Associação de Catadores de Mat. Rec. de Altinópolis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1022283\" lng=\"-42.45467719999999\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.77823907676769\" lng=\"-50.20103931427002\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.694646271336627\" lng=\"-44.82027053833008\" id=\"3358\" prefixo=\"cooperativa\"><![CDATA[ASCOL - Associação dos Catadores de Materiais Recicláveis de Oliveira]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4557268\" lng=\"-47.00895589999999\" id=\"15218\" prefixo=\"pev\"><![CDATA[Led Reciclagem tecnológica ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.3569813\" lng=\"-47.870272\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2857515\" lng=\"-44.401946599999974\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.7368248\" lng=\"-47.6292271\" id=\"-7\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.129804925264295\" lng=\"-48.97035598754883\" id=\"10478\" prefixo=\"pev\"><![CDATA[Supermercado Maranhão]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0027866\" lng=\"-49.32460600000002\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1275845\" lng=\"-44.24116889999999\" id=\"3469\" prefixo=\"cooperativa\"><![CDATA[ASCAS - Associação de Material Reciclável]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.9332363\" lng=\"-53.4467339\" id=\"-5\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.903631\" lng=\"-48.3400935\" id=\"11467\" prefixo=\"pev\"><![CDATA[Ecoponto Tocantins]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.955203\" lng=\"-45.84081\" id=\"-5\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.88189\" lng=\"-52.1637\" id=\"10740\" prefixo=\"cooperativa\"><![CDATA[Associação dos Catadores de Palmital]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8775283\" lng=\"-43.76746909999997\" id=\"-7\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0458672\" lng=\"-49.1691371\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8314105\" lng=\"-41.1779659\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1058964\" lng=\"-45.8291595\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8344104\" lng=\"-49.357710399999974\" id=\"14708\" prefixo=\"pev\"><![CDATA[Carrefour São José do Rio Preto]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9098073\" lng=\"-43.69486619999998\" id=\"15295\" prefixo=\"associacao\"><![CDATA[Centro de Inclusão e Socialização João Manoel Monteiro]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1192315\" lng=\"-41.99481079999998\" id=\"-3\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.929298\" lng=\"-42.2226284\" id=\"6255\" prefixo=\"cooperativa\"><![CDATA[Cooperativa de Coleta de Recicláveis da Costa do Sol]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8675898\" lng=\"-40.942878199999996\" id=\"16361\" prefixo=\"associacao\"><![CDATA[ASCARENOVO - Associação dos Catadores de Recicláveis de Rio Novo do Sul]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4902526\" lng=\"-44.644993399999976\" id=\"15259\" prefixo=\"triagem\"><![CDATA[UTC de Carrancas ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.9055979\" lng=\"-40.07590379999999\" id=\"15906\" prefixo=\"associacao\"><![CDATA[ACAMARES - Associação de Catadores de Materiais Reciclados de Jaguaré]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.0087082\" lng=\"-47.89092629999999\" id=\"-21\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.613148145013685\" lng=\"-42.12750434875488\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4035658\" lng=\"-42.9708109\" id=\"-89\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.278905\" lng=\"-48.318479\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.86\" lng=\"-46.03750000000002\" id=\"16735\" prefixo=\"associacao\"><![CDATA[Associação de Catadores de Monte Verde ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2740417\" lng=\"-40.0837871\" id=\"2112\" prefixo=\"comercio\"><![CDATA[Norte Recicla]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.104742\" lng=\"-46.964577\" id=\"-7\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7663903\" lng=\"-41.3467983\" id=\"-9\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.34688902\" lng=\"-50.6646809\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.7256005\" lng=\"-53.7266436\" id=\"-7\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.729117313438085\" lng=\"-53.497066497802734\" id=\"16791\" prefixo=\"triagem\"><![CDATA[Centro de Triagem de Xambrê]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.295556176157447\" lng=\"-51.57085418701172\" id=\"15336\" prefixo=\"associacao\"><![CDATA[Associação de Monte Castelo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.947525\" lng=\"-43.4942772\" id=\"12033\" prefixo=\"comercio\"><![CDATA[Reciclagem Três Moinhos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4781219\" lng=\"-51.5401457\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2179324\" lng=\"-40.2496058\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.1444709\" lng=\"-40.6244609\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7041304\" lng=\"-43.44491949999997\" id=\"13109\" prefixo=\"triagem\"><![CDATA[ASCAJUF - Usina de lixo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.341663\" lng=\"-49.19905879999999\" id=\"8734\" prefixo=\"comercio\"><![CDATA[Reciclagem Icém]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.24814558\" lng=\"-51.18673587\" id=\"-10\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.905981\" lng=\"-43.064\" id=\"-22\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.713403\" lng=\"-43.4063748\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.55345\" lng=\"-46.678985\" id=\"-29\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.41\" lng=\"-44.027202699999975\" id=\"-3\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1918576\" lng=\"-40.26017969999998\" id=\"-3\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2841027\" lng=\"-50.2466864\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2831522\" lng=\"-46.38253309999999\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.69688039733695\" lng=\"-41.844048500061035\" id=\"15959\" prefixo=\"associacao\"><![CDATA[ASCOMDEP - Associação dos Catadores de Materiais Recicláveis do Município de Dores do Rio Preto]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.79723\" lng=\"-51.74486\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5865651\" lng=\"-47.878152\" id=\"8281\" prefixo=\"comercio\"><![CDATA[Pelego Sucatas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.881855\" lng=\"-45.28064\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7881689\" lng=\"-51.70304829999998\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6437949\" lng=\"-43.90463010000002\" id=\"14676\" prefixo=\"pev\"><![CDATA[Carrefour Lagoa Santa]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.9654468\" lng=\"-53.4218197\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.6549001\" lng=\"-50.85734030000003\" id=\"16166\" prefixo=\"comercio\"><![CDATA[Reciclagem Antunes]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.5689719\" lng=\"-44.9647292\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2197664\" lng=\"-50.482515000000035\" id=\"-9\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.5137228\" lng=\"-47.4384838\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1907487\" lng=\"-42.61611140000002\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6474834\" lng=\"-42.750222199999996\" id=\"16564\" prefixo=\"triagem\"><![CDATA[Usina de Triagem e Compostagem de Jaguaraçu]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4519362\" lng=\"-43.98410910000001\" id=\"15252\" prefixo=\"triagem\"><![CDATA[UTC Ibertioga]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.019811\" lng=\"-45.962613199999964\" id=\"13744\" prefixo=\"comercio\"><![CDATA[O Adeleiro Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.1459249\" lng=\"-45.79078930000003\" id=\"-28\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.3194823\" lng=\"-43.74843329999999\" id=\"15250\" prefixo=\"comercio\"><![CDATA[RECICLAR]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5284243\" lng=\"-42.647575399999994\" id=\"14486\" prefixo=\"comercio\"><![CDATA[Faísca Rciclagem Ltda ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.0188483\" lng=\"-48.7748439\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.39094223275014\" lng=\"-49.99311447143555\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2928869\" lng=\"-40.37525679999999\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.1051421\" lng=\"-49.34305\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9088224\" lng=\"-43.958070099999986\" id=\"15254\" prefixo=\"triagem\"><![CDATA[UTC de Prados ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9190476\" lng=\"-43.961404000000016\" id=\"16181\" prefixo=\"associacao\"><![CDATA[ASMARE - Galpão Ituiutaba]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.7480772\" lng=\"-41.8813152\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7144618\" lng=\"-47.4147066\" id=\"10578\" prefixo=\"cooperativa\"><![CDATA[COTMAP - Coop. dos Trabalhadores do Meio Ambiente de Piedade]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3493201\" lng=\"-40.3503114\" id=\"-17\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4759414\" lng=\"-49.78320930000001\" id=\"17088\" prefixo=\"triagem\"><![CDATA[Centro de Triagem de Resíduos Solidos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5630211\" lng=\"-46.9834471\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.8247074\" lng=\"-42.47897849999998\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.55429684097349\" lng=\"-44.17426586151123\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.143470530207384\" lng=\"-44.74006175994873\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2688063\" lng=\"-42.50557409999999\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.909403\" lng=\"-49.3693555\" id=\"7639\" prefixo=\"comercio\"><![CDATA[Ferro Velho Santos Reis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.9681128\" lng=\"-49.45802040000001\" id=\"75\" prefixo=\"cooperativa\"><![CDATA[Copercicla - Coop. de Reciclagens de Ituiutaba]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.374893383426\" lng=\"-51.948983950291\" id=\"-12\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.06782\" lng=\"-51.58363\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.847723234851248\" lng=\"-47.10008382797241\" id=\"-31\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.19531967\" lng=\"-50.27096086\" id=\"15648\" prefixo=\"associacao\"><![CDATA[ACAVENT - Associação dos Catadores de Ventania]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6703816\" lng=\"-52.60821199999998\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3166021\" lng=\"-49.0330237\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.4927016\" lng=\"-48.84548640000003\" id=\"16569\" prefixo=\"comercio\"><![CDATA[Reciclagem do Jonas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.506125990873688\" lng=\"-51.46146297454834\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.5795048\" lng=\"-43.6886855\" id=\"1940\" prefixo=\"cooperativa\"><![CDATA[COMDEP - PARACAMBI]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4194554\" lng=\"-50.0752626\" id=\"6831\" prefixo=\"cooperativa\"><![CDATA[CORPE - Cooperativa de Trabalho dos Recicladores de lixo de Penápolis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2994718\" lng=\"-42.475513999999976\" id=\"16092\" prefixo=\"triagem\"><![CDATA[UTC de Abre Campo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.528004004904442\" lng=\"-49.05738830566406\" id=\"12060\" prefixo=\"cooperativa\"><![CDATA[Projeto Lar dos Velhinhos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7520053\" lng=\"-43.3454084\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.424572\" lng=\"-46.558338\" id=\"-43\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.278538\" lng=\"-50.271021\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.8685542\" lng=\"-41.955321200000014\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7787107\" lng=\"-42.1368368\" id=\"10493\" prefixo=\"cooperativa\"><![CDATA[ASMARC Associação dos Seletores de Materiais Recicláveis de Caratinga]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.4727657\" lng=\"-53.08968920000001\" id=\"15656\" prefixo=\"comercio\"><![CDATA[Sucata Show]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3586148\" lng=\"-46.9365632\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8559816\" lng=\"-41.06558239999998\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7480267\" lng=\"-45.9027192\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1517213\" lng=\"-44.9060599\" id=\"-5\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5144543\" lng=\"-41.00252139999998\" id=\"15198\" prefixo=\"associacao\"><![CDATA[ASCAMARE - Associação de Catadores de Materiais Recicláveis Cidadão Amigo do Meio Ambiente]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8539429\" lng=\"-42.5334396\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.6592479\" lng=\"-50.4408626\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8564408\" lng=\"-42.0995883\" id=\"7701\" prefixo=\"cooperativa\"><![CDATA[Usina de Seleção e Reciclagem de São Sebastião do Alto]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.5082967\" lng=\"-48.1534372\" id=\"10124\" prefixo=\"cooperativa\"><![CDATA[Associação dos Catadores de Reciclaveis de Motuca]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.244942380123064\" lng=\"-44.993348121643066\" id=\"1\" prefixo=\"cooperativa\"><![CDATA[ACAMAR - Associação de Catadores Materiais Reciclavéis de Lavras]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0894698\" lng=\"-52.46261959999998\" id=\"14697\" prefixo=\"comercio\"><![CDATA[Depósito do Renato - Heitor Furtado]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3510048\" lng=\"-42.740249000000006\" id=\"13336\" prefixo=\"associacao\"><![CDATA[ACAMARU - Associação de Catadores de Material Reciclavel de Urucânia]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.557711\" lng=\"-43.00597260000001\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9748899\" lng=\"-44.307362\" id=\"10586\" prefixo=\"cooperativa\"><![CDATA[Assoc. Catadores Jabiranga]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4435805\" lng=\"-43.106850699999995\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.48753869121113\" lng=\"-48.56375455856323\" id=\"-12\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4160461\" lng=\"-42.92070030000002\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.73175\" lng=\"-49.341619\" id=\"-19\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.932922891857583\" lng=\"-52.14909553527832\" id=\"15013\" prefixo=\"associacao\"><![CDATA[Associação de Catadores de Paranacity]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.893440494272635\" lng=\"-49.894280433654785\" id=\"11859\" prefixo=\"cooperativa\"><![CDATA[Associação de União Paulista]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.40380765452245\" lng=\"-42.97229290008545\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.177588363805622\" lng=\"-52.71886110305786\" id=\"14107\" prefixo=\"associacao\"><![CDATA[Associação de Catadores Água Amarela]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8606806\" lng=\"-46.03748159999998\" id=\"15217\" prefixo=\"pev\"><![CDATA[Associação de Catadores de Material Reciclável de Monte Verde]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.770082\" lng=\"-50.010695\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.197744\" lng=\"-47.300311\" id=\"-25\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.847955\" lng=\"-45.227411\" id=\"-6\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.993110274298\" lng=\"-51.94866448813\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.104336\" lng=\"-46.67183469999998\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.872529\" lng=\"-48.440376\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.700248323843205\" lng=\"-53.73640537261963\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.527292\" lng=\"-44.10152\" id=\"-7\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.9642483\" lng=\"-42.0386163\" id=\"6427\" prefixo=\"cooperativa\"><![CDATA[ASSOCATA - Associação de Catadores de Materiais Recicláveis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.191357\" lng=\"-46.76891469999998\" id=\"13450\" prefixo=\"associacao\"><![CDATA[Associação Viva Vida]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.6656405\" lng=\"-48.66110420000001\" id=\"17319\" prefixo=\"associacao\"><![CDATA[Projeto Reciclar Adefia Associação dos Deficientes Físicos de Areiópolis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7331938\" lng=\"-42.13283100000001\" id=\"14980\" prefixo=\"comercio\"><![CDATA[Ferro Velho do Mauro]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.0357278\" lng=\"-52.384235\" id=\"8150\" prefixo=\"pev\"><![CDATA[Muffato]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3450322\" lng=\"-49.0475109\" id=\"-7\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.117345025835526\" lng=\"-51.406123638153076\" id=\"14707\" prefixo=\"pev\"><![CDATA[Carrefour Presidente Prudente]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.79859549324888\" lng=\"-47.28588581085205\" id=\"-26\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.44686\" lng=\"-50.74457\" id=\"8783\" prefixo=\"cooperativa\"><![CDATA[ACAMARI]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4503771\" lng=\"-44.7489774\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4733795\" lng=\"-45.1266229\" id=\"10277\" prefixo=\"cooperativa\"><![CDATA[ASCAMARI - Associação de Catadores de Material Reciclável de Itapecerica]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.1115577\" lng=\"-48.9248834\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4052709\" lng=\"-40.05520289999998\" id=\"13950\" prefixo=\"cooperativa\"><![CDATA[ACARLI - Associação dos Catadores de Materiais Recicláveis de Linhares]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.610087\" lng=\"-45.568802300000016\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1519364\" lng=\"-41.62321710000003\" id=\"13116\" prefixo=\"comercio\"><![CDATA[Recicla Brasil]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1949744\" lng=\"-47.8516472\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.319806\" lng=\"-50.601478\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7379184\" lng=\"-43.3426846\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.9959555\" lng=\"-47.4268237\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.9461643\" lng=\"-46.3936302\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.087403065042874\" lng=\"-45.298826692742296\" id=\"17012\" prefixo=\"comercio\"><![CDATA[Prevê Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3349414\" lng=\"-41.1304328\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.1190668\" lng=\"-51.7376946\" id=\"6590\" prefixo=\"cooperativa\"><![CDATA[COMPARE]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.80891457227318\" lng=\"-50.849361419677734\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.905783\" lng=\"-49.810547\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.0962926\" lng=\"-45.282554600000026\" id=\"13578\" prefixo=\"associacao\"><![CDATA[Ascasam]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2110383\" lng=\"-45.2294907\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7593407\" lng=\"-45.906248\" id=\"9418\" prefixo=\"pev\"><![CDATA[Açai 5]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.977378366117843\" lng=\"-51.673593521118164\" id=\"15952\" prefixo=\"comercio\"><![CDATA[Sucatas Correia]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.297232\" lng=\"-51.387863\" id=\"-11\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3007897\" lng=\"-45.9011175\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.0625032\" lng=\"-44.11504130000003\" id=\"16182\" prefixo=\"associacao\"><![CDATA[Associação de Catadores de Materiais Recicláveis de Sarzedo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.463777\" lng=\"-45.22049320000002\" id=\"15307\" prefixo=\"triagem\"><![CDATA[UTC Carmo da Cachoeira]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.6004105\" lng=\"-41.207585600000016\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8364643\" lng=\"-45.40082480000001\" id=\"16295\" prefixo=\"associacao\"><![CDATA[ASCORCAMP]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4641965\" lng=\"-47.023329\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.0662323\" lng=\"-46.9744402\" id=\"-9\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2283201\" lng=\"-43.019130200000006\" id=\"13729\" prefixo=\"triagem\"><![CDATA[UTC de Ferros]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.3973901\" lng=\"-42.7095476\" id=\"8189\" prefixo=\"cooperativa\"><![CDATA[ASCATAG - Associação dos Catadores de Recicláveis de Cataguases]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.99867\" lng=\"-53.72196\" id=\"8761\" prefixo=\"cooperativa\"><![CDATA[Unidade de Triagem de Iporã]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.333563051194307\" lng=\"-48.947439193725586\" id=\"12156\" prefixo=\"cooperativa\"><![CDATA[Caap - Cooperativa dos Agentes Ambientais de Prata]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7278226\" lng=\"-42.408187399999974\" id=\"16552\" prefixo=\"triagem\"><![CDATA[UTC de Pingo d´Agua]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1094506\" lng=\"-44.02061930000002\" id=\"16739\" prefixo=\"triagem\"><![CDATA[UTC de Dores de Campo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0715418\" lng=\"-45.71477820000001\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1744043\" lng=\"-48.6912071\" id=\"8263\" prefixo=\"cooperativa\"><![CDATA[CooperColômbia]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.834775\" lng=\"-49.274283999999966\" id=\"3798\" prefixo=\"cooperativa\"><![CDATA[RECICLAZUL - Associação de Catadores de Resíduos Sólidos de Cerro Azul ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1303151\" lng=\"-42.3673893\" id=\"10309\" prefixo=\"cooperativa\"><![CDATA[ASCAMARE Associação dos Catadores de Material Reciclavel  de Muriaé]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.72374956308493\" lng=\"-50.557708740234375\" id=\"8692\" prefixo=\"comercio\"><![CDATA[Reciclagem Auriflama]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.0444134\" lng=\"-41.97336330000002\" id=\"15471\" prefixo=\"triagem\"><![CDATA[Centro de Triagem de Natividade]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7697279\" lng=\"-41.672296500000016\" id=\"15961\" prefixo=\"associacao\"><![CDATA[ASGUAMAR - Associação dos Catadores de Materiais Recicláveis do Município de Guaçuí]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.8597761\" lng=\"-50.4164763\" id=\"16001\" prefixo=\"cooperativa\"><![CDATA[ACARFI]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4477449\" lng=\"-46.8265581\" id=\"-5\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8903957\" lng=\"-48.4553087\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.71763614303226\" lng=\"-48.09247970581055\" id=\"10733\" prefixo=\"cooperativa\"><![CDATA[Cooreca Cooperativa de Trabalho dos Recicladores da Cidade de Cajati]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4616927\" lng=\"-45.432727099999966\" id=\"13144\" prefixo=\"associacao\"><![CDATA[Associação dos Recicladores de Formiga - Recifor]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.730904\" lng=\"-48.049975\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.967626\" lng=\"-48.9009935\" id=\"6992\" prefixo=\"cooperativa\"><![CDATA[Coamari - Cooperativa de Materiais Recicláveis de Itapeva]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.933049\" lng=\"-47.026011\" id=\"-55\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2318214\" lng=\"-50.9435281\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4977751\" lng=\"-47.38667800000002\" id=\"14449\" prefixo=\"associacao\"><![CDATA[Associação dos Agentes Ambientais e Recicladores de Santa Rosa de Viterbo - RECISA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3306116\" lng=\"-44.237043200000016\" id=\"-4\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.235353\" lng=\"-45.7532386\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0956243\" lng=\"-49.20088950000002\" id=\"13292\" prefixo=\"associacao\"><![CDATA[Associação dos Catadores de Cerqueira César]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4443064\" lng=\"-45.43090560000002\" id=\"13427\" prefixo=\"associacao\"><![CDATA[ACARI - ASSOCIACAO DE CATADORES AUTONOMOS DE RECICLAGEM ITAJUBENSE]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.9836214\" lng=\"-46.2460022\" id=\"-7\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.513339\" lng=\"-46.672905\" id=\"-119\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.05809\" lng=\"-51.05001\" id=\"8748\" prefixo=\"cooperativa\"><![CDATA[Associação de Catadores de Sertanópolis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.040087590901734\" lng=\"-53.477840423583984\" id=\"12063\" prefixo=\"cooperativa\"><![CDATA[Associação de Catadores de Querência do Norte]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6050096\" lng=\"-46.9277935\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9463387\" lng=\"-46.5428503\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.472354\" lng=\"-48.556884\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.67511\" lng=\"-51.191333799999995\" id=\"14589\" prefixo=\"cooperativa\"><![CDATA[Cooperativa de Catadores de Paranaíba - Corepa ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.246846\" lng=\"-42.637039\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.9120609\" lng=\"-51.3568115\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5261343\" lng=\"-47.3744714\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8009304\" lng=\"-45.19814910000002\" id=\"-11\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7923581\" lng=\"-47.09724240000003\" id=\"13321\" prefixo=\"cooperativa\"><![CDATA[COOPSERCAB Cooperativa de Reciclagem de Casa Branca]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.99445014876712\" lng=\"-48.391642570495605\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7977467\" lng=\"-51.66988249999997\" id=\"-23\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6119632\" lng=\"-46.10122430000001\" id=\"14551\" prefixo=\"associacao\"><![CDATA[ASCAMAC - Associação de Catadores de Material Reciclável de Caxambu]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.860199\" lng=\"-42.97143749999998\" id=\"13123\" prefixo=\"triagem\"><![CDATA[Usina de Triagem e Compostagem de São Domingos do Prata]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1875704\" lng=\"-43.9757005\" id=\"9149\" prefixo=\"cooperativa\"><![CDATA[ASCAB - Associação dos Catadores e Recicladores de Materiais Recicláveis de Barroso]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.5335951\" lng=\"-42.637741500000004\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.797158222146\" lng=\"-53.66782079597209\" id=\"15655\" prefixo=\"comercio\"><![CDATA[ELMU - Reciclagem do Português]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.9293472\" lng=\"-46.9926645\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6399075\" lng=\"-48.1597946\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.6412545\" lng=\"-48.841529700000024\" id=\"16578\" prefixo=\"associacao\"><![CDATA[Associação de Itaóca]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.048341\" lng=\"-42.67583\" id=\"3253\" prefixo=\"comercio\"><![CDATA[MKM - SUMIDOURO COM DE SUCATA E RECICLAGEM]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8792356\" lng=\"-42.69241840000001\" id=\"13112\" prefixo=\"associacao\"><![CDATA[ACRAP - Associação de Catadores de Material Reciclável de Além Paraíba]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1303151\" lng=\"-42.3673893\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.226135\" lng=\"-45.01452740000002\" id=\"14731\" prefixo=\"comercio\"><![CDATA[Reciclagem do Antonio]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2081188\" lng=\"-50.44057570000001\" id=\"11664\" prefixo=\"pev\"><![CDATA[Pão de Açúcar]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.959923\" lng=\"-46.799358\" id=\"-9\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.43374\" lng=\"-49.72908\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6035725\" lng=\"-51.6427084\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.62826871584464\" lng=\"-51.173715591430664\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.0538004\" lng=\"-44.3477717\" id=\"-11\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2554749\" lng=\"-52.04573779999998\" id=\"13959\" prefixo=\"cooperativa\"><![CDATA[ASSOBRAA - Associação Brasiliense de Agentes Ambientais]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.5808812\" lng=\"-45.42784110000002\" id=\"3569\" prefixo=\"cooperativa\"><![CDATA[Reciclagem Santa Maria]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4320402\" lng=\"-45.49508549999996\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4605089\" lng=\"-41.41119100000003\" id=\"-3\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.692578\" lng=\"-45.251546099999985\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3509433\" lng=\"-43.1198085\" id=\"-76\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2403501\" lng=\"-41.5060644\" id=\"10501\" prefixo=\"comercio\"><![CDATA[JB RECICLAGEM MUNDIAL]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3906852\" lng=\"-43.4899167\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2113554\" lng=\"-50.466356\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.428765\" lng=\"-47.455799\" id=\"-6\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.979662681434686\" lng=\"-42.3607063293457\" id=\"6437\" prefixo=\"comercio\"><![CDATA[AM de Cantagalo Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8398573\" lng=\"-42.648143800000014\" id=\"-3\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.13165283226347\" lng=\"-49.8382606597994\" id=\"13776\" prefixo=\"associacao\"><![CDATA[ACOMAR - Associação de Catadores de Materiais Reciclaveis de Arapoti]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4810187\" lng=\"-49.2254804\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.509458\" lng=\"-42.630361\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.2403708\" lng=\"-50.2364053\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.55144993712239\" lng=\"-52.988814971698105\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2416203\" lng=\"-49.9382866\" id=\"6817\" prefixo=\"cooperativa\"><![CDATA[Cotracil - Coop. de Trabalho Cidade Limpa de Marília]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.3062386\" lng=\"-45.927880500000015\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2179582\" lng=\"-49.6510747\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.409823227459583\" lng=\"-51.3316011428833\" id=\"7943\" prefixo=\"cooperativa\"><![CDATA[Cooperseli - Cooperativa de Trabalho e Produção dos Profissionais na Seleção para Reciclagem de Lixo de Ilha Solteira]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.2980593\" lng=\"-51.195686\" id=\"8156\" prefixo=\"pev\"><![CDATA[Muffato - Tiradentes]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9948504\" lng=\"-50.354052300000035\" id=\"12030\" prefixo=\"cooperativa\"><![CDATA[Associação Programa Ouro do Lixo ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.963281\" lng=\"-43.98479\" id=\"-17\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.01333302732664\" lng=\"-48.005919456481934\" id=\"8581\" prefixo=\"cooperativa\"><![CDATA[Associação dos Catadores de Recicláveis de Conchas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.5852017\" lng=\"-47.3976404\" id=\"5737\" prefixo=\"pev\"><![CDATA[PEV - SUPERCENTER LIMEIRA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5879735\" lng=\"-46.9315002\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.7606048\" lng=\"-51.7631116\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3584536\" lng=\"-41.9583901\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3175289\" lng=\"-48.3173597\" id=\"9087\" prefixo=\"cooperativa\"><![CDATA[Cooper Ação - Coleta Seletiva - APAMA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.3531081\" lng=\"-45.247899700000005\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.889613\" lng=\"-43.113733\" id=\"-55\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1386035\" lng=\"-51.09939109999999\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.74832844913456\" lng=\"-46.77051544189453\" id=\"15437\" prefixo=\"triagem\"><![CDATA[Usina de Triagem de Itaú de Minas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.533468113522034\" lng=\"-44.757936000823975\" id=\"10720\" prefixo=\"cooperativa\"><![CDATA[Reciclação Queluz]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0844832\" lng=\"-49.5386847\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1700616\" lng=\"-48.0395622\" id=\"9072\" prefixo=\"comercio\"><![CDATA[Reciclagem União]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.166667\" lng=\"-41.766667\" id=\"3562\" prefixo=\"comercio\"><![CDATA[Reciclagem Belisario]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6175681\" lng=\"-46.941122699999994\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.8960112\" lng=\"-48.307881899999984\" id=\"-7\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.898085084120574\" lng=\"-41.94562911987305\" id=\"2257\" prefixo=\"cooperativa\"><![CDATA[ASCANAVI - Associação dos Catadores de Materiais Recicláveis Natureza Viva]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1517213\" lng=\"-44.9060599\" id=\"17009\" prefixo=\"associacao\"><![CDATA[Ascamare (Associação dos Catadores de Materiais Recicláveis)]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1580578\" lng=\"-47.7946081\" id=\"-19\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2068607\" lng=\"-44.9372232\" id=\"3471\" prefixo=\"cooperativa\"><![CDATA[ASCAPEU - Associação dos Catadores de Pompéu]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.116379531283293\" lng=\"-44.878163335088175\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.5492651\" lng=\"-46.9331962\" id=\"-3\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.4253442\" lng=\"-48.7556832\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.5573984\" lng=\"-54.0607016\" id=\"8763\" prefixo=\"cooperativa\"><![CDATA[Cooperagir - Escritório]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2529907\" lng=\"-44.933759\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.526166815557882\" lng=\"-40.631462037563324\" id=\"-73\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.3577807\" lng=\"-41.922427200000016\" id=\"-3\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6723482\" lng=\"-46.62400250000002\" id=\"-57\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.621801027577142\" lng=\"-52.4644030843383\" id=\"13940\" prefixo=\"associacao\"><![CDATA[Acamjus - Associação de Catadores de Materiais Recicláveis de Jussara]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.5365132\" lng=\"-49.85956969999995\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.0254516\" lng=\"-52.370926\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3159723\" lng=\"-48.9792804\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.428354\" lng=\"-51.968633\" id=\"-7\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.4418333\" lng=\"-45.08121360000001\" id=\"830\" prefixo=\"comercio\"><![CDATA[Reciclagem Central]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1204792\" lng=\"-51.4188039\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.404794663829982\" lng=\"-50.09169101715088\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2510752\" lng=\"-43.705219899999975\" id=\"15379\" prefixo=\"comercio\"><![CDATA[Depósito do Sergio (Tira Entulho)]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7551\" lng=\"-53.315833999999995\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.30528\" lng=\"-52.61461\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2421996\" lng=\"-43.801092\" id=\"9383\" prefixo=\"comercio\"><![CDATA[Paty Reciclagem ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.8210123\" lng=\"-45.35785020000003\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.020287\" lng=\"-51.791807000000006\" id=\"12764\" prefixo=\"cooperativa\"><![CDATA[CooperIvaí]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6355188\" lng=\"-49.7505053\" id=\"6341\" prefixo=\"cooperativa\"><![CDATA[COOPERSOL - Cooperativa de Recicladores de Lins]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.48044\" lng=\"-53.1106\" id=\"7531\" prefixo=\"cooperativa\"><![CDATA[ARENO - Associação de Reciclagem de Nova Olímpia]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4789607\" lng=\"-43.125621000000024\" id=\"13340\" prefixo=\"triagem\"><![CDATA[União Recicláveis Rio Novo Ltda ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7060194\" lng=\"-46.4282022\" id=\"-30\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9321115\" lng=\"-40.59993459999998\" id=\"15045\" prefixo=\"triagem\"><![CDATA[COOPAAST - Cooperativa dos Agentes Ambientais de Santa Teresa]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.073887\" lng=\"-49.93437087000001\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.031412435736645\" lng=\"-51.44803056067168\" id=\"11643\" prefixo=\"comercio\"><![CDATA[Reciclagem Bueno]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6136465\" lng=\"-48.370699\" id=\"-3\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.5944793\" lng=\"-48.0539033\" id=\"-10\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.9175577\" lng=\"-44.241128300000014\" id=\"-3\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.293435794884548\" lng=\"-53.85635327182672\" id=\"8762\" prefixo=\"cooperativa\"><![CDATA[Associação de Catadores de Palotina]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1448994\" lng=\"-51.10398559999999\" id=\"13186\" prefixo=\"cooperativa\"><![CDATA[Cooperativa Global Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8427433\" lng=\"-43.36183289999997\" id=\"-21\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.259732655700645\" lng=\"-48.11144828796387\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.40333154021413\" lng=\"-41.836323738098145\" id=\"3022\" prefixo=\"comercio\"><![CDATA[INTERSEA AMBIENTAL]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8015794\" lng=\"-43.41637709999998\" id=\"13137\" prefixo=\"associacao\"><![CDATA[Coomub (Núcleo Nilópolis)]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4711744\" lng=\"-44.263332\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1440688\" lng=\"-44.28495880000003\" id=\"14437\" prefixo=\"comercio\"><![CDATA[Carolina Carvalho Andrade ME]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3050364\" lng=\"-53.8189127\" id=\"15844\" prefixo=\"associacao\"><![CDATA[Eco Centro Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7221434\" lng=\"-46.61333530000002\" id=\"104\" prefixo=\"cooperativa\"><![CDATA[COOCARES - Cooperativa dos Catadores de Passos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7831815\" lng=\"-46.62615219999998\" id=\"-3\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.9466186\" lng=\"-50.5267706\" id=\"6825\" prefixo=\"comercio\"><![CDATA[Aparas Marília ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.933730536633046\" lng=\"-42.605581283569336\" id=\"5927\" prefixo=\"cooperativa\"><![CDATA[COOPER BELLA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.619356148816895\" lng=\"-49.089789390563965\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1575722\" lng=\"-49.9551221\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.5509539\" lng=\"-52.9932322\" id=\"11746\" prefixo=\"comercio\"><![CDATA[Sucatas Gaúcho]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7545902\" lng=\"-42.8825242\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.219175596746762\" lng=\"-51.30658149719238\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.6264186\" lng=\"-49.6619747\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1980999\" lng=\"-49.6678996\" id=\"1533\" prefixo=\"cooperativa\"><![CDATA[CooperGarça]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.911721\" lng=\"-46.96522170000003\" id=\"14654\" prefixo=\"associacao\"><![CDATA[ACOMARP]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.9437298\" lng=\"-50.5406233\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0840893\" lng=\"-44.9610465\" id=\"7542\" prefixo=\"comercio\"><![CDATA[RECICLAGEM AMATO]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0159156\" lng=\"-48.01384730000001\" id=\"15615\" prefixo=\"comercio\"><![CDATA[Gramfer]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.943637\" lng=\"-44.007678\" id=\"-11\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2714782\" lng=\"-43.9565992\" id=\"10308\" prefixo=\"cooperativa\"><![CDATA[COOMARB Cooperativa de Catadores de Materiais Recicláveis de Baldim ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.6977518\" lng=\"-50.03222089999997\" id=\"15688\" prefixo=\"associacao\"><![CDATA[Associação de Catadores de Nhandeara]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.2242514\" lng=\"-42.488849500000015\" id=\"16543\" prefixo=\"associacao\"><![CDATA[ASCABEO - galpão 1]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.96560445728776\" lng=\"-48.96881103515625\" id=\"12083\" prefixo=\"comercio\"><![CDATA[Reciclagem Pica Pau]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.877459\" lng=\"-48.881651499999975\" id=\"11381\" prefixo=\"cooperativa\"><![CDATA[Ascamam - Associação dos Catadores de Mat. Rec. de Monte Alegre de Minas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1701878\" lng=\"-43.3082756\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.9172128\" lng=\"-46.984144300000025\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.431296\" lng=\"-46.405658\" id=\"-14\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.323377\" lng=\"-50.62476\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7110228\" lng=\"-46.60902390000001\" id=\"-39\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.6066394\" lng=\"-50.6704033\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.2241912\" lng=\"-46.8824746\" id=\"-3\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.809102\" lng=\"-43.184719\" id=\"-7\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.73608\" lng=\"-53.24878\" id=\"8758\" prefixo=\"cooperativa\"><![CDATA[CooperUma - Cooperativa de Catadores de Umuarama]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.899181238447127\" lng=\"-49.352431297302246\" id=\"15828\" prefixo=\"triagem\"><![CDATA[Central de Triagem de Itapagipe]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.6373731\" lng=\"-45.4411363\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2118698\" lng=\"-40.2774336\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.24683\" lng=\"-43.803381\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.3759622\" lng=\"-40.07443219999999\" id=\"-25\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.293558\" lng=\"-51.150288\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6728294\" lng=\"-49.73127779999999\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.307468163014\" lng=\"-52.485836000000006\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1003875\" lng=\"-49.4385977\" id=\"13436\" prefixo=\"associacao\"><![CDATA[Associação dos Catadores de Presidente Alves]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2036881\" lng=\"-47.7682702\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3104218\" lng=\"-42.3141536\" id=\"17182\" prefixo=\"comercio\"><![CDATA[M & A Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.896504\" lng=\"-48.245160699999985\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9325925\" lng=\"-43.5748012\" id=\"-6\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4589572\" lng=\"-45.59871250000003\" id=\"13037\" prefixo=\"triagem\"><![CDATA[UTC Dores do Indaía]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.895004131030724\" lng=\"-47.610626220703125\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.585738\" lng=\"-48.4850363\" id=\"11971\" prefixo=\"cooperativa\"><![CDATA[Associação de Catadores de Campina do Monte Alegre]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.299170885362617\" lng=\"-47.51868009567261\" id=\"17015\" prefixo=\"triagem\"><![CDATA[Centro de Triagem de Santa Juliana]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7614558\" lng=\"-48.121234\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.759765663090505\" lng=\"-41.5222692489624\" id=\"-4\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.4971767\" lng=\"-46.9079126\" id=\"-9\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.5693\" lng=\"-54.0702\" id=\"8764\" prefixo=\"comercio\"><![CDATA[SRM Reciclados]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.793867694650462\" lng=\"-45.16449451446533\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.790959\" lng=\"-53.07443330000001\" id=\"2470\" prefixo=\"cooperativa\"><![CDATA[ARCO - Ass. dos Trab. de Materiais Recicláveis de Cruzeiro do Oeste]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.18564469624317\" lng=\"-40.224595069885254\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.3244066\" lng=\"-41.25137670000004\" id=\"7635\" prefixo=\"cooperativa\"><![CDATA[ASCAURES - Associação dos Catadores de Lixo Unidos de Resplendor]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9197778\" lng=\"-53.14430440000001\" id=\"12061\" prefixo=\"comercio\"><![CDATA[Comércio de Reciclagem de Loanda]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.18262107054239\" lng=\"-47.810348868370056\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.432188\" lng=\"-46.958467\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9550786\" lng=\"-43.5834535\" id=\"-8\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7628628\" lng=\"-47.9501482\" id=\"5751\" prefixo=\"pev\"><![CDATA[PEV - SUPERCENTER UBERABA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.92078229\" lng=\"-53.17355999\" id=\"16035\" prefixo=\"triagem\"><![CDATA[UT de Loanda]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.7335418\" lng=\"-46.8836215\" id=\"-10\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.2582749\" lng=\"-48.5959895\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5273924\" lng=\"-48.56418400000001\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.4118439\" lng=\"-40.54603020000002\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.774365824784635\" lng=\"-48.13290596008301\" id=\"-18\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.192299\" lng=\"-53.037403\" id=\"7835\" prefixo=\"cooperativa\"><![CDATA[Associação dos Coletores de Goioerê]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.8010488\" lng=\"-40.6439999\" id=\"10626\" prefixo=\"cooperativa\"><![CDATA[UNIPRAN - Associação da Unidade Primária de Materiais Recicláveis Nova Esperança]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5335271\" lng=\"-40.6739802\" id=\"9870\" prefixo=\"comercio\"><![CDATA[RT Empreendimentos e Serviços Ltda]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2421488\" lng=\"-44.986433\" id=\"-26\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.0322065\" lng=\"-45.5364139\" id=\"1858\" prefixo=\"cooperativa\"><![CDATA[ASCALP - Associação dos Catadores de Lagoa da Prata]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.589644969387418\" lng=\"-46.51615619659424\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.657816\" lng=\"-46.736334\" id=\"-271\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.710825424151103\" lng=\"-41.307992935180664\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.529824\" lng=\"-46.345434\" id=\"-9\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.0622799\" lng=\"-41.02129460000003\" id=\"16364\" prefixo=\"associacao\"><![CDATA[Associação dos Catadores de Materiais Reciclados de Alto Rio Novo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1566348\" lng=\"-44.9260194\" id=\"6607\" prefixo=\"cooperativa\"><![CDATA[Associação dos Catadores de Materiais Recicláveis de Ijaci - CAMARE]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2346669\" lng=\"-43.7035348\" id=\"6290\" prefixo=\"pev\"><![CDATA[Grupo Espírita Lar Meimei]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.124737042940794\" lng=\"-48.03201198577881\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7613674\" lng=\"-53.2955894\" id=\"5657\" prefixo=\"pev\"><![CDATA[PEV - MERCADORAMA UMUARAMA-LONDRINA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.0500807\" lng=\"-52.4003438\" id=\"2913\" prefixo=\"cooperativa\"><![CDATA[Associação dos Trab. de Mat. Rec. e Prest. de Serv. Vl Guarujá]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.737182\" lng=\"-47.980596\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.27821\" lng=\"-53.37325\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.8611613\" lng=\"-47.4592936\" id=\"11590\" prefixo=\"cooperativa\"><![CDATA[Associação de Catadores de Recicláveis de Sacramento]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.381631\" lng=\"-53.385897399999976\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.4201551\" lng=\"-51.9072995\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2408356\" lng=\"-50.8849588\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5264657\" lng=\"-54.04446870000004\" id=\"15757\" prefixo=\"comercio\"><![CDATA[MM Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.2527766\" lng=\"-50.801826500000004\" id=\"15584\" prefixo=\"comercio\"><![CDATA[Reciclagem do Alessandro]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7888484\" lng=\"-45.69893309999998\" id=\"3481\" prefixo=\"cooperativa\"><![CDATA[AMARE - Associação Machadense de Reciclagem de Resíduos Sólidos]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.422971\" lng=\"-45.460251099999994\" id=\"3802\" prefixo=\"cooperativa\"><![CDATA[ACIMAR - Associação de Catadores de Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.21111937323616\" lng=\"-40.26491403579712\" id=\"8684\" prefixo=\"pev\"><![CDATA[C&C - Filial Serra]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.1519364\" lng=\"-41.62321710000003\" id=\"13115\" prefixo=\"triagem\"><![CDATA[Usina de Triagem e Compostagem de Lajinha]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.9344014\" lng=\"-43.00877049999997\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.416667\" lng=\"-46.583333\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.805382\" lng=\"-46.51659129999996\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.51555\" lng=\"-47.406754\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.1625536\" lng=\"-49.3913622\" id=\"9679\" prefixo=\"cooperativa\"><![CDATA[ACLU - Associação dos Catadores de Piraju]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.952703\" lng=\"-44.188262699999996\" id=\"16568\" prefixo=\"triagem\"><![CDATA[Usina de Triagem e Compostagem de Bom Jardim de Minas ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.50693\" lng=\"-54.04923\" id=\"10536\" prefixo=\"cooperativa\"><![CDATA[Cooperagir - Triagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.618753668279037\" lng=\"-51.644852443370354\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2229445\" lng=\"-47.62322210000002\" id=\"16395\" prefixo=\"triagem\"><![CDATA[Central de Triagem de Corumbataí]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.4882068\" lng=\"-51.699825499999974\" id=\"13096\" prefixo=\"comercio\"><![CDATA[JB Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.8687436\" lng=\"-44.0599225\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1238619\" lng=\"-45.03871170000002\" id=\"15733\" prefixo=\"comercio\"><![CDATA[Farias Comércio de Sucatas Ltda ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.0661092\" lng=\"-44.2570518\" id=\"15272\" prefixo=\"triagem\"><![CDATA[UTC de São Joaquim de Bicas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.48803770494095\" lng=\"-51.71140193939209\" id=\"15693\" prefixo=\"associacao\"><![CDATA[Associação de Catadores de Ouro Verde]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.485655\" lng=\"-47.459578\" id=\"-18\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3515958\" lng=\"-45.781301600000006\" id=\"15619\" prefixo=\"associacao\"><![CDATA[ACLAMA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.3743539\" lng=\"-48.18249500000002\" id=\"-2\" prefixo=\"associacao\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.928644\" lng=\"-42.7010076\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.970993\" lng=\"-46.130279900000005\" id=\"15684\" prefixo=\"associacao\"><![CDATA[Recicarmo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.627712\" lng=\"-48.382954\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6290749\" lng=\"-43.8897467\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8695466\" lng=\"-43.011214300000006\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.3102284\" lng=\"-42.38082850000001\" id=\"16544\" prefixo=\"associacao\"><![CDATA[ASCABEO - galpão 2]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7459796\" lng=\"-48.91465\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.8804954\" lng=\"-48.010619\" id=\"10579\" prefixo=\"cooperativa\"><![CDATA[Cooper Arcanjo - Coop. dos Cat. de Mat. Rec. de São Miguel Arcanjo - SP]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.7332539\" lng=\"-47.551372500000014\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.1468971\" lng=\"-46.7252745\" id=\"6915\" prefixo=\"comercio\"><![CDATA[Ferro Velho da Marginal]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.2841798\" lng=\"-45.9692211\" id=\"-16\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.51013858741355\" lng=\"-44.12341117858887\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.874862654763845\" lng=\"-40.875535011291504\" id=\"16965\" prefixo=\"associacao\"><![CDATA[ACI - Associação de Catadores de Itarana]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.75011961\" lng=\"-53.80482011\" id=\"10537\" prefixo=\"cooperativa\"><![CDATA[Associação Catadores de Toledo]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.3723006\" lng=\"-42.544626100000016\" id=\"16462\" prefixo=\"pev\"><![CDATA[CIEP 353 - Brochado da Rocha]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7325981\" lng=\"-47.9776202\" id=\"3457\" prefixo=\"cooperativa\"><![CDATA[COOPERU - Coop. R.A.de Res.Soli. e Mat. Rec de Uberaba]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.4734606\" lng=\"-45.417364599999985\" id=\"13743\" prefixo=\"comercio\"><![CDATA[Sucata Formiguense]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.001297807570825\" lng=\"-40.74196100234985\" id=\"7331\" prefixo=\"comercio\"><![CDATA[RECICLAGEM GUMS]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.220072279586766\" lng=\"-40.85721015930176\" id=\"12284\" prefixo=\"cooperativa\"><![CDATA[ASEMAP - Associação dos Empreendedores Ambientais de Pancas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.252484\" lng=\"-53.33765410000001\" id=\"14818\" prefixo=\"cooperativa\"><![CDATA[Corena]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4198762\" lng=\"-45.4756089\" id=\"6426\" prefixo=\"comercio\"><![CDATA[Erika Comércio de Reciclagem]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.2886137\" lng=\"-51.9057945\" id=\"10913\" prefixo=\"comercio\"><![CDATA[Ferro Velho Mirante]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.2592578\" lng=\"-51.1590237\" id=\"-10\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.7479907\" lng=\"-40.045238900000015\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.244655891925266\" lng=\"-51.6822624206543\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.11579622534542\" lng=\"-48.90040397644043\" id=\"-6\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6368446\" lng=\"-41.048442799999975\" id=\"15490\" prefixo=\"triagem\"><![CDATA[Coop. de Trab. Catadores, Triadores e Recicladores de SJB ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1977712\" lng=\"-47.8335698\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.17543763489611\" lng=\"-50.628604888916016\" id=\"-5\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.931580835370344\" lng=\"-47.61405944824219\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.893251\" lng=\"-43.0691\" id=\"-9\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.4293924\" lng=\"-46.9266506\" id=\"-17\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6197761\" lng=\"-43.2244867\" id=\"69\" prefixo=\"cooperativa\"><![CDATA[Itaurb - Empresa de Desenvolvimento de itabira Ltda ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.5376065\" lng=\"-40.7871631\" id=\"-2\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-18.9316287\" lng=\"-48.2855348\" id=\"-11\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8460286\" lng=\"-42.2362583\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7978954\" lng=\"-49.3937353\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.6517108\" lng=\"-42.856595700000014\" id=\"12090\" prefixo=\"cooperativa\"><![CDATA[UTC Teixeiras - Biokratos Soluções Ambientais]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7891247\" lng=\"-49.8166311\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5993748\" lng=\"-46.91161090000003\" id=\"15436\" prefixo=\"associacao\"><![CDATA[Ascarpac - Associação De Catadores e Preservadores Ambientais de Cassia]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.542750998636\" lng=\"-51.3775634765625\" id=\"12071\" prefixo=\"cooperativa\"><![CDATA[Associação de Cândido de Abreu]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.965441\" lng=\"-50.104161999999974\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.014936\" lng=\"-45.181124\" id=\"3531\" prefixo=\"pev\"><![CDATA[Escola Municipal Waldivino José Freire]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.5688277\" lng=\"-48.0302413\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6110489\" lng=\"-48.3559566\" id=\"-8\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.7054444\" lng=\"-47.901593\" id=\"4542\" prefixo=\"pev\"><![CDATA[Fac - Fraterno Auxilio Cristão]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.544622\" lng=\"-41.9809004\" id=\"-4\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.6201095\" lng=\"-44.04376780000001\" id=\"12116\" prefixo=\"comercio\"><![CDATA[Destinadora de Resíduos Nascimento Ltda ]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.6955133\" lng=\"-47.6785147\" id=\"2394\" prefixo=\"cooperativa\"><![CDATA[Cooperativa Reciclador Solidário]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.1265707\" lng=\"-48.9747618\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.0978104\" lng=\"-42.1853868\" id=\"13920\" prefixo=\"triagem\"><![CDATA[UTC de Eugenópolis]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.904651\" lng=\"-43.09859\" id=\"-191\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.0716717\" lng=\"-45.721639100000004\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.3599045\" lng=\"-43.04468910000003\" id=\"-2\" prefixo=\"triagem\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.524469\" lng=\"-42.63382009999998\" id=\"15431\" prefixo=\"associacao\"><![CDATA[ASCAMAREN]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.9545093\" lng=\"-53.4529111\" id=\"-13\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.485278498549853\" lng=\"-44.54625606536865\" id=\"-3\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.42237615\" lng=\"-49.749939110000014\" id=\"17235\" prefixo=\"triagem\"><![CDATA[Usina de Reciclagem VMA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.416667\" lng=\"-46.583333\" id=\"-4\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.5393081\" lng=\"-47.4168212\" id=\"-4\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.2916747\" lng=\"-47.6757272\" id=\"-12\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3863531\" lng=\"-45.5739659\" id=\"3470\" prefixo=\"cooperativa\"><![CDATA[ACAP - Associação Catadores Amigos de Pains]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.6869442\" lng=\"-51.0959523\" id=\"-3\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.2136477\" lng=\"-43.7557084\" id=\"14550\" prefixo=\"pev\"><![CDATA[Sérgio Carlos Cândido]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.200625527487283\" lng=\"-45.88032245635986\" id=\"-17\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.67156851201159\" lng=\"-49.814372062683105\" id=\"-2\" prefixo=\"comercio\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.8481435\" lng=\"-43.8075982\" id=\"11721\" prefixo=\"pev\"><![CDATA[PEV IBITIPOCA]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7212239\" lng=\"-52.42587750000001\" id=\"16502\" prefixo=\"comercio\"><![CDATA[Nardo Sucatas]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.7155474\" lng=\"-49.4909736\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.1036461\" lng=\"-49.9405435\" id=\"-3\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-21.7803692\" lng=\"-52.1384356\" id=\"-2\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-23.553275213898527\" lng=\"-51.47689846688312\" id=\"-10\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.1825591\" lng=\"-47.3977345\" id=\"-7\" prefixo=\"cooperativa\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-24.4819265\" lng=\"-47.8328966\" id=\"7014\" prefixo=\"comercio\"><![CDATA[RM - Reciclagem Moacir]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-19.973636\" lng=\"-44.019104\" id=\"-169\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-20.3184974\" lng=\"-40.3229718\" id=\"-3\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <marker lat=\"-22.8605411\" lng=\"-42.0539834\" id=\"-21\" prefixo=\"pev\"><![CDATA[]]></marker>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</markers>\n" +
                "\n");
        XMLMapMarkerParser xmlMapMarkerParser = new XMLMapMarkerParser();
        try {
            items = xmlMapMarkerParser.parse(sr);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (items != null)
            for (PontoDeTroca item : items) {
                System.out.println(item.toString());
            }
        return items;
    }

    public List<PontoDeTroca> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            if (in == null)
                throw new IOException("InputStream must have a value of InputStream valid!");
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readMarkers(parser);
        } finally {
            if (in != null)
                in.close();
        }
    }

    public List<PontoDeTroca> parse(Reader in) throws XmlPullParserException, IOException {
        try {
            if (in == null)
                throw new IOException("Reader must have a value of Reader valid!");
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in);
            parser.nextTag();
            return readMarkers(parser);
        } finally {
            if (in != null)
                in.close();
        }
    }

    private List<PontoDeTroca> readMarkers(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<PontoDeTroca> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "markers");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the markers tag
            // <markers>
            if (name.equals("marker")) {
                entries.add(readMarker(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
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

//    // For the tags title and summary, extracts their text values.
//    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String result = "";
//        if (parser.next() == XmlPullParser.TEXT) {
//            result = parser.getText();
//            parser.nextTag();
//        }
//        return result;
//    }

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

    // Parses the contents of an PontoDeTroca. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private PontoDeTroca readMarker(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "marker");

        double latitude = 0, longitude = 0;
        int id = 0;
        String prefixo = "";
        String cData = "";

        if (parser.getEventType() == XmlPullParser.START_TAG) {

            for (int i = 0; i < parser.getAttributeCount(); i++) {
                switch (parser.getAttributeName(i)) {
                    case "lat":
                        latitude = Double.parseDouble(parser.getAttributeValue(i));
                        break;
                    case "lng":
                        longitude = Double.parseDouble(parser.getAttributeValue(i));
                        break;
                    case "id":
                        id = Integer.parseInt(parser.getAttributeValue(i));
                        break;
                    case "prefixo":
                        prefixo = parser.getAttributeValue(i);
                        break;
                    default:
                        cData = "";
                        break;
                }
//                if (parser.getAttributeName(i).equals("lat")) {
//                    latitude = Double.parseDouble(parser.getAttributeValue(i));
//                } else if (parser.getAttributeName(i).equals("lng")) {
//                    longitude = Double.parseDouble(parser.getAttributeValue(i));
//                } else if (parser.getAttributeName(i).equals("id")) {
//                    id = Integer.parseInt(parser.getAttributeValue(i));
//                } else if (parser.getAttributeName(i).equals("prefixo")) {
//                    prefixo = parser.getAttributeValue(i);
//                }
            }
        }
        while (parser.nextToken() != XmlPullParser.END_TAG) {
            // Read the data from attributes
            // <marker lat="-20.5504415" lng="-47.4095209" id="5731" prefixo="pev">
            // <![CDATA[ PEV - SUPERCENTER FRANCA ]]>
            // </marker>
            if (parser.getEventType() == XmlPullParser.CDSECT) {
                cData = parser.getText();
            }

        }
        return new PontoDeTroca(latitude, longitude, id, prefixo, cData, (id < 0) ? R.drawable.marcadorpadraoplus : R.drawable.marcadorpadrao);
    }
}
