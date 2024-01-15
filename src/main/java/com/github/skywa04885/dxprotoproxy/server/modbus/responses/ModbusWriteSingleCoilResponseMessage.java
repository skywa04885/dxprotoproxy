package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusWriteSingleCoilResponseMessage extends ModbusResponseMessage {
    private final int address;
    private final boolean value;

    public ModbusWriteSingleCoilResponseMessage(int address, boolean value) {
        this.address = address;
        this.value = value;
    }

    public int address() {
        return address;
    }

    public boolean value() {
        return value;
    }
}
