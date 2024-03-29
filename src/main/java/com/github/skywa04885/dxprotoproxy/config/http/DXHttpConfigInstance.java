package com.github.skywa04885.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigInstance {
    public static final String TAG_NAME = "Instance";
    public static final String NAME_ATTRIBUTE_NAME = "Name";
    public static final String HOST_ATTRIBUTE_NAME = "Host";
    public static final String PORT_ATTRIBUTE_NAME = "Port";
    public static final String PROTOCOL_ATTRIBUTE_NAME = "Protocol";

    private @Nullable HttpConfigInstances parent;
    public final SimpleStringProperty name;
    public final SimpleStringProperty host;
    public final SimpleIntegerProperty port;
    public final SimpleStringProperty protocol;

    public DXHttpConfigInstance(final String name, final String host, final int port, final String protocol) {
        this.parent = parent;
        this.name = new SimpleStringProperty(null, "name", name);
        this.host = new SimpleStringProperty(null, "host", host);
        this.port = new SimpleIntegerProperty(null, "port", port);
        this.protocol = new SimpleStringProperty(null, "protocol", protocol);
    }

    public DXHttpConfigInstance()
    {
        this("", "", 0, "");
    }

    public @Nullable HttpConfigInstances parent() {
        return parent;
    }

    public void setParent(@Nullable HttpConfigInstances parent) {
        this.parent = parent;
    }

    public String name() {
        return name.getValue();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name.set(name);
    }

    public String host() {
        return host.getValue();
    }

    public SimpleStringProperty hostProperty() {
        return host;
    }

    public void setHost(@NotNull String host) {
        this.host.set(host);
    }

    public int port() {
        return port.getValue();
    }

    public SimpleIntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public String protocol() {
        return protocol.getValue();
    }

    public SimpleStringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(@NotNull String protocol) {
        this.protocol.set(protocol);
    }

    public static DXHttpConfigInstance FromElement(Element element) {
        final var name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) throw new RuntimeException("Name attribute is missing");

        final var host = element.getAttribute(HOST_ATTRIBUTE_NAME).trim();
        if (host.isEmpty()) throw new RuntimeException("Host attribute is missing");

        final var portString = element.getAttribute(PORT_ATTRIBUTE_NAME).trim();
        if (portString.isEmpty()) throw new RuntimeException("Port attribute is missing");

        short port;

        try {
            port = Short.parseShort(portString);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid port");
        }

        final var protocol = element.getAttribute(PROTOCOL_ATTRIBUTE_NAME).trim();
        if (protocol.isEmpty()) throw new RuntimeException("Protocol missing");

        return new DXHttpConfigInstance(name, host, port, protocol);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.setAttribute(NAME_ATTRIBUTE_NAME, name());
        element.setAttribute(HOST_ATTRIBUTE_NAME, host());
        element.setAttribute(PORT_ATTRIBUTE_NAME, Integer.toString(port()));
        element.setAttribute(PROTOCOL_ATTRIBUTE_NAME, protocol());

        return element;
    }
}
