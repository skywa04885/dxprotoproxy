package com.github.skywa04885.dxprotoproxy.server.modbus.master;

import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusConfig;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutService;
import com.github.skywa04885.dxprotoproxy.server.net.NetService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModbusMasterNetService extends NetOutService {
    class Active extends NetOutService.Active {
        @Override
        protected List<NetOutConn> createConnectionsImpl() {
            return modbusConfig.mastersConfig().children().stream().map(masterConfig -> {
                return (NetOutConn) new ModbusMasterNetConn(masterConfig);
            }).toList();
        }
    }

    private final @NotNull ModbusConfig modbusConfig;

    public ModbusMasterNetService(@NotNull ModbusConfig modbusConfig) throws Exception {
        this.modbusConfig = modbusConfig;
    }

    @Override
    protected NetService.Active createActiveState() throws Exception {
        return new Active();
    }

    @Override
    public @NotNull ObservableStringValue getName() {
        return Bindings.createStringBinding(() -> "Modbus master");
    }

    @Override
    public @NotNull ObservableStringValue getDescription() {
        return Bindings.createStringBinding(() -> "Allow controllers to act as modbus masters");
    }
}
