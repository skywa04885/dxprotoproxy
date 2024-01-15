package com.github.skywa04885.dxprotoproxy.config.modbus;

import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class ModbusConfig {
    public static final @NotNull String ELEMENT_TAG_NAME = "Modbus";

    private final @NotNull ModbusSlavesConfig slavesConfig;
    private final @NotNull ModbusMastersConfig mastersConfig;

    public ModbusConfig(@NotNull ModbusSlavesConfig slavesConfig, @NotNull ModbusMastersConfig mastersConfig) {
        this.slavesConfig = slavesConfig;
        this.mastersConfig = mastersConfig;
    }

    public ModbusConfig() {
        this(new ModbusSlavesConfig(), new ModbusMastersConfig());
    }

    public @NotNull ModbusSlavesConfig slavesConfig() {
        return slavesConfig;
    }

    public @NotNull ModbusMastersConfig mastersConfig() {
        return mastersConfig;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        element.appendChild(slavesConfig().toElement(document));
        element.appendChild(mastersConfig().toElement(document));

        return element;
    }

    public static @NotNull ModbusConfig fromElement(@NotNull Element element) {
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        final @NotNull List<Element> slavesConfigElements = DXDomUtils.GetChildElementsWithTagName(element,
                ModbusSlavesConfig.ELEMENT_TAG_NAME);
        final @NotNull List<Element> mastersConfigElements = DXDomUtils.GetChildElementsWithTagName(element,
                ModbusMastersConfig.ELEMENT_TAG_NAME);

        if (slavesConfigElements.isEmpty()) throw new RuntimeException("Slaves config element is missing");
        if (mastersConfigElements.isEmpty()) throw new RuntimeException("Masters config element is missing");

        if (slavesConfigElements.size() > 1) throw new RuntimeException("Too many slaves config elements");
        if (mastersConfigElements.size() > 1) throw new RuntimeException("Too many masters config elements");

        final @NotNull Element slavesConfigElement = slavesConfigElements.get(0);
        final @NotNull Element mastersConfigElement = mastersConfigElements.get(0);

        final @NotNull ModbusSlavesConfig slavesConfig = ModbusSlavesConfig.fromElement(slavesConfigElement);
        final @NotNull ModbusMastersConfig mastersConfig = ModbusMastersConfig.fromElement(mastersConfigElement);

        return new ModbusConfig(slavesConfig, mastersConfig);
    }
}
