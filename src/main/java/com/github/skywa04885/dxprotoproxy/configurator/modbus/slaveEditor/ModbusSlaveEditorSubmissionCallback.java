package com.github.skywa04885.dxprotoproxy.configurator.modbus.slaveEditor;

import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusSlaveConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusSlavesConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ModbusSlaveEditorSubmissionCallback implements IModbusSlaveEditorSubmissionCallback {
    private final @NotNull ModbusSlavesConfig slavesConfig;
    private final @Nullable ModbusSlaveConfig slaveConfig;

    public ModbusSlaveEditorSubmissionCallback(@NotNull ModbusSlavesConfig SlavesConfig) {
        this.slavesConfig = SlavesConfig;
        slaveConfig = null;
    }

    public ModbusSlaveEditorSubmissionCallback(@NotNull ModbusSlaveConfig SlaveConfig) {
        slavesConfig = Objects.requireNonNull(SlaveConfig.parent());
        this.slaveConfig = SlaveConfig;
    }

    private void create(@NotNull String controllerAddress, int controllerPort, @NotNull String masterAddress, int masterPort) {
        final ModbusSlaveConfig modbusSlaveConfig = new ModbusSlaveConfig(masterAddress, masterPort, controllerAddress,
                controllerPort, slavesConfig);

        slavesConfig.children().add(modbusSlaveConfig);
    }

    private void update(@NotNull String controllerAddress, int controllerPort, @NotNull String masterAddress, int masterPort) {
        assert slaveConfig != null;

        if (!slaveConfig.controllerAddress().equals(controllerAddress)) slaveConfig.setControllerAddress(controllerAddress);
        if (slaveConfig.controllerPort() != controllerPort) slaveConfig.setControllerPort(controllerPort);

        if (!slaveConfig.masterAddress().equals(masterAddress)) slaveConfig.setMasterAddress(masterAddress);
        if (slaveConfig.masterPort() != masterPort) slaveConfig.setMasterPort(masterPort);
    }

    @Override
    public void submit(@NotNull String controllerAddress, int controllerPort, @NotNull String masterAddress, int masterPort) {
        if (slaveConfig == null) {
            create(controllerAddress, controllerPort, masterAddress, masterPort);
        } else {
            update(controllerAddress, controllerPort, masterAddress, masterPort);
        }
    }
}
