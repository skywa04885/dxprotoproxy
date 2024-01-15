package com.github.skywa04885.dxprotoproxy.server.modbus;

import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ModbusProxyRequestTypeReader {
    private final @NotNull NetInputStreamWrapper inputStreamWrapper;

    public ModbusProxyRequestTypeReader(@NotNull NetInputStreamWrapper inputStreamWrapper) {
        this.inputStreamWrapper = inputStreamWrapper;
    }

    public @Nullable ModbusProxyRequestType read() throws IOException {
        String opcode = inputStreamWrapper.readStringUntilNewLine();
        if (opcode == null) return null;

        opcode = opcode.trim().toUpperCase();

        for (final ModbusProxyRequestType requestType : ModbusProxyRequestType.values()) {
            if (requestType.opcode().equals(opcode)) return requestType;
        }

        throw new RuntimeException("Unrecognized request type opcode: " + opcode);
    }
}
