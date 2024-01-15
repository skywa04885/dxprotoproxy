package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusWriteSingleCoilRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final boolean value;

    public ModbusWriteSingleCoilRequestMessage(int unitId, int address, boolean value) {
        super(unitId);

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
