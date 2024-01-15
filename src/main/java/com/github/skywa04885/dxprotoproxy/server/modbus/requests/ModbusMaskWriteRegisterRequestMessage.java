package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusMaskWriteRegisterRequestMessage extends ModbusRequestMessage {
    private final int address;
    private final int orMask;
    private final int andMask;

    public ModbusMaskWriteRegisterRequestMessage(int unitId, int address, int orMask, int andMask) {
        super(unitId);

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
