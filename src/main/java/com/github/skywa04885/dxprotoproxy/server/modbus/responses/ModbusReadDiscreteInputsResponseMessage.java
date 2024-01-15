package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModbusReadDiscreteInputsResponseMessage extends ModbusResponseMessage {
    private final @NotNull List<Boolean> values;

    public ModbusReadDiscreteInputsResponseMessage(@NotNull List<Boolean> values) {
        this.values = values;
    }

    public @NotNull List<Boolean> values() {
        return values;
    }
}
