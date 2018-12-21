package br.com.file;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.configuration.SocketSetting;

public class FileReader {

    /**
     * Le o arquivo de configuracao XML
     *
     * @param file
     * @return SocketSetting
     */
    public static SocketSetting readXMLConfiguration(File file) {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("server");

            Node nNode = nList.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String host = eElement.getElementsByTagName("host").item(0).getTextContent();
                String port = eElement.getElementsByTagName("port").item(0).getTextContent();
                String id = eElement.getElementsByTagName("id").item(0).getTextContent();

                return new SocketSetting(host, Integer.parseInt(port), Integer.parseInt(id));

            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
