package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
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

    public Map<Integer, DXHttpConfigResponse> children() {
        return Responses;
    }

    public SimpleMapProperty<Integer, DXHttpConfigResponse> childrenProperty() {
        return Responses;
    }

    public DXHttpConfigResponse getByCode(final int code) {
        return Responses.get(code);
    }

    public static DXHttpConfigResponses FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch, expected: " + ELEMENT_TAG_NAME + ", got: "
                    + element.getTagName());

        final List<Element> responseElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigResponse.ELEMENT_NAME);

        final Map<Integer, DXHttpConfigResponse> responses = new HashMap<>();

        for (final Element responseElement : responseElements) {
            final var response = DXHttpConfigResponse.FromElement(responseElement);
            if (responses.containsKey(response.Code.getValue()))
                throw new RuntimeException("Duplicate response code " + response.Code.getValue());
            responses.put(response.Code.getValue(), response);
        }

        return new DXHttpConfigResponses(responses);
    }
}
