package com.github.skywa04885.dxprotoproxy.http;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DXHttpPathTemplateRenderer {
    public final DXHttpPathTemplate Template;

    public DXHttpPathTemplateRenderer(final DXHttpPathTemplate template) {
        Template = template;
    }

    public String Render(final Map<String, String> substitutions) {
        final var stringBuilder = new StringBuilder();

        for (final var segment : Template.segments()) {
            stringBuilder.append('/');

            if (segment instanceof DXHttpPathTemplatePlaceholderSegment placeholderSegment) {
                final var substitution = substitutions.get(placeholderSegment.name());
                if (substitution == null)
                    throw new RuntimeException("No substitution provided for placeholder " + placeholderSegment.name());
                stringBuilder.append(URLEncoder.encode(substitution, StandardCharsets.UTF_8));
            } else if (segment instanceof DXHttpPathTemplateTextSegment textSegment) {
                stringBuilder.append(URLEncoder.encode(textSegment.text(), StandardCharsets.UTF_8));
            }
        }

        return stringBuilder.toString();
    }
}
