package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.ConfiguratorImageCache;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DXHttpConfig implements IDXTreeItem {
    public static final String ELEMENT_TAG_NAME = "Apis";

    public final SimpleMapProperty<String, DXHttpConfigApi> HttpApis;

    public DXHttpConfig(final Map<String, DXHttpConfigApi> httpApis) {
        HttpApis = new SimpleMapProperty<>(null, "HTTP APIs", FXCollections.observableMap(httpApis));
    }

    public DXHttpConfig() {
        this(new HashMap<>());
    }

    public Map<String, DXHttpConfigApi> httpApis() {
        return HttpApis.getValue();
    }

    public SimpleMapProperty<String, DXHttpConfigApi> httpApisProperty() {
        return HttpApis;
    }

    public DXHttpConfigApi GetApiByName(final String name) {
        return HttpApis.get(name);
    }

    public static DXHttpConfig FromElement(final Element element) {
        final var httpConfig = new DXHttpConfig();

        for (var i = 0; i < element.getChildNodes().getLength(); ++i) {
            final var childNode = element.getChildNodes().item(i);
            if (childNode instanceof Element childElement)
            {
                final DXHttpConfigApi api = DXHttpConfigApi.FromElement(childElement, httpConfig);
                if (httpConfig.httpApis().containsKey(api.Name.getValue())) throw new RuntimeException("Duplicate API name: " + api.Name.getValue());
                httpConfig.httpApis().put(api.Name.getValue(), api);
            }
        }

        return httpConfig;
    }

    public static DXHttpConfig ReadFromFile(final File file) throws IOException {
        try (var inputStream = new FileInputStream(file)) {
            final var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final var documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final var document = documentBuilder.parse(inputStream);

            final var documentElement = document.getDocumentElement();
            if (!documentElement.getTagName().equals(ELEMENT_TAG_NAME))
                throw new RuntimeException("Document tag name mismatch");

            return DXHttpConfig.FromElement(documentElement);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/manufacturing_FILL0_wght400_GRAD0_opsz24.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return new SimpleStringProperty(null, null, "Configuratie");
    }
}
