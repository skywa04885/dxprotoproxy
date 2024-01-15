package com.github.skywa04885.dxprotoproxy.config.modbus;

import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class ModbusMastersConfig {
    public static final @NotNull String ELEMENT_TAG_NAME = "ModbusMasters";

    private final @NotNull SimpleListProperty<ModbusMasterConfig> childrenProperty;
    private final @NotNull SimpleObjectProperty<ModbusConfig> parentProperty;

    public ModbusMastersConfig(@NotNull List<ModbusMasterConfig> children,
                               @Nullable ModbusConfig parent) {
        childrenProperty = new SimpleListProperty<>(null, "children",
                FXCollections.observableList(children));
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    public ModbusMastersConfig() {
        this(new ArrayList<>(), null);
    }

    public @NotNull SimpleListProperty<ModbusMasterConfig> childrenProperty() {
        return childrenProperty;
    }

    public @NotNull List<ModbusMasterConfig> children() {
        return childrenProperty.get();
    }

    public @Nullable ModbusConfig parent() {
        return parentProperty.get();
    }

    public void setParent(@Nullable ModbusConfig parent) {
        parentProperty.set(parent);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        children().stream().map(child -> child.toElement(document)).forEach(element::appendChild);

        return element;
    }

    public static @NotNull ModbusMastersConfig fromElement(@NotNull Element element) {
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        final @NotNull List<Element> modbusMasterElements = DXDomUtils.GetChildElementsWithTagName(element,
                ModbusMasterConfig.ELEMENT_TAG_NAME);

        final @NotNull List<ModbusMasterConfig> modbusMasterConfigs = new ArrayList<>(
                modbusMasterElements.stream().map(ModbusMasterConfig::fromElement).toList());

        final @NotNull ModbusMastersConfig modbusMastersConfig = new ModbusMastersConfig(modbusMasterConfigs, null);

        modbusMasterConfigs.forEach(modbusMasterConfig -> modbusMasterConfig.setParent(modbusMastersConfig));

        return modbusMastersConfig;
    }
}
