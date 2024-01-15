package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.digitalpetri.modbus.responses.*;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.*;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

public class ModbusResponseFactory {
    public static @NotNull ModbusResponse create(@NotNull ModbusResponseMessage modbusResponseMessage) {
        if (modbusResponseMessage instanceof ModbusMaskWriteRegisterResponseMessage modbusMaskWriteRegisterResponseMessage) {
            return new MaskWriteRegisterResponse(modbusMaskWriteRegisterResponseMessage.address(),
                    modbusMaskWriteRegisterResponseMessage.andMask(), modbusMaskWriteRegisterResponseMessage.orMask());
        }

        if (modbusResponseMessage instanceof ModbusReadCoilsResponseMessage readCoilsResponseMessage) {
            return new ReadCoilsResponse(Unpooled.copiedBuffer(ModbusProxyBitArrayEncoder.encodeToBinary(
                    readCoilsResponseMessage.values())));
        }

        if (modbusResponseMessage instanceof ModbusReadDiscreteInputsResponseMessage readDiscreteInputsResponseMessage) {
            return new ReadDiscreteInputsResponse(Unpooled.wrappedBuffer(ModbusProxyBitArrayEncoder.encodeToBinary(
                    readDiscreteInputsResponseMessage.values())));
        }

        if (modbusResponseMessage instanceof ModbusReadHoldingRegistersResponseMessage readHoldingRegistersResponseMessage) {
            return new ReadHoldingRegistersResponse(Unpooled.copiedBuffer(
                    readHoldingRegistersResponseMessage.values()));
        }

        if (modbusResponseMessage instanceof ModbusReadInputRegistersResponseMessage readInputRegistersResponseMessage) {
            return new ReadInputRegistersResponse(Unpooled.copiedBuffer(readInputRegistersResponseMessage.values()));
        }

        if (modbusResponseMessage instanceof ModbusReadWriteMultipleRegistersResponseMessage readWriteMultipleRegistersResponseMessage) {
            return new ReadWriteMultipleRegistersResponse(Unpooled.copiedBuffer(
                    readWriteMultipleRegistersResponseMessage.values()));
        }

        if (modbusResponseMessage instanceof ModbusWriteMultipleCoilsResponseMessage writeMultipleCoilsResponseMessage) {
            return new WriteMultipleCoilsResponse(writeMultipleCoilsResponseMessage.address(),
                    writeMultipleCoilsResponseMessage.quantity());
        }

        if (modbusResponseMessage instanceof ModbusWriteMultipleRegistersResponseMessage writeMultipleRegistersResponseMessage) {
            return new WriteMultipleRegistersResponse(writeMultipleRegistersResponseMessage.address(),
                    writeMultipleRegistersResponseMessage.quantity());
        }

        if (modbusResponseMessage instanceof ModbusWriteSingleCoilResponseMessage writeSingleCoilResponseMessage) {
            return new WriteSingleCoilResponse(writeSingleCoilResponseMessage.address(),
                    writeSingleCoilResponseMessage.value() ? 0xFF00 : 0x0000);
        }

        if (modbusResponseMessage instanceof ModbusWriteSingleRegisterResponseMessage writeSingleRegisterResponseMessage) {
            return new WriteSingleRegisterResponse(writeSingleRegisterResponseMessage.address(),
                    writeSingleRegisterResponseMessage.value());
        }

        throw new RuntimeException("Invalid modbus response or something");
    }
}
