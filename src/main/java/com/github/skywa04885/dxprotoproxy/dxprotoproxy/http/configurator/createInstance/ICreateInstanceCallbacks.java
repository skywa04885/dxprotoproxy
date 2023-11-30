package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createInstance;

public interface ICreateInstanceCallbacks {
    /**
     * Gets called when the create instance window is about to submit and needs to validate the values.
     * @param name the name.
     * @param host the host.
     * @param port the port.
     * @param protocol the protocol.
     * @return null if the values are valid, otherwise an error message.
     */
    String validate(String name, String host, int port, String protocol);

    /**
     * Gets called when the create instance windows submits.
     * @param name the name.
     * @param host the host.
     * @param port the port.
     * @param protocol the protocol.
     */
    void submit(String name, String host, int port, String protocol);
}
