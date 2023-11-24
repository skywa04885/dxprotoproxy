package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.Window;

import java.io.IOException;

public class CreateInstanceWindow extends Window<CreateInstanceController> {
    private final ICreateInstanceCallbacks callbacks;

    /**
     * Creates a new create instance window for the given http config api.
     * @param callbacks the callbacks.
     * @throws IOException gets thrown if the resource could not be loaded.
     */
    public CreateInstanceWindow(ICreateInstanceCallbacks callbacks) throws IOException {
        super();

        this.callbacks = callbacks;
        controller = new CreateInstanceController(this);

        loadView(CreateInstanceWindow.class.getResource("view.fxml"));
    }

    /**
     * Gets the callbacks.
     * @return the callbacks.
     */
    public ICreateInstanceCallbacks getCallbacks() {
        return this.callbacks;
    }
}
