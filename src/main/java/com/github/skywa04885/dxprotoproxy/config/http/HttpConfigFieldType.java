package com.github.skywa04885.dxprotoproxy.config.http;

public enum HttpConfigFieldType {
    Integer("Integer"),
    Double("Double"),
    Number("Number"),
    Boolean("Boolean"),
    DateTime("DateTime"),
    String("String");

    private final String label;

    HttpConfigFieldType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static HttpConfigFieldType fromLabel(String label) {


        return null;
    }
}
