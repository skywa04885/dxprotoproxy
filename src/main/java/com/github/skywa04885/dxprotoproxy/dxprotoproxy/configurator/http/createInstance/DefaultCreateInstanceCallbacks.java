package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;

public class DefaultCreateInstanceCallbacks implements ICreateInstanceCallbacks {
    private final DXHttpConfigApi httpConfigApi;

    public DefaultCreateInstanceCallbacks(DXHttpConfigApi httpConfigApi) {
        this.httpConfigApi = httpConfigApi;
    }

    @Override
    public String validate(String name, String host, int port, String protocol) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Naam is niet geldig";
        } else if (!DXHttpConfigValidators.isValidHost(host)) {
            return "Host is niet geldig";
        } else if (!DXHttpConfigValidators.isProtocolValid(protocol)) {
            return "protocol is niet geldig";
        } else if (httpConfigApi.instances().containsKey(name)) {
            return "Er bestaat al een instantie met de gekozen naam";
        }

        return null;
    }

    @Override
    public void submit(String name, String host, int port, String protocol) {

        // Create the instance.
        final var configInstance = new DXHttpConfigInstance(name, host, port, protocol);

        // Inserts the instance into the config.
        httpConfigApi.instances().put(configInstance.name(), configInstance);
    }
}
