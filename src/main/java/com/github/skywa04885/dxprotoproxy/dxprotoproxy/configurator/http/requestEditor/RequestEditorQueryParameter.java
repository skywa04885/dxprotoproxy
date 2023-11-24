package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigUriQueryParameter;
import javafx.beans.property.SimpleStringProperty;

public class RequestEditorQueryParameter {
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public RequestEditorQueryParameter(String key, String value) {
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public RequestEditorQueryParameter() {
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
