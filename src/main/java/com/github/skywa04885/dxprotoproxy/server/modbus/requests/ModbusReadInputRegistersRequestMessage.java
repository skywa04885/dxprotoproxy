package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusReadInputRegistersRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final int quantity;

    public ModbusReadInputRegistersRequestMessage(int unitId, int address, int quantity) {
        super(unitId);

        this.address = address;
        this.quantity = quantity;
    }

    public int address() {
        return address;
    }

    public int quantity() {
        return quantity;
    }
}
