package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

public class ModbusReadWriteMultipleRegistersRequestMessage extends ModbusRequestMessage {
    private final int readAddress;
    private final int readQuantity;
    private final int writeAddress;
    private final int writeQuantity;
    private final byte [] writeValues;

    public ModbusReadWriteMultipleRegistersRequestMessage(int unitId, int readAddress, int readQuantity, int writeAddress,
                                                          int writeQuantity, byte [] writeValues) {
        super(unitId);

        this.readAddress = readAddress;
        this.readQuantity = readQuantity;
        this.writeAddress = writeAddress;
        this.writeQuantity = writeQuantity;
        this.writeValues = writeValues;
    }

    public int readAddress() {
        return readAddress;
    }

    public int readQuantity() {
        return readQuantity;
    }

    public int writeAddress() {
        return writeAddress;
    }

    public int writeQuantity() {
        return writeQuantity;
    }

    public byte [] writeValues() {
        return writeValues;
    }
}
