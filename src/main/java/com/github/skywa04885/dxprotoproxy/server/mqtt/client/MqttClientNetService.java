package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTConfig;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutService;
import com.github.skywa04885.dxprotoproxy.server.net.NetService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MqttClientNetService extends NetOutService {
    class Active extends NetOutService.Active {

        @Override
        protected List<NetOutConn> createConnectionsImpl() {
            return mqttConfig.mqttClientsConfig().children().stream().map(mqttClientConfig -> {
                return (NetOutConn) new MqttClientNetConn(mqttClientConfig);
            }).toList();
        }
    }

    private final @NotNull MQTTConfig mqttConfig;

    public MqttClientNetService(@NotNull MQTTConfig mqttConfig) throws Exception {
        this.mqttConfig = mqttConfig;
    }

    @Override
    protected NetService.Active createActiveState() throws Exception {
        return new Active();
    }

    @Override
    public @NotNull ObservableStringValue getName() {
        return Bindings.createStringBinding(() -> "Mqtt client");
    }

    @Override
    public @NotNull ObservableStringValue getDescription() {
        return Bindings.createStringBinding(() -> "Allows PLCs to connect to an MQTT bus");
    }
}
