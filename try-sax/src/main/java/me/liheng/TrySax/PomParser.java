package me.liheng.TrySax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class PomParser {

    public static void main( String[] args ) throws ParserConfigurationException, SAXException, IOException {
        PomXMLHandler pomXMLHandler = new PomXMLHandler();
        pomXMLHandler.readDataFromPomXML("example-xml" + File.separator + "spring-boot-2.2.0.RELEASE.pom");
    }
}
