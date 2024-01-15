package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.digitalpetri.modbus.requests.*;
import com.github.skywa04885.dxprotoproxy.server.modbus.ModbusProxyBitArrayEncoder;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.*;

public class ModbusProxyRequestFactory {
    public static ModbusRequest create(final ModbusRequestMessage requestMessage) {
        if (requestMessage instanceof ModbusMaskWriteRegisterRequestMessage maskWriteRegisterRequestMessage) {
            return new MaskWriteRegisterRequest(maskWriteRegisterRequestMessage.address(),
                    maskWriteRegisterRequestMessage.andMask(), maskWriteRegisterRequestMessage.orMask());
        }

        if (requestMessage instanceof ModbusReadCoilsRequestMessage readCoilsRequestMessage) {
            return new ReadCoilsRequest(readCoilsRequestMessage.address(), readCoilsRequestMessage.quantity());
        }

        if (requestMessage instanceof ModbusReadDiscreteInputsRequestMessage readDiscreteInputsRequestMessage) {
            return new ReadDiscreteInputsRequest(readDiscreteInputsRequestMessage.address(),
                    readDiscreteInputsRequestMessage.quantity());
        }

        if (requestMessage instanceof ModbusReadHoldingRegistersRequestMessage readHoldingRegistersRequestMessage) {
            return new ReadHoldingRegistersRequest(readHoldingRegistersRequestMessage.address(),
                    readHoldingRegistersRequestMessage.quantity());
        }

        if (requestMessage instanceof ModbusReadInputRegistersRequestMessage readInputRegistersRequestMessage) {
            return new ReadInputRegistersRequest(readInputRegistersRequestMessage.address(),
                    readInputRegistersRequestMessage.quantity());
        }

        if (requestMessage instanceof ModbusReadWriteMultipleRegistersRequestMessage readWriteMultipleRegistersRequestMessage) {
            return new ReadWriteMultipleRegistersRequest(readWriteMultipleRegistersRequestMessage.readAddress(),
                    readWriteMultipleRegistersRequestMessage.readQuantity(),
                    readWriteMultipleRegistersRequestMessage.writeAddress(),
                    readWriteMultipleRegistersRequestMessage.writeQuantity(),
                    readWriteMultipleRegistersRequestMessage.writeValues());
        }

        if (requestMessage instanceof ModbusWriteMultipleCoilsRequestMessage writeMultipleCoilsRequestMessage) {
            return new WriteMultipleCoilsRequest(writeMultipleCoilsRequestMessage.address(),
                    writeMultipleCoilsRequestMessage.values().size(),
                    ModbusProxyBitArrayEncoder.encodeToBinary(writeMultipleCoilsRequestMessage.values()));
        }

        if (requestMessage instanceof ModbusWriteMultipleRegistersRequestMessage writeMultipleRegistersRequestMessage) {
            return new WriteMultipleRegistersRequest(writeMultipleRegistersRequestMessage.address(),
                    writeMultipleRegistersRequestMessage.quantity(), writeMultipleRegistersRequestMessage.values());
        }

        if (requestMessage instanceof ModbusWriteSingleCoilRequestMessage writeSingleCoilRequestMessage) {
            return new WriteSingleCoilRequest(writeSingleCoilRequestMessage.address(),
                    writeSingleCoilRequestMessage.value());
        }

        if (requestMessage instanceof ModbusWriteSingleRegisterRequestMessage writeSingleRegisterRequestMessage) {
            return new WriteSingleRegisterRequest(writeSingleRegisterRequestMessage.address(),
                    writeSingleRegisterRequestMessage.value());
        }

        throw new RuntimeException("Unrecognized modbus proxy request message");
    }
}
