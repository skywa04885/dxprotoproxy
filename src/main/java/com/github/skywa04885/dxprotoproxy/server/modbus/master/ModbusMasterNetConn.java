package com.github.skywa04885.dxprotoproxy.server.modbus.master;

import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.responses.ModbusResponse;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusMasterConfig;
import com.github.skywa04885.dxprotoproxy.server.modbus.*;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessage;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessageReader;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessage;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessageFactory;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessageWriter;
import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import io.netty.util.ReferenceCountUtil;
import org.jetbrains.annotations.NotNull;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class ModbusMasterNetConn extends NetOutConn {
    private final @NotNull Logger logger = Logger.getLogger(getClass().getName());
    private final @NotNull ModbusMasterConfig modbusMasterConfig;

    public ModbusMasterNetConn(@NotNull ModbusMasterConfig modbusMasterConfig) {
        super(modbusMasterConfig.serverAddress(), modbusMasterConfig.serverPort());

        this.modbusMasterConfig = modbusMasterConfig;
    }

    @Override
    protected void runImpl(@NotNull NetOutputStreamWrapper outputStreamWrapper, @NotNull NetInputStreamWrapper inputStreamWrapper) {
        final ModbusTcpMasterConfig modbusTcpMasterConfig = new ModbusTcpMasterConfig.Builder(modbusMasterConfig.listenAddress())
                .setPort(modbusMasterConfig.listenPort())
                .build();

        // Create the tcp master.
        final @NotNull ModbusTcpMaster modbusTcpMaster = new ModbusTcpMaster(modbusTcpMasterConfig);

        logger.info("Connecting master to " + modbusMasterConfig.listenAddress() + ":" +
                modbusMasterConfig.listenPort());

        // Connect the modbus tcp master.
        modbusTcpMaster.connect();

        // Create the proxy request type reader.
        final @NotNull ModbusProxyRequestTypeReader modbusProxyRequestTypeReader =
                new ModbusProxyRequestTypeReader(inputStreamWrapper);

        // Create the proxy request message reader.
        final @NotNull ModbusRequestMessageReader modbusRequestMessageReader =
                new ModbusRequestMessageReader(inputStreamWrapper);

        // Create the proxy response message writer.
        final @NotNull ModbusResponseMessageWriter modbusResponseMessageWriter
                = new ModbusResponseMessageWriter(outputStreamWrapper);

        // Stay in loop and process as long as possible.
        try {
            while (true) {
                // Read the request type.
                final @Nullable ModbusProxyRequestType modbusProxyRequestType = modbusProxyRequestTypeReader.read();
                if (modbusProxyRequestType == null) break;

                // Read the request message.
                final @Nullable ModbusRequestMessage modbusRequestMessage =
                        modbusRequestMessageReader.read(modbusProxyRequestType);
                if (modbusRequestMessage == null) break;

                // Create the modbus request from the read proxy request message.
                final @NotNull ModbusRequest modbusRequest = ModbusProxyRequestFactory.create(modbusRequestMessage);

                // Send the request and get the response.
                final @NotNull ModbusResponse modbusResponse = modbusTcpMaster.sendRequest(modbusRequest,
                        modbusRequestMessage.unitId()).get();

                // Create the modbus proxy response message from the modbus response.
                final @NotNull ModbusResponseMessage modbusResponseMessage =
                        ModbusResponseMessageFactory.create(modbusRequest, modbusResponse);

                // Write the modbus proxy response message to the client.
                modbusResponseMessageWriter.write(modbusResponseMessage);

                // Release the request and the response.
                ReferenceCountUtil.release(modbusRequest);
                ReferenceCountUtil.release(modbusResponse);
            }
        } catch (@NotNull IOException ioException) {
            logger.severe("Got io exception, message: " + ioException.getMessage());
        } catch (@NotNull ExecutionException executionException) {
            logger.severe("Got execution exception, message: " + executionException.getMessage());
        } catch (@NotNull RuntimeException runtimeException) {
            logger.severe("Got runtime exception, message: " + runtimeException.getMessage());
        } catch (@NotNull InterruptedException ignored) {
        }

        // Disconnect the client.
        modbusTcpMaster.disconnect();
    }
}
