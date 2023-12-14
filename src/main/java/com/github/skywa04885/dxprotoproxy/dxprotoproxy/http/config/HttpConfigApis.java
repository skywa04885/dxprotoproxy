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

public class HttpConfigApis implements IDXTreeItem {
    public static final String TAG_NAME = "Apis";

    private @Nullable DXHttpConfig parent;
    private final @NotNull SimpleMapProperty<String, DXHttpConfigApi> children;

    public HttpConfigApis(@NotNull Map<String, DXHttpConfigApi> children) {
        this.children = new SimpleMapProperty<>(null, "children",
                FXCollections.observableMap(children));
    }

    public HttpConfigApis() {
        this(new HashMap<>());
    }

    public @Nullable DXHttpConfig parent() {
        return parent;
    }

    public void setParent(@Nullable DXHttpConfig parent) {
        this.parent = parent;
    }

    public @NotNull Map<String, DXHttpConfigApi> children() {
        return children.get();
    }

    public @NotNull SimpleMapProperty<String, DXHttpConfigApi> childrenProperty() {
        return children;
    }

    /**
     * Attempts to get an API that has the given name.
     *
     * @param name the name of the API.
     * @return the API or null if it does not exist.
     */
    public @Nullable DXHttpConfigApi getChildByName(final @NotNull String name) {
        return children().get(name);
    }

    public static @NotNull HttpConfigApis fromElement(@NotNull Element element) {
        if (!element.getTagName().equals(TAG_NAME)) {
            throw new RuntimeException("Tag name mismatch, expected " + TAG_NAME + " got " + element.getTagName());
        }

        final var httpApiElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigApi.TAG_NAME);

        final var children = new HashMap<String, DXHttpConfigApi>();

        httpApiElements.forEach(httpApiElement -> {
            final var httpConfigApi = DXHttpConfigApi.FromElement(httpApiElement);

            if (children.containsKey(httpConfigApi.name())) {
                throw new RuntimeException("Duplicate api name");
            }

            children.put(httpConfigApi.name(), httpConfigApi);
        });

        final var httpConfigApis = new HttpConfigApis(children);

        httpConfigApis.children().values().forEach(httpConfigApi ->
                httpConfigApi.setParent(httpConfigApis));

        return httpConfigApis;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        children().values().forEach(httpConfigApi ->
                element.appendChild(httpConfigApi.toElement(document)));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return null;
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> "Apis");
    }
}
