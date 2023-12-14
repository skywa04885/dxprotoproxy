package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigHeader;

public class EditorHeaderFactory {
    public static EditorHeader create(DXHttpConfigHeader configHeader) {
        return new EditorHeader(configHeader, configHeader.name(), configHeader.key(),
                configHeader.value() == null ? "" : configHeader.value());
    }
}
