package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusReadHoldingRegistersResponseMessage extends ModbusResponseMessage {
    private final byte[] values;

    public ModbusReadHoldingRegistersResponseMessage(byte[] values) {
        this.values = values;
    }

    public byte[] values() {
        return values;
    }
}
