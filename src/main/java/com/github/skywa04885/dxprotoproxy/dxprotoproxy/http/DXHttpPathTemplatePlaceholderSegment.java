package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

public class DXHttpPathTemplatePlaceholderSegment extends DXHttpPathTemplateSegment {
    private final String name;

    public DXHttpPathTemplatePlaceholderSegment(final String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DXHttpPathTemplatePlaceholderSegment other) {
            return other.name.equals(name);
        }

        return false;
    }
}
