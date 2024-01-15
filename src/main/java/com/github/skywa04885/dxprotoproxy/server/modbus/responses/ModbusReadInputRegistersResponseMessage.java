package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

public class ModbusReadInputRegistersResponseMessage extends ModbusResponseMessage {
    private final byte[] values;

    public ModbusReadInputRegistersResponseMessage(byte[] values) {
        this.values = values;
    }

    public byte[] values() {
        return values;
    }
}
