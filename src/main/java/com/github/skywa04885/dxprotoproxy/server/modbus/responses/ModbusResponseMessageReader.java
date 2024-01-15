package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

import com.digitalpetri.modbus.responses.WriteMultipleRegistersResponse;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayDecoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyRequestType;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.*;
import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class ModbusResponseMessageReader {
    private final @NotNull NetInputStreamWrapper inputStreamWrapper;

    public ModbusResponseMessageReader(@NotNull NetInputStreamWrapper inputStreamWrapper) {
        this.inputStreamWrapper = inputStreamWrapper;
    }

    private @Nullable ModbusMaskWriteRegisterResponseMessage readMaskWriteRegisterResponseMessage() throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.split(" ");
        if (lineSegments.length != 3) throw new RuntimeException("Invalid response line");

        final String addressLineSegment = lineSegments[0];
        final String orMaskLineSegment = lineSegments[1];
        final String andMaskLineSegment = lineSegments[2];

        int address;
        int orMask;
        int andMask;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Invalid address");
        }

        try {
            orMask = Integer.parseInt(orMaskLineSegment);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Invalid or mask");
        }

        try {
            andMask = Integer.parseInt(andMaskLineSegment);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Invalid and mask");
        }

        return new ModbusMaskWriteRegisterResponseMessage(address, orMask, andMask);
    }

    private @Nullable ModbusReadCoilsResponseMessage readReadCoilsResponseMessage() throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final @NotNull List<Boolean> values = ModbusProxyBitArrayDecoder.decodeFromString(line);

        return new ModbusReadCoilsResponseMessage(values);
    }

    private @Nullable ModbusReadDiscreteInputsResponseMessage readReadDiscreteInputsResponseMessage() throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final @NotNull List<Boolean> values = ModbusProxyBitArrayDecoder.decodeFromString(line);

        return new ModbusReadDiscreteInputsResponseMessage(values);
    }

    private @Nullable ModbusReadHoldingRegistersResponseMessage readReadHoldingRegistersResponseMessage() throws IOException {
        final String nBytesLine = inputStreamWrapper.readStringUntilNewLine();
        if (nBytesLine == null) return null;

        int nBytes;

        try {
            nBytes = Integer.parseInt(nBytesLine);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Received invalid number of bytes");
        }

        final byte[] values = inputStreamWrapper.readNBytes(nBytes);
        if (values == null) return null;

        return new ModbusReadHoldingRegistersResponseMessage(values);
    }

    private @Nullable ModbusReadInputRegistersResponseMessage readReadInputRegistersResponseMessage() throws IOException {
        final String nBytesLine = inputStreamWrapper.readStringUntilNewLine();
        if (nBytesLine == null) return null;

        int nBytes;

        try {
            nBytes = Integer.parseInt(nBytesLine);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Received invalid number of bytes");
        }

        final byte[] values = inputStreamWrapper.readNBytes(nBytes);
        if (values == null) return null;

        return new ModbusReadInputRegistersResponseMessage(values);
    }

    private @Nullable ModbusReadWriteMultipleRegistersResponseMessage readReadWriteMultipleRegistersResponseMessage() throws IOException {
        final String nBytesLine = inputStreamWrapper.readStringUntilNewLine();
        if (nBytesLine == null) return null;

        int nBytes;

        try {
            nBytes = Integer.parseInt(nBytesLine);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Received invalid number of bytes");
        }

        final byte[] values = inputStreamWrapper.readNBytes(nBytes);
        if (values == null) return null;

        return new ModbusReadWriteMultipleRegistersResponseMessage(values);
    }

    private @Nullable ModbusWriteSingleCoilResponseMessage readWriteSingleCoilResponseMessage() throws IOException {
        int address;
        boolean value;

        // Reads the response line.
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        // Splits the line into the segments.
        final String[] lineSegments = line.split(" ");
        if (lineSegments.length != 2) throw new RuntimeException("Invalid response length");

        // Parses the address.
        try {
            address = Integer.parseInt(lineSegments[0]);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid address given");
        }

        // Parses the value.
        try {
            value = Integer.parseInt(lineSegments[1]) > 0;
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid value given");
        }

        return new ModbusWriteSingleCoilResponseMessage(address, value);
    }

    private @Nullable ModbusWriteSingleRegisterResponseMessage readWriteSingleRegisterResponseMessage() throws IOException {
        int address;
        int value;

        // Reads the line and null if the end of the stream is reached.
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        // Splits the line into multiple segments
        final String[] lineSegments = line.split(" ");
        if (lineSegments.length != 2) throw new RuntimeException("Invalid number of line segments");

        // Parses the address.
        try {
            address = Integer.parseInt(lineSegments[0]);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid address given");
        }

        // Parses the value.
        try {
            value = Integer.parseInt(lineSegments[1]);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid value given");
        }

        return new ModbusWriteSingleRegisterResponseMessage(address, value);
    }

    private @Nullable ModbusWriteMultipleRegistersResponseMessage readWriteMultipleRegistersResponseMessage() throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.split(" ");
        if (lineSegments.length != 2) throw new RuntimeException("Unexpected segment amount for write multiple " +
                "registers response");

        final String addressSegment = lineSegments[0];
        final String quantitySegment = lineSegments[1];

        int address;
        int quantity;

        try {
            address = Integer.parseInt(addressSegment);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Got invalid address");
        }

        try {
            quantity = Integer.parseInt(quantitySegment);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Got invalid quantity");
        }

        return new ModbusWriteMultipleRegistersResponseMessage(address, quantity);
    }

    private @Nullable ModbusWriteMultipleCoilsResponseMessage readWriteMultipleCoilsResponseMessage() throws IOException {
        int address;
        int quantity;

        // Reads the line.
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        // Splits the line into the segments.
        final String[] lineSegments = line.split(" ");
        if (lineSegments.length != 2) throw new RuntimeException("Invalid number of segments in line");

        // Parses the address.
        try {
            address = Integer.parseInt(lineSegments[0]);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid address");
        }

        // Parses the quantity.
        try {
            quantity = Integer.parseInt(lineSegments[1]);
        } catch (final NumberFormatException exception) {
            throw new RuntimeException("Invalid quantity");
        }

        return new ModbusWriteMultipleCoilsResponseMessage(address, quantity);
    }

    public @Nullable ModbusResponseMessage read(@NotNull ModbusProxyRequestType modbusProxyRequestType) throws IOException {
        return switch (modbusProxyRequestType) {
            case WriteSingleRegister -> readWriteSingleRegisterResponseMessage();
            case ReadWriteMultipleRegisters -> readReadWriteMultipleRegistersResponseMessage();
            case ReadHoldingRegisters -> readReadHoldingRegistersResponseMessage();
            case WriteSingleCoil -> readWriteSingleCoilResponseMessage();
            case WriteMultipleCoils -> readWriteMultipleCoilsResponseMessage();
            case ReadCoils -> readReadCoilsResponseMessage();
            case WriteMultipleRegisters -> readWriteMultipleRegistersResponseMessage();
            case ReadDiscreteInputs -> readReadDiscreteInputsResponseMessage();
            case ReadInputRegisters -> readReadInputRegistersResponseMessage();
            case MaskWriteRegister -> readMaskWriteRegisterResponseMessage();
        };
    }
}
