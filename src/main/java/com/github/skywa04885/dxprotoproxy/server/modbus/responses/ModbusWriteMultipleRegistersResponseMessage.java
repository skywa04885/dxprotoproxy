package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusWriteMultipleRegistersResponseMessage extends ModbusResponseMessage {
    private final int address;
    private final int quantity;

    public ModbusWriteMultipleRegistersResponseMessage(int address, int quantity) {
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
