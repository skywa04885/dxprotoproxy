package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusWriteMultipleRegistersRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final int quantity;
    private final byte[] values;

    public ModbusWriteMultipleRegistersRequestMessage(int unitId, int address, int quantity, byte[] values) {
        super(unitId);

        this.address = address;
        this.quantity = quantity;
        this.values = values;
    }

    public int address() {
        return address;
    }

    public int quantity() {
        return quantity;
    }

    public byte[] values() {
        return values;
    }
}
