package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.github.skywa04885.dxprotoproxy.server.modbus.requests.*;
import org.jetbrains.annotations.NotNull;

public class ModbusProxyRequestTypeFactory {
    public static ModbusProxyRequestType createFromRequestMessage(@NotNull ModbusRequestMessage modbusRequestMessage) {
        if (modbusRequestMessage instanceof ModbusMaskWriteRegisterRequestMessage) {
            return ModbusProxyRequestType.MaskWriteRegister;
        }

        if (modbusRequestMessage instanceof ModbusReadCoilsRequestMessage) {
            return ModbusProxyRequestType.ReadCoils;
        }

        if (modbusRequestMessage instanceof ModbusReadDiscreteInputsRequestMessage) {
            return ModbusProxyRequestType.ReadDiscreteInputs;
        }

        if (modbusRequestMessage instanceof ModbusReadHoldingRegistersRequestMessage) {
            return ModbusProxyRequestType.ReadHoldingRegisters;
        }

        if (modbusRequestMessage instanceof ModbusReadInputRegistersRequestMessage) {
            return ModbusProxyRequestType.ReadInputRegisters;
        }

        if (modbusRequestMessage instanceof ModbusReadWriteMultipleRegistersRequestMessage) {
            return ModbusProxyRequestType.ReadWriteMultipleRegisters;
        }

        if (modbusRequestMessage instanceof ModbusWriteMultipleCoilsRequestMessage) {
            return ModbusProxyRequestType.WriteMultipleCoils;
        }

        if (modbusRequestMessage instanceof ModbusWriteMultipleRegistersRequestMessage) {
            return ModbusProxyRequestType.WriteMultipleRegisters;
        }

        if (modbusRequestMessage instanceof ModbusWriteSingleCoilRequestMessage) {
            return ModbusProxyRequestType.WriteSingleCoil;
        }

        if (modbusRequestMessage instanceof ModbusWriteSingleRegisterRequestMessage) {
            return ModbusProxyRequestType.WriteSingleRegister;
        }

        throw new RuntimeException("Cannot construct idk");
    }
}
