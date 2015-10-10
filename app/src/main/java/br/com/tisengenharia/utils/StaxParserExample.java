package br.com.tisengenharia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

//import javax.swing.text.NumberFormatter;
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamConstants;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.XMLStreamReader;

public class StaxParserExample {
//	public static void main(String[] args) throws FileNotFoundException,
//			XMLStreamException {
//
//		// if (args.length != 1)
//		// throw new RuntimeException("The name of the XML file is required!");
//
//		List<PontoDeTroca> markers = null;
//		PontoDeTroca pontoDeTroca = null;
//		//String text = null;
//
//		XMLInputFactory factory = XMLInputFactory.newInstance();
//
//		File f = new File("C:\\Projetos\\teste\\LeitorXML\\exemplo.xml");
//		// System.out.println( f.getAbsoluteFile());
//		// new Scanner(System.in).nextLine();
//		XMLStreamReader reader = factory
//				.createXMLStreamReader(new FileInputStream(f));
//		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
//
//		while (reader.hasNext()) {
//			int Event = reader.next();
//
//			switch (Event) {
//			case XMLStreamConstants.START_ELEMENT: {
//				if ("marker".equals(reader.getLocalName())) {
//					pontoDeTroca = new PontoDeTroca();
//					try {
//						pontoDeTroca.setmPosition(
//								new LatLng( nf.parse(reader.getAttributeValue(0)).doubleValue(),
//										    nf.parse(reader.getAttributeValue(1)).doubleValue())
//
//								);
//					} catch (ParseException e) {
//						pontoDeTroca.setmPosition(new LatLng(0, 0));
//						e.printStackTrace();
//					}
//					pontoDeTroca.setId(Integer.parseInt(reader.getAttributeValue(2).toString()));
//					pontoDeTroca.setPrefixo(reader.getAttributeValue(3).toString());
//					//pontoDeTroca.setcData(text);
//					//markers.add(pontoDeTroca);
//
//				}
//
//				if ("markers".equals(reader.getLocalName()))
//					markers = new ArrayList<PontoDeTroca>();
//
//				break;
//			}
//			case XMLStreamConstants.CHARACTERS: {
//				if(pontoDeTroca!=null)
//					pontoDeTroca.setcData(pontoDeTroca.getcData()+reader.getText().trim());
//				break;
//			}
//			case XMLStreamConstants.END_ELEMENT: {
//				 if(pontoDeTroca!=null){
//					 markers.add(pontoDeTroca);
//					 pontoDeTroca = null;
//				 }
//
//				//markers.add(pontoDeTroca);
//				// switch (reader.getLocalName()) {
//				// case "Employee":
//				// markers.add(pontoDeTroca);
//				// break;
//				// case "Firstname":
//				// pontoDeTroca.setcData(text);
//				// break;
//				// case "Lastname":
//				// pontoDeTroca.setLastname(text);
//				// break;
//				// case "Age":
//				// pontoDeTroca.setAge(Integer.parseInt(text));
//				// break;
//				// case "Salary": {
//				// pontoDeTroca.setSalary(Double.parseDouble(text));
//				// break;
//				// }
//				// }
//				// break;
//			}
//			}
//		}
//
//		// Print all employees.
//		for (PontoDeTroca employee : markers)
//			System.out.println(employee);
//	}
}
