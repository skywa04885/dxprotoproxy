package com.github.skywa04885.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.configurator.http.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.http.DXHttpRequestMethod;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigEndpoint implements IDXTreeItem {
    public static final String TAG_NAME = "Endpoint";
    public static final String NAME_ATTRIBUTE_NAME = "Name";

    private @Nullable HttpConfigEndpoints parent;
    public @NotNull SimpleStringProperty name;
    public @NotNull SimpleMapProperty<DXHttpRequestMethod, DXHttpConfigRequest> requests;

    public DXHttpConfigEndpoint(@NotNull String name, @NotNull Map<DXHttpRequestMethod, DXHttpConfigRequest> requests) {
        this.name = new SimpleStringProperty(null, "Naam", name);
        this.requests = new SimpleMapProperty<>(null, "Verzoeken", FXCollections.observableMap(requests));
    }

    public DXHttpConfigEndpoint(@NotNull String name) {
        this(name, new HashMap<>());
    }

    public @Nullable HttpConfigEndpoints parent() {
        return parent;
    }

    public void setParent(@Nullable HttpConfigEndpoints parent) {
        this.parent = parent;
    }

    @NotNull
    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name.setValue(name);
    }

    @NotNull
    public SimpleMapProperty<DXHttpRequestMethod, DXHttpConfigRequest> requestsProperty() {
        return requests;
    }

    @NotNull
    public Map<DXHttpRequestMethod, DXHttpConfigRequest> requests()
    {
        return requests.getValue();
    }

    @NotNull
    public String name() {
        return name.getValue();
    }

    @NotNull
    public SimpleStringProperty nameProperty() {
        return name;
    }

    @NotNull
    public Map<DXHttpRequestMethod, DXHttpConfigRequest> getRequests() {
        return requests;
    }

    public @Nullable DXHttpConfigRequest getRequestByMethod(DXHttpRequestMethod method) {
        return requests().get(method);
    }

    public static DXHttpConfigEndpoint FromElement(@NotNull Element element) {
        if (!element.getTagName().equals(TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) throw new RuntimeException("Name attribute missing");

        final var requestElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigRequest.ELEMENT_TAG_NAME);

        final var configEndpoint = new DXHttpConfigEndpoint(name);

        for (final Element value : requestElements) {
            final var request = DXHttpConfigRequest.FromElement(value);

            request.setParent(configEndpoint);

            if (configEndpoint.requests().containsKey(request.method()))
                throw new RuntimeException("Two requests with same method");

            configEndpoint.requests().put(request.method(), request);
        }

        return configEndpoint;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.setAttribute(NAME_ATTRIBUTE_NAME, name());

        requests().values().forEach(httpConfigRequest ->
                element.appendChild(httpConfigRequest.toElement(document)));

        return element;
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
