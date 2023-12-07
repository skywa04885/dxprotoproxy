package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import javafx.beans.property.SimpleStringProperty;

public class DXHttpPathTemplateTextSegment extends DXHttpPathTemplateSegment {
    public final String text;

    public DXHttpPathTemplateTextSegment(final String text) {
        this.text = text;
    }

    public final String text() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DXHttpPathTemplateTextSegment other) {
            return other.text.equals(text);
        }

        return false;
    }
}
