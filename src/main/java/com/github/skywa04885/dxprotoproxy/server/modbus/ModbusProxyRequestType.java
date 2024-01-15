package com.github.skywa04885.dxprotoproxy.server.modbus;

public enum ModbusProxyRequestType {
    WriteSingleRegister("WSR"),
    ReadWriteMultipleRegisters("WMR"),
    ReadHoldingRegisters("RHR"),
    WriteSingleCoil("WSC"),
    WriteMultipleCoils("WMC"),
    ReadCoils("RC"),
    WriteMultipleRegisters("WMR"),
    ReadDiscreteInputs("RDI"),
    ReadInputRegisters("RIR"),
    MaskWriteRegister("MWR");

    private final String opcode;

    ModbusProxyRequestType(final String opcode) {
        this.opcode = opcode;
    }

    public String opcode() {
        return this.opcode;
    }
}
