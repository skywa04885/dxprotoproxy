package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigValidators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InstanceEditorValidationCallback implements IInstanceEditorValidationCallback {
    private final @Nullable DXHttpConfigApi configApi;
    private final @Nullable DXHttpConfigInstance configInstance;

    public InstanceEditorValidationCallback(@Nullable DXHttpConfigApi configApi,
                                            @Nullable DXHttpConfigInstance configInstance) {
        assert (configInstance == null && configApi != null) || (configInstance != null && configApi == null);

        this.configApi = configApi;
        this.configInstance = configInstance;
    }

    public InstanceEditorValidationCallback(@Nullable DXHttpConfigApi configApi) {
        this(configApi, null);
    }

    public InstanceEditorValidationCallback(@Nullable DXHttpConfigInstance configInstance) {
        this(null, configInstance);
    }

    /**
     * Gets called when the instance editor wants to validate.
     *
     * @param name     the name.
     * @param host     the host.
     * @param port     the port.
     * @param protocol the protocol.
     */
    @Override
    public @Nullable String validate(@NotNull String name, @NotNull String host, int port, @NotNull String protocol) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Invalid name";
        }

        if (!DXHttpConfigValidators.isValidHost(host)) {
            return "Invalid host";
        }

        if (!DXHttpConfigValidators.isProtocolValid(protocol)) {
            return "Invalid protocol";
        }

        if (configApi == null) {
            assert configInstance != null;
            if (!configInstance.name().equals(name) && configInstance.parent().instances().containsKey(name)) {
                return "Name already in use";
            }
        } else {
            if (configApi.instances().containsKey(name)) {
                return "Name already in use";
            }
        }

        return null;
    }
}
