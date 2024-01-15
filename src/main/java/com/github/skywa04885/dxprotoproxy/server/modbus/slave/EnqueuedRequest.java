package com.github.skywa04885.dxprotoproxy.server.modbus.slave;

import com.github.skywa04885.dxprotoproxy.server.modbus.requests.ModbusRequestMessage;
import com.github.skywa04885.dxprotoproxy.server.modbus.responses.ModbusResponseMessage;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public record EnqueuedRequest(@NotNull CompletableFuture<ModbusResponseMessage> completableFuture,
                              @NotNull ModbusRequestMessage modbusRequestMessage) {
}
