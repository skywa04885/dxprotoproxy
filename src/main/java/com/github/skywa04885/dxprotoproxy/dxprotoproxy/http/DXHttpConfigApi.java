package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigApi implements IDXTreeItem {
    public static final String NAME_ATTRIBUTE = "Name";
    public static final String HTTP_VERSION_ATTRIBUTE = "HttpVersion";
    public static final String INSTANCES_ELEMENT_TAG_NAME = "Instances";
    public static final String ENDPOINTS_ELEMENT_TAG_NAME = "Endpoints";

    public final SimpleStringProperty Name;
    public final SimpleStringProperty HttpVersion;
    public final MapProperty<String, DXHttpConfigInstance> Instances;
    public final MapProperty<String, DXHttpConfigEndpoint> Endpoints;
    private final DXHttpConfig parent;

    public DXHttpConfigApi(String name, final String httpVersion,
                           Map<String, DXHttpConfigInstance> instances,
                           Map<String, DXHttpConfigEndpoint> endpoints,
                           DXHttpConfig parent) {
        Name = new SimpleStringProperty(null, "Naam", name);
        HttpVersion = new SimpleStringProperty(null, "HTTP Versie", httpVersion);
        Instances = new SimpleMapProperty<>(null, "Instanties", FXCollections.observableMap(instances));
        Endpoints = new SimpleMapProperty<>(null, "Eindpunten", FXCollections.observableMap(endpoints));
        this.parent = parent;
    }

    public Map<String, DXHttpConfigInstance> instances() {
        return Instances.getValue();
    }

    public MapProperty<String, DXHttpConfigInstance> instancesProperty() {
        return Instances;
    }

    public Map<String, DXHttpConfigEndpoint> endpoints() {
        return Endpoints.getValue();
    }

    public MapProperty<String, DXHttpConfigEndpoint> endpointsProperty() {
        return Endpoints;
    }

    public DXHttpConfigApi(final String name, final String httpVersion, final DXHttpConfig parent) {
        this(name, httpVersion, new HashMap<>(), new HashMap<>(), parent);
    }

    public DXHttpConfig parent() {
        return parent;
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

    public ObservableStringValue getName() {
        return Name;
    }

    public void setName(String name) {
        Name.setValue(name);
    }

    public DXHttpConfigEndpoint GetEndpointByName(final String name) {
        return Endpoints.get(name);
    }

    public DXHttpConfigInstance GetInstanceByName(final String name) {
        return Instances.get(name);
    }

    public static DXHttpConfigApi FromElement(Element element, DXHttpConfig parent) {
        final var name = element.getAttribute(NAME_ATTRIBUTE);
        final var httpVersion = element.getAttribute(HTTP_VERSION_ATTRIBUTE);

        final var instancesElements = DXDomUtils.GetChildElementsWithTagName(element, INSTANCES_ELEMENT_TAG_NAME);

        if (instancesElements.size() == 0)
            throw new RuntimeException("Instances element is missing");
        else if (instancesElements.size() > 1)
            throw new RuntimeException("Too many instances elements");

        final var instancesElement = (Element) instancesElements.get(0);
        final var instanceElements = DXDomUtils.GetChildElementsWithTagName(instancesElement, DXHttpConfigInstance.TAG_NAME);
        final var instances = new HashMap<String, DXHttpConfigInstance>();

        for (final Element item : instanceElements) {
            final var instance = DXHttpConfigInstance.FromElement(item);
            instances.put(instance.name.getValue(), instance);
        }

        final var endpointsElements = DXDomUtils.GetChildElementsWithTagName(element, ENDPOINTS_ELEMENT_TAG_NAME);

        if (endpointsElements.size() == 0)
            throw new RuntimeException("Endpoints element is missing");
        else if (endpointsElements.size() > 1)
            throw new RuntimeException("Too many endpoints elements");

        final var endpointsElement = (Element) endpointsElements.get(0);
        final var endpointElements = DXDomUtils.GetChildElementsWithTagName(endpointsElement, DXHttpConfigEndpoint.TAG_NAME);
        final var endpoints = new HashMap<String, DXHttpConfigEndpoint>();

        for (final Element value : endpointElements) {
            final var endpoint = DXHttpConfigEndpoint.FromElement(value);
            endpoints.put(endpoint.name.getValue(), endpoint);
        }

        return new DXHttpConfigApi(name, httpVersion, instances, endpoints, parent);
    }

    @Override
    public String toString() {
        return "Name: " + this.Name.getValue() + ", HTTP Version: " + this.HttpVersion.getValue()
                + ", Number of instances: " + this.Instances.size() + ", Number of endpoints: " + this.Endpoints.size();
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
