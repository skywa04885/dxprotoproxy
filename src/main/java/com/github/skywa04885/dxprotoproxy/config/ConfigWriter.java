package com.github.skywa04885.dxprotoproxy.config;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ConfigWriter {
    public static void write(ConfigRoot configRoot, File file) throws ParserConfigurationException,
            TransformerException, IOException {
        try (final var outputStream = new FileOutputStream(file)) {
            final var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final var documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final var document = documentBuilder.newDocument();

            document.appendChild(configRoot.toElement(document));

            final var transformerFactory = TransformerFactory.newInstance();
            final var transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            final var domSource = new DOMSource(document);
            final var streamResult = new StreamResult(outputStream);

            transformer.transform(domSource, streamResult);
        }
    }
}
