package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigField;
import javafx.beans.property.SimpleStringProperty;

public class RequestEditorField {
    private final SimpleStringProperty name;
    private final SimpleStringProperty path;
    private final SimpleStringProperty value;

    public RequestEditorField(String name, String key, String  value) {
        this.name = new SimpleStringProperty(null, "name", name);
        this.path = new SimpleStringProperty(null, "path", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public RequestEditorField() {
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

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public String path() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
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
        return value.get().isEmpty() && name.get().isEmpty() && path.get().isEmpty();
    }

    public DXHttpConfigField httpConfigField() {
        return new DXHttpConfigField(path.get(), name.get(), value.get().isEmpty() ? null : value.get());
    }
}
