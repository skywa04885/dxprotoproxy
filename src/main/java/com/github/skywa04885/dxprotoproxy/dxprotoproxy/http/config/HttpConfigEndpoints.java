package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class HttpConfigEndpoints implements IDXTreeItem {
    public static final String TAG_NAME = "Endpoints";

    private @Nullable DXHttpConfigApi parent;
    private final @NotNull SimpleMapProperty<String, DXHttpConfigEndpoint> children;

    public HttpConfigEndpoints(Map<String, DXHttpConfigEndpoint> children) {
        this.children = new SimpleMapProperty<>(null, "children",
                FXCollections.observableMap(children));
    }

    public HttpConfigEndpoints() {
        this(new HashMap<>());
    }

    public @Nullable DXHttpConfigApi parent() {
        return parent;
    }

    public void setParent(@Nullable DXHttpConfigApi parent) {
        this.parent = parent;
    }

    public @NotNull Map<String, DXHttpConfigEndpoint> children() {
        return children.get();
    }

    public @NotNull SimpleMapProperty<String, DXHttpConfigEndpoint> childrenProperty() {
        return children;
    }

    public static @NotNull HttpConfigEndpoints fromElement(@NotNull Element element) {
        final var children = new HashMap<String, DXHttpConfigEndpoint>();

        final var endpointElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigEndpoint.TAG_NAME);

        endpointElements.forEach(endpointElement -> {
            final var endpoint = DXHttpConfigEndpoint.FromElement(endpointElement);

            if (children.containsKey(endpoint.name())) {
                throw new RuntimeException("Duplicate endpoint name");
            }

            children.put(endpoint.name(), endpoint);
        });

        final var httpConfigEndpoints = new HttpConfigEndpoints(children);

        httpConfigEndpoints.children().values().forEach(httpConfigEndpoint ->
                httpConfigEndpoint.setParent(httpConfigEndpoints));

        return httpConfigEndpoints;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        children().values().forEach(httpConfigEndpoint -> element.appendChild(httpConfigEndpoint.toElement(document)));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return null;
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> "Endpoints");
    }
}
