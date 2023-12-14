package com.github.skywa04885.dxprotoproxy.configurator.http;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigUriQueryParameter;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EditorQueryParameter {
    @Nullable
    private final DXHttpConfigUriQueryParameter configQueryParameter;
    @NotNull
    private final SimpleStringProperty key;
    @NotNull
    private final SimpleStringProperty value;

    public EditorQueryParameter(@Nullable DXHttpConfigUriQueryParameter configQueryParameter, @NotNull String key,
                                @NotNull String value) {
        this.configQueryParameter = configQueryParameter;
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public EditorQueryParameter() {
        this(null, "", "");
    }

    @Nullable
    public DXHttpConfigUriQueryParameter configQueryParameter() {
        return configQueryParameter;
    }

    /**
     * Returns true if the editor query parameter has a config query parameter.
     * @return true or false depending on whether it has it or not.
     */
    public boolean hasConfigQueryParameter() {
        return configQueryParameter != null;
    }

    /**
     * Returns false if the editor query parameter has a config query parameter.
     * @return true or false depending on whether it has it or not.
     */
    public boolean hasNoConfigQueryParameter() {
        return configQueryParameter == null;
    }

    @NotNull
    public SimpleStringProperty keyProperty() {
        return key;
    }

    @NotNull
    public String key() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    @NotNull
    public SimpleStringProperty valueProperty() {
        return value;
    }

    @NotNull

    public String value() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public boolean isEmpty() {
        return value.get().isEmpty() && key.get().isEmpty();
    }

    @NotNull
    public DXHttpConfigUriQueryParameter httpConfigUriQueryParameter() {
        return new DXHttpConfigUriQueryParameter(key.get(), value.get().isEmpty() ? null : value.get());
    }
}
