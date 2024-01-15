package com.github.skywa04885.dxprotoproxy.server.modbus.responses;

import com.digitalpetri.modbus.requests.*;
import com.digitalpetri.modbus.responses.*;
import com.github.skywa04885.dxprotoproxy.modbus.ModbusRegisterWidth;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayDecoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusWriteSingleCoilRequestMessage;
import io.netty.buffer.ByteBufUtil;
import org.jetbrains.annotations.NotNull;

public class ModbusResponseMessageFactory {
    public static ModbusResponseMessage create(@NotNull ModbusRequest modbusRequest, @NotNull ModbusResponse modbusResponse) {
        if (modbusResponse instanceof MaskWriteRegisterResponse maskWriteRegisterResponse) {
            return new ModbusMaskWriteRegisterResponseMessage(maskWriteRegisterResponse.getAddress(),
                    maskWriteRegisterResponse.getOrMask(), maskWriteRegisterResponse.getAndMask());
        }

        if (modbusRequest instanceof ReadCoilsRequest readCoilsRequest
                && modbusResponse instanceof ReadCoilsResponse readCoilsResponse) {
            final byte[] values = ByteBufUtil.getBytes(readCoilsResponse.getCoilStatus());
            return new ModbusReadCoilsResponseMessage(ModbusProxyBitArrayDecoder.decodeFromBinary(values,
                    readCoilsRequest.getQuantity()));
        }

        if (modbusRequest instanceof ReadDiscreteInputsRequest readDiscreteInputsRequest
                && modbusResponse instanceof ReadDiscreteInputsResponse readDiscreteInputsResponse) {
            final byte[] values = ByteBufUtil.getBytes(readDiscreteInputsResponse.getInputStatus());
            return new ModbusReadDiscreteInputsResponseMessage(ModbusProxyBitArrayDecoder.decodeFromBinary(values,
                    readDiscreteInputsRequest.getQuantity()));
        }

        if (modbusResponse instanceof ReadHoldingRegistersResponse readHoldingRegistersResponse) {
            final byte[] values = ByteBufUtil.getBytes(readHoldingRegistersResponse.getRegisters());
            return new ModbusReadHoldingRegistersResponseMessage(values);
        }

        if (modbusResponse instanceof ReadInputRegistersResponse readInputRegistersResponse) {
            final byte[] values = ByteBufUtil.getBytes(readInputRegistersResponse.getRegisters());
            return new ModbusReadInputRegistersResponseMessage(values);
        }

        if (modbusResponse instanceof ReadWriteMultipleRegistersResponse readWriteMultipleRegistersResponse) {
            final byte[] readValues = ByteBufUtil.getBytes(readWriteMultipleRegistersResponse.getRegisters());
            return new ModbusReadWriteMultipleRegistersResponseMessage(readValues);
        }

        if (modbusResponse instanceof WriteMultipleCoilsResponse writeMultipleCoilsResponse) {
            return new ModbusWriteMultipleCoilsResponseMessage(writeMultipleCoilsResponse.getAddress(),
                    writeMultipleCoilsResponse.getQuantity());
        }

        if (modbusResponse instanceof WriteMultipleRegistersResponse writeMultipleRegistersResponse) {
            return new ModbusWriteMultipleRegistersResponseMessage(writeMultipleRegistersResponse.getAddress(),
                    writeMultipleRegistersResponse.getQuantity());
        }

        if (modbusResponse instanceof WriteSingleCoilResponse writeSingleCoilResponse) {
            return new ModbusWriteSingleCoilResponseMessage(writeSingleCoilResponse.getAddress(),
                    writeSingleCoilResponse.getValue() == 0xFF00);
        }

        if (modbusResponse instanceof WriteSingleRegisterResponse writeSingleRegisterResponse) {
            return new ModbusWriteSingleRegisterResponseMessage(writeSingleRegisterResponse.getAddress(),
                    writeSingleRegisterResponse.getValue());
        }

        throw new RuntimeException("Invalid modbus response");
    }
}
