package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigEndpoint implements IDXTreeItem {
    public static final String TAG_NAME = "Endpoint";
    public static final String NAME_ATTRIBUTE_NAME = "Name";

    public SimpleStringProperty name;
    public SimpleMapProperty<DXHttpRequestMethod, DXHttpConfigRequest> requests;

    public DXHttpConfigEndpoint(final String name, final Map<DXHttpRequestMethod, DXHttpConfigRequest> requests) {
        this.name = new SimpleStringProperty(null, "Naam", name);
        this.requests = new SimpleMapProperty<>(null, "Verzoeken", FXCollections.observableMap(requests));
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public SimpleMapProperty<DXHttpRequestMethod, DXHttpConfigRequest> requestsProperty() {
        return requests;
    }

    public Map<DXHttpRequestMethod, DXHttpConfigRequest> requests()
    {
        return requests.getValue();
    }

    public String name() {
        return name.getValue();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public Map<DXHttpRequestMethod, DXHttpConfigRequest> getRequests() {
        return requests;
    }

    public DXHttpConfigRequest GetRequestByMethod(final DXHttpRequestMethod method) {
        return requests.get(method);
    }

    public static DXHttpConfigEndpoint FromElement(final Element element) {
        if (!element.getTagName().equals(TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) throw new RuntimeException("Name attribute missing");

        final var requestElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigRequest.ELEMENT_TAG_NAME);

        final var requests = new HashMap<DXHttpRequestMethod, DXHttpConfigRequest>();

        for (final Element value : requestElements) {
            final var request = DXHttpConfigRequest.FromElement(value);

            if (requests.containsKey(request.method()))
                throw new RuntimeException("Two requests with same method");

            requests.put(request.method(), request);
        }

        return new DXHttpConfigEndpoint(name, requests);
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/globe_FILL0_wght400_GRAD0_opsz24.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(this::name, nameProperty());
    }
}
