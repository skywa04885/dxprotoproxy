package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import javafx.beans.property.SimpleStringProperty;

public class DXHttpPathTemplatePlaceholderSegment extends DXHttpPathTemplateSegment {
    private final String name;

    public DXHttpPathTemplatePlaceholderSegment(final String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }
}
