package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigField;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EditorField {
    @Nullable
    private final DXHttpConfigField configField;
    @NotNull
    private final SimpleStringProperty name;
    @NotNull
    private final SimpleStringProperty path;
    @NotNull
    private final SimpleStringProperty value;

    public EditorField(@Nullable DXHttpConfigField configField, @NotNull String name, @NotNull String key, @NotNull String value) {
        this.configField = configField;
        this.name = new SimpleStringProperty(null, "name", name);
        this.path = new SimpleStringProperty(null, "path", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public EditorField() {
        this(null, "", "", "");
    }

    public DXHttpConfigField configField() {
        return configField;
    }

    public boolean hasConfigField() {
        return configField != null;
    }

    public boolean hasNoConfigField() {
        return configField == null;
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

    public boolean isBlank() {
        return name.get().isBlank() && path.get().isBlank();
    }

    public boolean isNotBlank() {
        return !isBlank();
    }

    public DXHttpConfigField httpConfigField() {
        return new DXHttpConfigField(path.get(), name.get(), value.get().isEmpty() ? null : value.get());
    }
}
