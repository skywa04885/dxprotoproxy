package com.github.skywa04885.dxprotoproxy.configurator.http;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigUriQueryParameter;

import java.util.Objects;

public class EditorQueryParameterFactory {
    /**
     * Creates a new editor query parameter from the given config query parameter.
     * @param configQueryParameter the config query parameter.
     * @return the editor query parameter.
     */
    public static EditorQueryParameter create(DXHttpConfigUriQueryParameter configQueryParameter) {
        return new EditorQueryParameter(configQueryParameter, configQueryParameter.key(),
                configQueryParameter.value() == null ? "" : Objects.requireNonNull(configQueryParameter.value()));
    }
}
