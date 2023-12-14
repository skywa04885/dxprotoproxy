package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigHeader;
import javafx.beans.property.SimpleStringProperty;

public class EditorHeader {
    private final DXHttpConfigHeader configHeader;
    private final SimpleStringProperty name;
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public EditorHeader(DXHttpConfigHeader configHeader, String name, String key, String  value) {
        this.configHeader = configHeader;
        this.name = new SimpleStringProperty(null, "name", name);
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public EditorHeader(String name, String key, String  value) {
        this(null, name, key, value);
    }

    public EditorHeader() {
        this(null, "", "", "");
    }

    public DXHttpConfigHeader configHeader() {
        return configHeader;
    }

    public boolean hasConfigHeader() {
        return configHeader != null;
    }

    public boolean hasNoConfigHeader() {
        return configHeader == null;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String name() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty keyProperty() {
        return key;
    }

    public String key() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public String value() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public boolean isBlank() {
        return value.get().isBlank() && name.get().isBlank() && key.get().isBlank();
    }

    public boolean isNotBlank() {
        return !isBlank();
    }

    public DXHttpConfigHeader httpConfigHeader() {
        return new DXHttpConfigHeader(key.get(), value.get().isEmpty() ? null : value.get(), name.get());
    }
}
