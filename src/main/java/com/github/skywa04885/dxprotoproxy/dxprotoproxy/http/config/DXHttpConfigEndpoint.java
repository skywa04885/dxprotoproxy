package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigEndpoint implements IDXTreeItem {
    public static final String TAG_NAME = "Endpoint";
    public static final String NAME_ATTRIBUTE_NAME = "Name";

    private @NotNull DXHttpConfigApi parent;
    public @NotNull SimpleStringProperty name;
    public @NotNull SimpleMapProperty<DXHttpRequestMethod, DXHttpConfigRequest> requests;

    public DXHttpConfigEndpoint(@NotNull DXHttpConfigApi parent, @NotNull String name,
                                @NotNull Map<DXHttpRequestMethod, DXHttpConfigRequest> requests) {
        this.parent = parent;
        this.name = new SimpleStringProperty(null, "Naam", name);
        this.requests = new SimpleMapProperty<>(null, "Verzoeken", FXCollections.observableMap(requests));
    }

    public DXHttpConfigEndpoint(@NotNull DXHttpConfigApi parent, @NotNull String name) {
        this(parent, name, new HashMap<>());
    }

    public @NotNull DXHttpConfigApi parent() {
        return parent;
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

    @NotNull
    public DXHttpConfigRequest GetRequestByMethod(DXHttpRequestMethod method) {
        return requests.get(method);
    }

    public static DXHttpConfigEndpoint FromElement(@NotNull DXHttpConfigApi parent, @NotNull Element element) {
        if (!element.getTagName().equals(TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) throw new RuntimeException("Name attribute missing");

        final var requestElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigRequest.ELEMENT_TAG_NAME);

        final var configEndpoint = new DXHttpConfigEndpoint(parent, name);

        for (final Element value : requestElements) {
            final var request = DXHttpConfigRequest.FromElement(configEndpoint, value);

            if (configEndpoint.requests().containsKey(request.method()))
                throw new RuntimeException("Two requests with same method");

            configEndpoint.requests().put(request.method(), request);
        }

        return configEndpoint;
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
