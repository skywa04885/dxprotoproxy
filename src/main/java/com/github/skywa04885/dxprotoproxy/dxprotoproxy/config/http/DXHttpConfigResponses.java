package com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DXHttpConfigResponses {
    public static final String ELEMENT_TAG_NAME = "Responses";

    public final SimpleMapProperty<Integer, DXHttpConfigResponse> Responses;

    public DXHttpConfigResponses(final Map<Integer, DXHttpConfigResponse> responses) {
        Responses = new SimpleMapProperty<>(null, "Reacties", FXCollections.observableMap(responses));
    }

    public DXHttpConfigResponses() {
        this(new HashMap<>());
    }

    public Map<Integer, DXHttpConfigResponse> children() {
        return Responses;
    }

    public SimpleMapProperty<Integer, DXHttpConfigResponse> childrenProperty() {
        return Responses;
    }

    public DXHttpConfigResponse getByCode(final int code) {
        return Responses.get(code);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        Responses.values().forEach(httpConfigResponse -> {
            element.appendChild(httpConfigResponse.toElement(document));
        });

        return element;
    }

    public static DXHttpConfigResponses FromElement(final Element element) {
        // Make sure that the given element has the correct tag.
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch, expected: " + ELEMENT_TAG_NAME + ", got: "
                    + element.getTagName());

        // Create the config responses.
        final var configResponses = new DXHttpConfigResponses();

        // Get all the response elements.
        final List<Element> responseElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigResponse.ELEMENT_NAME);

        // Process all the response elements.
        responseElements.forEach(responseElement -> {
            // Creates the response from the XML element.
            final var response = DXHttpConfigResponse.FromElement(configResponses, responseElement);

            // Checks if we're dealing with duplicate codes.
            if (configResponses.children().containsKey(response.code())) {
                throw new RuntimeException("Duplicate response code " + response.code());
            }

            // Puts the response in the responses object.
            configResponses.children().put(response.code(), response);
        });

        // Return the config responses.
        return configResponses;
    }
}
