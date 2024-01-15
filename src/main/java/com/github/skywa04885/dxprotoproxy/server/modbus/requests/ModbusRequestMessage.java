package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusRequestMessage {
    private final int unitId;

    public ModbusRequestMessage(int unitId) {
        this.unitId = unitId;
    }

    public int unitId() {
        return unitId;
    }
}
