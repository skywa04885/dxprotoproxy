package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;
import javafx.scene.control.Alert;

public class DefaultCreateApiCallbacks implements ICreateApiCallbacks {
    private final DXHttpConfig httpConfig;

    public DefaultCreateApiCallbacks(DXHttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    @Override
    public String validate(String name, String httpVersion) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Name is invalid";
        } else if (!DXHttpConfigValidators.isHttpVersionValid(httpVersion)) {
            return "HTTP version is invalid";
        } else if (httpConfig.httpApis().containsKey(name)) {
            return "Name is already in use";
        }

        return null;
    }

    @Override
    public void submit(String name, String httpVersion) {
        // Creates the API.
        final var configApi = new DXHttpConfigApi(name, httpVersion, httpConfig);

        // Inserts the API in the config.
        httpConfig.httpApis().put(configApi.name(), configApi);
    }
}
