package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ModbusProxyRequestTypeWriter {
    private final @NotNull NetOutputStreamWrapper outputStreamWrapper;

    public ModbusProxyRequestTypeWriter(@NotNull NetOutputStreamWrapper outputStreamWrapper) {
        this.outputStreamWrapper = outputStreamWrapper;
    }

    public void write(@NotNull ModbusProxyRequestType modbusProxyRequestType) throws IOException {
        outputStreamWrapper.writeLine(modbusProxyRequestType.opcode());
    }
}
