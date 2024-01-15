package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.digitalpetri.modbus.requests.*;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.*;
import io.netty.buffer.ByteBufUtil;
import org.jetbrains.annotations.NotNull;

public class ModbusRequestFactory {
    public static @NotNull ModbusRequestMessage create(int unitId, final @NotNull ModbusRequest modbusRequest) {
        if (modbusRequest instanceof MaskWriteRegisterRequest maskWriteRegisterRequest) {
            return new ModbusMaskWriteRegisterRequestMessage(unitId, maskWriteRegisterRequest.getAddress(),
                    maskWriteRegisterRequest.getOrMask(), maskWriteRegisterRequest.getAndMask());
        }

        if (modbusRequest instanceof ReadCoilsRequest readCoilsRequest) {
            return new ModbusReadCoilsRequestMessage(unitId, readCoilsRequest.getAddress(),
                    readCoilsRequest.getQuantity());
        }

        if (modbusRequest instanceof ReadDiscreteInputsRequest readDiscreteInputsRequest) {
            return new ModbusReadDiscreteInputsRequestMessage(unitId, readDiscreteInputsRequest.getAddress(),
                    readDiscreteInputsRequest.getQuantity());
        }

        if (modbusRequest instanceof ReadHoldingRegistersRequest readHoldingRegistersRequest) {
            return new ModbusReadHoldingRegistersRequestMessage(unitId, readHoldingRegistersRequest.getAddress(),
                    readHoldingRegistersRequest.getQuantity());
        }

        if (modbusRequest instanceof ReadInputRegistersRequest readInputRegistersRequest) {
            return new ModbusReadInputRegistersRequestMessage(unitId, readInputRegistersRequest.getAddress(),
                    readInputRegistersRequest.getQuantity());
        }

        if (modbusRequest instanceof ReadWriteMultipleRegistersRequest readWriteMultipleRegistersRequest) {
            final byte[] readValues = ByteBufUtil.getBytes(readWriteMultipleRegistersRequest.getValues());
            return new ModbusReadWriteMultipleRegistersRequestMessage(unitId,
                    readWriteMultipleRegistersRequest.getReadAddress(), readWriteMultipleRegistersRequest.getReadQuantity(),
                    readWriteMultipleRegistersRequest.getWriteAddress(), readWriteMultipleRegistersRequest.getWriteQuantity(),
                    readValues);
        }

        if (modbusRequest instanceof WriteMultipleCoilsRequest writeMultipleCoilsRequest) {
            final byte[] values = ByteBufUtil.getBytes(writeMultipleCoilsRequest.getValues());
            return new ModbusWriteMultipleCoilsRequestMessage(unitId, writeMultipleCoilsRequest.getAddress(),
                    ModbusProxyBitArrayDecoder.decodeFromBinary(values, writeMultipleCoilsRequest.getQuantity()));
        }

        if (modbusRequest instanceof WriteMultipleRegistersRequest writeMultipleRegistersRequest) {
            final byte[] values = ByteBufUtil.getBytes(writeMultipleRegistersRequest.getValues());
            return new ModbusWriteMultipleRegistersRequestMessage(unitId, writeMultipleRegistersRequest.getAddress(),
                    writeMultipleRegistersRequest.getQuantity(), values);
        }

        if (modbusRequest instanceof WriteSingleCoilRequest writeSingleCoilRequest) {
            return new ModbusWriteSingleCoilRequestMessage(unitId, writeSingleCoilRequest.getAddress(),
                    writeSingleCoilRequest.getValue() == 0xFF00);
        }

        if (modbusRequest instanceof WriteSingleRegisterRequest writeSingleRegisterRequest) {
            return new ModbusWriteSingleRegisterRequestMessage(unitId, writeSingleRegisterRequest.getAddress(),
                    writeSingleRegisterRequest.getValue());
        }

        throw new RuntimeException("Unrecognized modbus request");
    }
}
