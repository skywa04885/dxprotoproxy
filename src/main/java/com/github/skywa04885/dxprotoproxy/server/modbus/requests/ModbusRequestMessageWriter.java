package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayEncoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.*;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ModbusRequestMessageWriter {
    private final @NotNull NetOutputStreamWrapper outputStreamWrapper;

    public ModbusRequestMessageWriter(@NotNull NetOutputStreamWrapper outputStreamWrapper) {
        this.outputStreamWrapper = outputStreamWrapper;
    }

    public void write(@NotNull ModbusMaskWriteRegisterRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.orMask() + " " + message.andMask());
    }

    public void write(@NotNull ModbusReadCoilsRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.quantity());
    }

    public void write(@NotNull ModbusReadDiscreteInputsRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.quantity());
    }

    public void write(@NotNull ModbusReadHoldingRegistersRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.quantity());
    }

    public void write(@NotNull ModbusReadInputRegistersRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.quantity());
    }

    public void write(@NotNull ModbusReadWriteMultipleRegistersRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.readAddress() + " " + message.readQuantity()
                + " " + message.writeAddress() + " " + message.writeQuantity());

        outputStreamWrapper.write(message.writeValues());
    }

    public void write(@NotNull ModbusWriteMultipleCoilsRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + "");
        outputStreamWrapper.writeLine(ModbusProxyBitArrayEncoder.encode(message.values()));
    }

    public void write(@NotNull ModbusWriteMultipleRegistersRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.quantity());
        outputStreamWrapper.write(message.values());
    }

    public void write(@NotNull ModbusWriteSingleCoilRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + (message.value() ? "1" : "0"));
    }

    public void write(@NotNull ModbusWriteSingleRegisterRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.address() + " " + message.value());
    }

    public void write(@NotNull ModbusRequestMessage message) throws IOException {
        outputStreamWrapper.writeLine(message.unitId() + "");

        if (message instanceof ModbusMaskWriteRegisterRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusReadCoilsRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusReadDiscreteInputsRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusReadHoldingRegistersRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusReadInputRegistersRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusReadWriteMultipleRegistersRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusWriteMultipleCoilsRequestMessage message1) {
            write(message1);
        } else if (message instanceof ModbusWriteSingleCoilRequestMessage message1) {
            write(message1);
        } else {
            throw new RuntimeException("Write not implemented for modbus request message of class: "
                    + message.getClass().getName());
        }

        outputStreamWrapper.flush();
    }
}
