package com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree;

import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusMasterConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusMastersConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusSlaveConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusSlavesConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientsConfig;
import org.jetbrains.annotations.NotNull;

public interface IPrimaryTreeContextMenuCallbacks {
    void createResponse(DXHttpConfigRequest request);

    void createApi(@NotNull HttpConfigApis httpConfigApis);

    void modifyApi(@NotNull DXHttpConfigApi httpConfigApi);

    void deleteApi(@NotNull DXHttpConfigApi httpConfigApi);

    void createInstance(@NotNull HttpConfigInstances httpConfigInstances);

    void modifyInstance(@NotNull DXHttpConfigInstance httpConfigInstance);

    void deleteInstance(@NotNull DXHttpConfigInstance httpConfigInstance);

    void createEndpoint(@NotNull HttpConfigEndpoints httpConfigEndpoints);

    /**
     * Deletes the given endpoint.
     *
     * @param configEndpoint the endpoint to delete.
     */
    void deleteEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint);

    /**
     * Modifies the given endpoint.
     *
     * @param configEndpoint the endpoint to modify.
     */
    void modifyEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint);

    void createRequest(@NotNull DXHttpConfigEndpoint httpConfigEndpoint);

    /**
     * This callback gets called when the given response needs to be edited.
     *
     * @param configResponse the response that needs to be modified.
     */
    void modifyResponse(@NotNull DXHttpConfigResponse configResponse);

    /**
     * This callback gets called when the given response needs to be deleted.
     *
     * @param configResponse the response that needs to be deleted.
     */
    void deleteResponse(@NotNull DXHttpConfigResponse configResponse);

    /**
     * Gets called when the given request needs to be deleted.
     *
     * @param configRequest the request that needs to be deleted.
     */
    void deleteRequest(@NotNull DXHttpConfigRequest configRequest);

    /**
     * Gets called when the given request needs to be modified.
     *
     * @param configRequest the request that needs to be modified.
     */
    void modifyRequest(@NotNull DXHttpConfigRequest configRequest);

    /**
     * Gets called when a new mqtt client needs to be created in the given clients config.
     *
     * @param mqttClientsConfig the clients config to create the client in.
     */
    void createMqttClient(@NotNull MQTTClientsConfig mqttClientsConfig);

    /**
     * Gets called when the given mqtt client needs to be deleted.
     *
     * @param mqttClientConfig the mqtt client to delete.
     */
    void deleteMqttClient(@NotNull MQTTClientConfig mqttClientConfig);

    /**
     * Gets called when the given mqtt client config needs to be modified.
     *
     * @param mqttClientConfig the mqtt client config that needs to be modified.
     */
    void modifyMqttClient(@NotNull MQTTClientConfig mqttClientConfig);

    /**
     * Gets called when a new modbus slave should be created.
     * @param config the slaves-config.
     */
    void createModbusSlave(@NotNull ModbusSlavesConfig config);

    /**
     * Gets called when an ew modbus master should be created.
     * @param config the masters-config.
     */
    void createModbusMaster(@NotNull ModbusMastersConfig config);

    void modifyModbusMaster(@NotNull ModbusMasterConfig modbusMasterConfig);

    void deleteModbusMaster(@NotNull ModbusMasterConfig modbusMasterConfig);

    void modifyModbusSlave(@NotNull ModbusSlaveConfig modbusMasterConfig);

    void deleteModbusSlave(@NotNull ModbusSlaveConfig modbusMasterConfig);
}
