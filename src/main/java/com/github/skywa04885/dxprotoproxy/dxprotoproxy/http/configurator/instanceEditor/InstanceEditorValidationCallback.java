package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.HttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.HttpConfigInstances;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class InstanceEditorValidationCallback implements IInstanceEditorValidationCallback {
    private final @NotNull HttpConfigInstances httpConfigInstances;
    private final @Nullable DXHttpConfigInstance configInstance;

    public InstanceEditorValidationCallback(@NotNull HttpConfigInstances httpConfigInstances,
                                            @Nullable DXHttpConfigInstance configInstance) {
        this.httpConfigInstances = httpConfigInstances;
        this.configInstance = configInstance;
    }

    public InstanceEditorValidationCallback(@NotNull HttpConfigInstances httpConfigInstances) {
        this(httpConfigInstances, null);
    }

    public InstanceEditorValidationCallback(@NotNull DXHttpConfigInstance configInstance) {
        this(Objects.requireNonNull(configInstance.parent()), configInstance);
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
        if (!HttpConfigValidators.isNameValid(name)) {
            return "Invalid name";
        }

        if (!HttpConfigValidators.isValidHost(host)) {
            return "Invalid host";
        }

        if (!HttpConfigValidators.isProtocolValid(protocol)) {
            return "Invalid protocol";
        }

        if (configInstance != null && !configInstance.name().equals(name)
                && httpConfigInstances.children().containsKey(name)) {
            return "Name already in use";
        } else if (httpConfigInstances.children().containsKey(name)) {
            return "Name already in use";
        }


        return null;
    }
}
