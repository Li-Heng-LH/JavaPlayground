package me.liheng.TrySax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class PomXMLHandler extends DefaultHandler {

    private int dependencyCount = 0;
    private StringBuilder currentText;

    public void readDataFromPomXML(String path) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File(path), this);

        System.out.println(String.format("There are %s dependencies.", dependencyCount));
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Starting to traverse document");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End of document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Start element: " + qName);
        currentText = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("End element: " + qName);
        if (qName.equals("dependency")) {
            dependencyCount ++ ;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentText != null) {
            currentText.append(ch, start, length);
        }
        System.out.println("Chars: " + currentText);
    }
}
