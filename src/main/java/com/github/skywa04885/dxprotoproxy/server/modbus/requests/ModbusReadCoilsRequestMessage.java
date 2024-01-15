package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessage;

public class ModbusReadCoilsRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final int quantity;

    public ModbusReadCoilsRequestMessage(int unitId, int address, int quantity) {
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
