package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusWriteSingleRegisterResponseMessage extends ModbusResponseMessage {
    private final int address;
    private final int value;

    public ModbusWriteSingleRegisterResponseMessage(int address, int value) {
        this.address = address;
        this.value = value;
    }

    public int address() {
        return address;
    }

    public int value() {
        return value;
    }
}
