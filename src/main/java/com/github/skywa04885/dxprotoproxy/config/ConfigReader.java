package com.github.skywa04885.dxprotoproxy.config;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigReader {
    public static ConfigRoot read(File file) throws IOException, ParserConfigurationException, SAXException {
        try (final var inputStream = new FileInputStream(file)) {
            final var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final var documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final var document = documentBuilder.parse(inputStream);

            final var documentElement = document.getDocumentElement();

            return ConfigRoot.fromElement(documentElement);
        }
    }
}
