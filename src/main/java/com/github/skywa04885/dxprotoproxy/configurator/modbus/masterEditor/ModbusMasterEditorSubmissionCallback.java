package com.github.skywa04885.dxprotoproxy.configurator.modbus.masterEditor;

import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusMasterConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusMastersConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ModbusMasterEditorSubmissionCallback implements IModbusMasterEditorSubmissionCallback {
    private final @NotNull ModbusMastersConfig mastersConfig;
    private final @Nullable ModbusMasterConfig masterConfig;

    public ModbusMasterEditorSubmissionCallback(@NotNull ModbusMastersConfig mastersConfig) {
        this.mastersConfig = mastersConfig;
        masterConfig = null;
    }

    public ModbusMasterEditorSubmissionCallback(@NotNull ModbusMasterConfig masterConfig) {
        mastersConfig = Objects.requireNonNull(masterConfig.parent());
        this.masterConfig = masterConfig;
    }

    private void create(@NotNull String serverAddress, int serverPort, @NotNull String listenAddress, int listenPort) {
        final ModbusMasterConfig modbusMasterConfig = new ModbusMasterConfig(serverAddress, serverPort, listenAddress,
                listenPort, mastersConfig);

        mastersConfig.children().add(modbusMasterConfig);
    }

    private void update(@NotNull String serverAddress, int serverPort, @NotNull String listenAddress, int listenPort) {
        assert masterConfig != null;

        if (!masterConfig.serverAddress().equals(serverAddress)) masterConfig.setServerAddress(serverAddress);
        if (masterConfig.serverPort() != serverPort) masterConfig.setServerPort(serverPort);

        if (!masterConfig.listenAddress().equals(listenAddress)) masterConfig.setListenAddress(listenAddress);
        if (masterConfig.listenPort() != listenPort) masterConfig.setListenPort(listenPort);
    }

    @Override
    public void submit(@NotNull String serverAddress, int serverPort, @NotNull String listenAddress, int listenPort) {
        if (masterConfig == null) {
            create(serverAddress, serverPort, listenAddress, listenPort);
        } else {
            update(serverAddress, serverPort, listenAddress, listenPort);
        }
    }
}
