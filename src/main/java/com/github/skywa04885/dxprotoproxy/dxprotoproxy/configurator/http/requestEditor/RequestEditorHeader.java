package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigHeader;
import javafx.beans.property.SimpleStringProperty;

public class RequestEditorHeader {
    private final SimpleStringProperty name;
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public RequestEditorHeader(String name, String key, String  value) {
        this.name = new SimpleStringProperty(null, "name", name);
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public RequestEditorHeader() {
        this("", "", "");
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

    public boolean isEmpty() {
        return value.get().isEmpty() && name.get().isEmpty() && key.get().isEmpty();
    }

    public DXHttpConfigHeader httpConfigHeader() {
        return new DXHttpConfigHeader(key.get(), value.get().isEmpty() ? null : value.get(), name.get());
    }
}
