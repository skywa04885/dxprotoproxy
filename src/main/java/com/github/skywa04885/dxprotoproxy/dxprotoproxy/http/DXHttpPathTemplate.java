package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.stream.Collectors;

public class DXHttpPathTemplate {
    private final List<DXHttpPathTemplateSegment> segments;

    public DXHttpPathTemplate(final List<DXHttpPathTemplateSegment> segments) {
        this.segments = new SimpleListProperty<>(null, "Segmenten", FXCollections.observableList(segments));
    }

    public List<DXHttpPathTemplateSegment> segments() {
        return segments;
    }

    public boolean shouldSubstitute() {
        return segments.stream().anyMatch(segment -> segment instanceof DXHttpPathTemplatePlaceholderSegment);
    }

    public String stringOfSegments() {
        return segments().stream().map(y -> {
            if (y instanceof DXHttpPathTemplateTextSegment textSegment) {
                return textSegment.text();
            }
            else if (y instanceof DXHttpPathTemplatePlaceholderSegment placeholderSegment) {
                return "{" + placeholderSegment.name() + "}";
            } else {
                throw new Error();
            }
        }).collect(Collectors.joining("/"));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DXHttpPathTemplate pathTemplate) {
            // Make sure there are the same number of segments.
            if (pathTemplate.segments.size() != segments.size()) {
                return false;
            }

            // Make sure all the segments are equal.
            for (int i = 0; i < segments.size(); ++i) {
                final DXHttpPathTemplateSegment segment = segments.get(i);
                final DXHttpPathTemplateSegment otherSegment = pathTemplate.segments.get(i);
                if (!segment.equals(otherSegment)) {
                    return false;
                }
            }

            // Return true since they're the same.
            return true;
        } else {
            return false;
        }
    }
}
