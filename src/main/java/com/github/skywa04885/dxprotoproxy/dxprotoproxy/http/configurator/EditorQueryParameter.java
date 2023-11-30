package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigUriQueryParameter;
import javafx.beans.property.SimpleStringProperty;

public class EditorQueryParameter {
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public EditorQueryParameter(String key, String value) {
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public EditorQueryParameter() {
        this("", "");
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
        return value.get().isEmpty() && key.get().isEmpty();
    }

    public DXHttpConfigUriQueryParameter httpConfigUriQueryParameter() {
        return new DXHttpConfigUriQueryParameter(key.get(), value.get().isEmpty() ? null : value.get());
    }
}
