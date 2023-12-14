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

public class HttpConfigInstances implements IDXTreeItem {
    public static final String TAG_NAME = "Instances";

    private @Nullable DXHttpConfigApi parent;
    private final @NotNull SimpleMapProperty<String, DXHttpConfigInstance> children;

    public HttpConfigInstances(Map<String, DXHttpConfigInstance> children) {
        this.children = new SimpleMapProperty<>(null, "children", FXCollections.observableMap(children));
    }

    public HttpConfigInstances() {
        this(new HashMap<>());
    }

    public @Nullable DXHttpConfigApi parent() {
        return parent;
    }

    public void setParent(@Nullable DXHttpConfigApi parent) {
        this.parent = parent;
    }

    public @NotNull Map<String, DXHttpConfigInstance> children() {
        return children.get();
    }

    public @NotNull SimpleMapProperty<String, DXHttpConfigInstance> childrenProperty() {
        return children;
    }

    public @Nullable DXHttpConfigInstance getChildByName(final @NotNull String name) {
        return children().get(name);
    }

    public static @NotNull HttpConfigInstances fromElement(@NotNull Element element) {
        final var configInstances = new HttpConfigInstances();

        final var instanceElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigInstance.TAG_NAME);

        instanceElements.forEach(instanceElement -> {
            final var instance = DXHttpConfigInstance.FromElement(instanceElement);

            if (configInstances.children().containsKey(instance.name())) {
                throw new RuntimeException("Duplicate instance name");
            }

            configInstances.children().put(instance.name(), instance);
        });

        configInstances.children().values().forEach(configInstance -> {
            configInstance.setParent(configInstances);
        });

        return configInstances;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        children().values().forEach(configInstance ->
                element.appendChild(configInstance.toElement(document)));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return null;
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> "Instances");
    }
}
