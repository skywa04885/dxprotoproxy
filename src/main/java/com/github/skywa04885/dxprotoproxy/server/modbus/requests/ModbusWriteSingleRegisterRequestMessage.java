package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusWriteSingleRegisterRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final int value;

    public ModbusWriteSingleRegisterRequestMessage(int unitId, int address, int value) {
        super(unitId);

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
