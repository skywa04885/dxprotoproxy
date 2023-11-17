package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import javafx.beans.property.SimpleStringProperty;

public class DXConfiguratorPreviewItem {
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public DXConfiguratorPreviewItem(String key, String value) {
        this.key = new SimpleStringProperty(null, "key", key);
        this.value = new SimpleStringProperty(null, "value", value);
    }

    public String value() {
        return value.getValue();
    }

    public SimpleStringProperty keyProperty() {
        return key;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }
}
