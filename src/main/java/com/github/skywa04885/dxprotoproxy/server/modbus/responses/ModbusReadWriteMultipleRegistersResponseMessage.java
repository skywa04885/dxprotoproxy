package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusReadWriteMultipleRegistersResponseMessage extends ModbusResponseMessage {
    private final byte[] values;

    public ModbusReadWriteMultipleRegistersResponseMessage(byte[] values) {
        this.values = values;
    }

    public byte[] values() {
        return values;
    }
}
