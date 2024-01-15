package com.github.skywa04885.dxprotoproxy.server.modbus.slave;

import com.digitalpetri.modbus.slave.ModbusTcpSlave;
import com.digitalpetri.modbus.slave.ModbusTcpSlaveConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusSlaveConfig;
import com.github.skywa04885.dxprotoproxy.server.modbus.*;
import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessageWriter;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessage;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessageReader;
import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class ModbusSlaveNetConn extends NetOutConn  {
    private final @NotNull Logger logger = Logger.getLogger(getClass().getName());
    private final @NotNull ModbusSlaveConfig modbusSlaveConfig;

    public ModbusSlaveNetConn(@NotNull ModbusSlaveConfig modbusSlaveConfig) {
        super(modbusSlaveConfig.controllerAddress(), modbusSlaveConfig.controllerPort());

        this.modbusSlaveConfig = modbusSlaveConfig;
    }

    @Override
    protected void runImpl(@NotNull NetOutputStreamWrapper outputStreamWrapper, @NotNull NetInputStreamWrapper inputStreamWrapper) {
        // Create queue that will contain all the enqueued requests (due to the async nature
        //  of the library I'm using).
        final @NotNull ArrayBlockingQueue<EnqueuedRequest> enqueuedRequests = new ArrayBlockingQueue<>(100);

        // Create the modbus proxy request message writer.
        final @NotNull ModbusRequestMessageWriter modbusRequestMessageWriter =
                new ModbusRequestMessageWriter(outputStreamWrapper);

        // Create the modbus proxy request-type writer.
        final @NotNull ModbusProxyRequestTypeWriter modbusProxyRequestTypeWriter
                = new ModbusProxyRequestTypeWriter(outputStreamWrapper);

        // Create the modbus proxy response message reader.
        final @NotNull ModbusResponseMessageReader modbusResponseMessageReader
                = new ModbusResponseMessageReader(inputStreamWrapper);

        logger.info("Binding slave to " + modbusSlaveConfig.masterAddress() + ":" +
                modbusSlaveConfig.masterPort());

        final ModbusTcpSlaveConfig modbusTcpSlaveConfig = new ModbusTcpSlaveConfig.Builder().build();
        final ModbusTcpSlave modbusTcpSlave = new ModbusTcpSlave(modbusTcpSlaveConfig);

        // Set the request handle and bind the modbus tcp slave.
        modbusTcpSlave.setRequestHandler(new ModbusSlaveServiceHandler(enqueuedRequests));
        modbusTcpSlave.bind(modbusSlaveConfig.masterAddress(), modbusSlaveConfig.masterPort());

        // Stay in loop until we cannot process anymore.
        try {
            while (true) {
                EnqueuedRequest enqueuedRequest = enqueuedRequests.take();

                // Get the request type from the request.
                final @NotNull ModbusProxyRequestType modbusProxyRequestType =
                        ModbusProxyRequestTypeFactory.createFromRequestMessage(
                                enqueuedRequest.modbusRequestMessage());

                // Write the request type,
                modbusProxyRequestTypeWriter.write(modbusProxyRequestType);

                // Write the request message to the client.
                modbusRequestMessageWriter.write(enqueuedRequest.modbusRequestMessage());

                // Read the modbus proxy response message, or break from the loop
                //  if the client disconnected.
                final @Nullable ModbusResponseMessage modbusResponseMessage
                        = modbusResponseMessageReader.read(modbusProxyRequestType);
                if (modbusResponseMessage == null) break;

                // Respond with the response.
                enqueuedRequest.completableFuture().complete(modbusResponseMessage);
            }
        } catch (@NotNull InterruptedException ignored) {
        } catch (@NotNull IOException ioException) {
            logger.severe("Got an IOException while handling requests, exception: "
                    + ioException.getMessage());
        } catch (@NotNull RuntimeException runtimeException) {
            logger.severe("Got runtime exception, message: " + runtimeException.getMessage());
        }

        // Shut the modbus tcp slave down.
        modbusTcpSlave.shutdown();
    }
}
