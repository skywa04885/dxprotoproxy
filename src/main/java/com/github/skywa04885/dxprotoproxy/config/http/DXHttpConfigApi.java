package com.github.skywa04885.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigApi implements IDXTreeItem {
    public static final String TAG_NAME = "Api";
    public static final String NAME_ATTRIBUTE = "Name";
    public static final String HTTP_VERSION_ATTRIBUTE = "HttpVersion";

    private @Nullable HttpConfigApis parent;
    public final @NotNull SimpleStringProperty Name;
    public final @NotNull SimpleStringProperty HttpVersion;
    public final @NotNull HttpConfigInstances httpConfigInstances;
    public final @NotNull HttpConfigEndpoints httpConfigEndpoints;

    public DXHttpConfigApi(@NotNull String name, @NotNull String httpVersion,
                           @NotNull HttpConfigInstances httpConfigInstances,
                           @NotNull HttpConfigEndpoints httpConfigEndpoints) {
        Name = new SimpleStringProperty(null, "Naam", name);
        HttpVersion = new SimpleStringProperty(null, "HTTP Versie", httpVersion);
        this.httpConfigInstances = httpConfigInstances;
        this.httpConfigEndpoints = httpConfigEndpoints;
    }

    public DXHttpConfigApi(@NotNull String name, @NotNull String httpVersion) {
        this(name, httpVersion, new HttpConfigInstances(), new HttpConfigEndpoints());
    }

    public HttpConfigInstances instances() {
        return httpConfigInstances;
    }

    public HttpConfigEndpoints endpoints() {
        return httpConfigEndpoints;
    }

    public @Nullable HttpConfigApis parent() {
        return parent;
    }

    public void setParent(@Nullable HttpConfigApis parent) {
        this.parent = parent;
    }

    public String name() {
        return Name.getValue();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public String httpVersion() {
        return HttpVersion.getValue();
    }

    public SimpleStringProperty httpVersionProperty() {
        return HttpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.HttpVersion.set(httpVersion);
    }

    public ObservableStringValue getName() {
        return Name;
    }

    public void setName(String name) {
        Name.setValue(name);
    }

    private static HttpConfigInstances httpConfigInstancesFromElement(@NotNull Element element) {
        final var instancesElements = DXDomUtils.GetChildElementsWithTagName(element, HttpConfigInstances.TAG_NAME);

        if (instancesElements.isEmpty())
            throw new RuntimeException("Instances element is missing");
        else if (instancesElements.size() > 1)
            throw new RuntimeException("Too many instances elements");

        final var instancesElement = (Element) instancesElements.get(0);

        return HttpConfigInstances.fromElement(instancesElement);
    }

    private static HttpConfigEndpoints httpConfigEndpointsFromElement(@NotNull Element element) {
        final var endpointsElements = DXDomUtils.GetChildElementsWithTagName(element, HttpConfigEndpoints.TAG_NAME);

        if (endpointsElements.isEmpty()) {
            throw new RuntimeException("Endpoints element is missing");
        } else if (endpointsElements.size() > 1) {
            throw new RuntimeException("Too many endpoints elements");
        }

        final var endpointsElement = (Element) endpointsElements.get(0);

        return HttpConfigEndpoints.fromElement(endpointsElement);
    }

    public static @NotNull DXHttpConfigApi FromElement(@NotNull Element element) {
        final var name = element.getAttribute(NAME_ATTRIBUTE);
        final var httpVersion = element.getAttribute(HTTP_VERSION_ATTRIBUTE);

        final HttpConfigInstances instances = httpConfigInstancesFromElement(element);
        final HttpConfigEndpoints endpoints = httpConfigEndpointsFromElement(element);

        final DXHttpConfigApi httpConfigApi = new DXHttpConfigApi(name, httpVersion, instances, endpoints);

        instances.setParent(httpConfigApi);
        endpoints.setParent(httpConfigApi);

        return httpConfigApi;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.setAttribute(NAME_ATTRIBUTE, name());
        element.setAttribute(HTTP_VERSION_ATTRIBUTE, httpVersion());

        element.appendChild(instances().toElement(document));
        element.appendChild(endpoints().toElement(document));

        return element;
    }

    @Override
    public String toString() {
        return "Name: " + this.Name.getValue() + ", HTTP Version: " + this.HttpVersion.getValue()
                + ", Number of instances: " + this.instances().children().size()
                + ", Number of endpoints: " + this.endpoints().children().size();
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/network_node_FILL0_wght400_GRAD0_opsz24.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> name() + " - " + httpVersion(), nameProperty(), httpVersionProperty());
    }
}
