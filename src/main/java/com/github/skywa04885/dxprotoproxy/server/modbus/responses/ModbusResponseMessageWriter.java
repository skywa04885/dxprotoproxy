package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayEncoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.*;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ModbusResponseMessageWriter {
    private final @NotNull NetOutputStreamWrapper outputStreamWrapper;

    public ModbusResponseMessageWriter(@NotNull NetOutputStreamWrapper outputStreamWrapper) {
        this.outputStreamWrapper = outputStreamWrapper;
    }

    private void write(@NotNull ModbusMaskWriteRegisterResponseMessage maskWriteRegisterResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(maskWriteRegisterResponseMessage.address() + " "
                + maskWriteRegisterResponseMessage.orMask() + " " + maskWriteRegisterResponseMessage.andMask());
    }

    private void write(@NotNull ModbusReadCoilsResponseMessage readCoilsResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(ModbusProxyBitArrayEncoder.encode(readCoilsResponseMessage.values()));
    }

    private void write(@NotNull ModbusReadDiscreteInputsResponseMessage readDiscreteInputsResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(ModbusProxyBitArrayEncoder.encode(readDiscreteInputsResponseMessage.values()));
    }

    private void write(@NotNull ModbusReadHoldingRegistersResponseMessage readHoldingRegistersResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(readHoldingRegistersResponseMessage.values().length + "");
        outputStreamWrapper.write(readHoldingRegistersResponseMessage.values());
    }

    private void write(@NotNull ModbusReadInputRegistersResponseMessage readInputRegistersResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(readInputRegistersResponseMessage.values().length + "");
        outputStreamWrapper.write(readInputRegistersResponseMessage.values());
    }

    private void write(@NotNull ModbusReadWriteMultipleRegistersResponseMessage readWriteMultipleRegistersResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(readWriteMultipleRegistersResponseMessage.values().length + "");
        outputStreamWrapper.write(readWriteMultipleRegistersResponseMessage.values());
    }

    private void write(@NotNull ModbusWriteMultipleRegistersResponseMessage readWriteMultipleRegistersResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(readWriteMultipleRegistersResponseMessage.address() + " " + readWriteMultipleRegistersResponseMessage.quantity());
    }

    private void write(@NotNull ModbusWriteMultipleCoilsResponseMessage writeMultipleCoilsResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(writeMultipleCoilsResponseMessage.address() + " " + writeMultipleCoilsResponseMessage.quantity());
    }

    private void write(@NotNull ModbusWriteSingleCoilResponseMessage writeSingleCoilResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(writeSingleCoilResponseMessage.address() + " "
                + (writeSingleCoilResponseMessage.value() ? "1" : "0"));
    }

    private void write(@NotNull ModbusWriteSingleRegisterResponseMessage writeSingleRegisterResponseMessage) throws IOException {
        outputStreamWrapper.writeLine(writeSingleRegisterResponseMessage.address() + " " + writeSingleRegisterResponseMessage.value());
    }

    public void write(@NotNull ModbusResponseMessage responseMessage) throws IOException {
        if (responseMessage instanceof ModbusMaskWriteRegisterResponseMessage maskWriteRegisterResponseMessage) {
            write(maskWriteRegisterResponseMessage);
        } else if (responseMessage instanceof ModbusReadCoilsResponseMessage readCoilsResponseMessage) {
            write(readCoilsResponseMessage);
        } else if (responseMessage instanceof ModbusReadDiscreteInputsResponseMessage readDiscreteInputsResponseMessage) {
            write(readDiscreteInputsResponseMessage);
        } else if (responseMessage instanceof ModbusReadHoldingRegistersResponseMessage readHoldingRegistersResponseMessage) {
            write(readHoldingRegistersResponseMessage);
        } else if (responseMessage instanceof ModbusReadInputRegistersResponseMessage modbusReadInputRegistersResponseMessage) {
            write(modbusReadInputRegistersResponseMessage);
        } else if (responseMessage instanceof ModbusReadWriteMultipleRegistersResponseMessage readWriteMultipleRegistersResponseMessage) {
            write(readWriteMultipleRegistersResponseMessage);
        } else if (responseMessage instanceof ModbusWriteMultipleCoilsResponseMessage writeMultipleCoilsResponseMessage) {
            write(writeMultipleCoilsResponseMessage);
        } else if (responseMessage instanceof ModbusWriteSingleCoilResponseMessage writeSingleCoilResponseMessage) {
            write(writeSingleCoilResponseMessage);
        } else if (responseMessage instanceof ModbusWriteSingleRegisterResponseMessage writeSingleRegisterResponseMessage) {
            write(writeSingleRegisterResponseMessage);
        } else if (responseMessage instanceof ModbusWriteMultipleRegistersResponseMessage writeMultipleRegistersResponseMessage) {
            write(writeMultipleRegistersResponseMessage);
        }

        outputStreamWrapper.flush();
    }
}
