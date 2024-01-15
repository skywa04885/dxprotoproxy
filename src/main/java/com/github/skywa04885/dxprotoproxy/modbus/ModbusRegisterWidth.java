package com.github.skywa04885.dxprotoproxy.modbus;

import org.jetbrains.annotations.NotNull;

public enum ModbusRegisterWidth {
    HalfWord("Half word", 2),
    Word("Word", 4);

    private final @NotNull String label;
    private final int width;

    ModbusRegisterWidth(@NotNull String label, int width) {
        this.label = label;
        this.width = width;
    }

    public @NotNull String label() {
        return label;
    }

    public int width() {
        return width;
    }
}
