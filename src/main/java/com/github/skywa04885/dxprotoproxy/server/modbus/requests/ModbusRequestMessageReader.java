package com.github.skywa04885.dxprotoproxy.server.modbus.requests;

import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayDecoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyRequestType;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.*;
import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class ModbusRequestMessageReader {
    private final @NotNull NetInputStreamWrapper inputStreamWrapper;

    public ModbusRequestMessageReader(@NotNull NetInputStreamWrapper inputStreamWrapper) {
        this.inputStreamWrapper = inputStreamWrapper;
    }

    public @Nullable ModbusMaskWriteRegisterRequestMessage readMaskWriteRegisterRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 3)
            throw new RuntimeException("Invalid number of line segments for mask write register request line");

        final String addressLineSegment = lineSegments[0].trim();
        final String orMaskLineSegment = lineSegments[1].trim();
        final String andMaskLineSegment = lineSegments[2].trim();

        int address;
        int orMask;
        int andMask;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in mask write register request line");
        }

        try {
            orMask = Integer.parseInt(orMaskLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid or mask in mask write register request line");
        }

        try {
            andMask = Integer.parseInt(andMaskLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid and mask in mask write register request line");
        }

        return new ModbusMaskWriteRegisterRequestMessage(unitId, address, orMask, andMask);
    }

    public @Nullable ModbusReadCoilsRequestMessage readReadCoilsRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2) throw new RuntimeException("Invalid number of segments in coils request line");

        final String addressLineSegment = lineSegments[0];
        final String quantityLineSegment = lineSegments[1];

        int address;
        int quantity;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in coils request line");
        }

        try {
            quantity = Integer.parseInt(quantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quantity in coils request line");
        }

        return new ModbusReadCoilsRequestMessage(unitId, address, quantity);
    }

    public @Nullable ModbusReadDiscreteInputsRequestMessage readDiscreteInputsRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2)
            throw new RuntimeException("Invalid number of segments in discrete inputs read request line");

        final String addressLineSegment = lineSegments[0];
        final String quantityLineSegment = lineSegments[1];

        int address;
        int quantity;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in discrete inputs read request line");
        }

        try {
            quantity = Integer.parseInt(quantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quantity in discrete inputs read request line");
        }

        return new ModbusReadDiscreteInputsRequestMessage(unitId, address, quantity);
    }

    public @Nullable ModbusReadHoldingRegistersRequestMessage readHoldingRegistersRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2)
            throw new RuntimeException("Invalid number of segments in read holding register request line");

        final String addressLineSegment = lineSegments[0];
        final String quantityLineSegment = lineSegments[1];

        int address;
        int quantity;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in read holding register request line");
        }

        try {
            quantity = Integer.parseInt(quantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quantity in read holding register request line");
        }

        return new ModbusReadHoldingRegistersRequestMessage(unitId, address, quantity);
    }

    public @Nullable ModbusReadInputRegistersRequestMessage readReadInputRegistersRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2)
            throw new RuntimeException("Invalid number of segments in read input registers request line");

        final String addressLineSegment = lineSegments[0];
        final String quantityLineSegment = lineSegments[1];

        int address;
        int quantity;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in read input registers request line");
        }

        try {
            quantity = Integer.parseInt(quantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quantity in read input registers request line");
        }

        return new ModbusReadInputRegistersRequestMessage(unitId, address, quantity);
    }

    public @Nullable ModbusReadWriteMultipleRegistersRequestMessage readReadWriteMultipleRegistersRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 5) throw new RuntimeException("Invalid number of segments in read write multiple " +
                "registers request");

        final String readAddressLineSegment = lineSegments[0];
        final String readQuantityLineSegment = lineSegments[1];
        final String writeAddressLineSegment = lineSegments[2];
        final String writeQuantityLineSegment = lineSegments[3];
        final String noBytesForWritingLineSegment = lineSegments[4];

        int readAddress;
        int readQuantity;
        int writeAddress;
        int writeQuantity;
        int noBytesForWriting;

        try {
            readAddress = Integer.parseInt(readAddressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid read address in read write multiple registers request");
        }

        try {
            readQuantity = Integer.parseInt(readQuantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid read quantity in read write multiple registers request");
        }

        try {
            writeAddress = Integer.parseInt(writeAddressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid write address in read write multiple registers request");
        }

        try {
            writeQuantity = Integer.parseInt(writeQuantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid write quantity in read write multiple registers request");
        }

        try {
            noBytesForWriting = Integer.parseInt(noBytesForWritingLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid no bytes for writing in read write multiple registers request");
        }

        final byte[] writeValues = inputStreamWrapper.readNBytes(noBytesForWriting);
        if (writeValues == null) return null;

        return new ModbusReadWriteMultipleRegistersRequestMessage(unitId, readAddress, readQuantity, writeAddress,
                writeQuantity, writeValues);
    }

    public @Nullable ModbusWriteMultipleCoilsRequestMessage readWriteMultipleCoilsRequestMessage(int unitId) throws IOException {
        final String addressLine = inputStreamWrapper.readStringUntilNewLine();
        if (addressLine == null) return null;

        final String valuesLine = inputStreamWrapper.readStringUntilNewLine();
        if (valuesLine == null) return null;

        int address;

        try {
            address = Integer.parseInt(addressLine);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in address line of write multiple coils request");
        }

        List<Boolean> values;

        try {
            values = ModbusProxyBitArrayDecoder.decodeFromString(valuesLine);
        } catch (final RuntimeException runtimeException) {
            throw new RuntimeException("Invalid values given in values line of write multiple coils request");
        }

        return new ModbusWriteMultipleCoilsRequestMessage(unitId, address, values);
    }

    public @Nullable ModbusWriteMultipleRegistersRequestMessage readWriteMultipleRegistersRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 3) throw new RuntimeException("Invalid number of segments in read write multiple " +
                "registers request");

        final String addressLineSegment = lineSegments[0];
        final String quantityLineSegment = lineSegments[1];
        final String noBytesLineSegment = lineSegments[2];

        int address;
        int quantity;
        int noBytes;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in write multiple registers request line");
        }

        try {
            quantity = Integer.parseInt(quantityLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quantity in write multiple registers request line");
        }

        try {
            noBytes = Integer.parseInt(noBytesLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid no bytes in write multiple registers request line");
        }

        final byte[] values = inputStreamWrapper.readNBytes(noBytes);
        if (values == null) return null;

        return new ModbusWriteMultipleRegistersRequestMessage(unitId, address, quantity, values);
    }

    public @Nullable ModbusWriteSingleCoilRequestMessage readWriteSingleCoilRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2)
            throw new RuntimeException("Invalid number of segments for write single coil request");

        final String addressLineSegment = lineSegments[0].trim();
        final String valueLineSegment = lineSegments[1].trim();

        int address;
        boolean value;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in line for write single coil request");
        }

        if (valueLineSegment.equals("1")) value = true;
        else if (valueLineSegment.equals("0")) value = false;
        else throw new RuntimeException("Invalid value in line for write single coil request");

        return new ModbusWriteSingleCoilRequestMessage(unitId, address, value);
    }

    public @Nullable ModbusWriteSingleRegisterRequestMessage readWriteSingleRegisterRequestMessage(int unitId) throws IOException {
        final String line = inputStreamWrapper.readStringUntilNewLine();
        if (line == null) return null;

        final String[] lineSegments = line.trim().split(" +");
        if (lineSegments.length != 2)
            throw new RuntimeException("Invalid number of line segments for write single register request");

        final String addressLineSegment = lineSegments[0].trim();
        final String valueLineSegment = lineSegments[1].trim();

        int address;
        int value;

        try {
            address = Integer.parseInt(addressLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in line for write single register request");
        }

        try {
            value = Integer.parseInt(valueLineSegment);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid value in line for write single register request");
        }

        return new ModbusWriteSingleRegisterRequestMessage(unitId, address, value);
    }

    public @Nullable ModbusRequestMessage read(@NotNull ModbusProxyRequestType modbusProxyRequestType) throws IOException {
        @Nullable String unitIdLine = inputStreamWrapper.readStringUntilNewLine();
        if (unitIdLine == null) return null;

        int unitId;

        try {
            unitId = Integer.parseInt(unitIdLine);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid address in line for write single register request");
        }

        return switch (modbusProxyRequestType) {
            case MaskWriteRegister -> readMaskWriteRegisterRequestMessage(unitId);
            case ReadCoils -> readReadCoilsRequestMessage(unitId);
            case ReadDiscreteInputs -> readDiscreteInputsRequestMessage(unitId);
            case ReadHoldingRegisters -> readHoldingRegistersRequestMessage(unitId);
            case ReadInputRegisters -> readReadInputRegistersRequestMessage(unitId);
            case ReadWriteMultipleRegisters -> readReadWriteMultipleRegistersRequestMessage(unitId);
            case WriteMultipleCoils -> readWriteMultipleCoilsRequestMessage(unitId);
            case WriteMultipleRegisters -> readWriteMultipleRegistersRequestMessage(unitId);
            case WriteSingleCoil -> readWriteSingleCoilRequestMessage(unitId);
            case WriteSingleRegister -> readWriteSingleRegisterRequestMessage(unitId);
        };
    }
}
