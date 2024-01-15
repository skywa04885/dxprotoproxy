package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessage;

public class ModbusMaskWriteRegisterResponseMessage extends ModbusResponseMessage {
    private final int address;
    private final int orMask;
    private final int andMask;

    public ModbusMaskWriteRegisterResponseMessage(int address, int orMask, int andMask) {
        this.address = address;
        this.orMask = orMask;
        this.andMask = andMask;
    }

    public int address() {
        return address;
    }

    public int orMask() {
        return orMask;
    }

    public int andMask() {
        return andMask;
    }
}
