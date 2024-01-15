package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModbusWriteMultipleCoilsRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final @NotNull List<Boolean> values;

    public ModbusWriteMultipleCoilsRequestMessage(int unitId, int address, @NotNull List<Boolean> values) {
        super(unitId);

        this.address = address;
        this.values = values;
    }

    public int address() {
        return address;
    }

    public @NotNull List<Boolean> values() {
        return values;
    }
}
