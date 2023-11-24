package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.Window;

import java.io.IOException;

public class CreateApiWindow extends Window<CreateApiController> {
    private final ICreateApiCallbacks callbacks;

    /**
     * Creates a new create instance window for the given http config api.
     * @param callbacks the callbacks.
     * @throws IOException gets thrown if the resource could not be loaded.
     */
    public CreateApiWindow(ICreateApiCallbacks callbacks) throws IOException {
        super();

        this.callbacks = callbacks;
        controller = new CreateApiController(this);

        loadView(CreateApiWindow.class.getResource("view.fxml"));
    }

    /**
     * Gets the callbacks.
     * @return the callbacks.
     */
    public ICreateApiCallbacks getCallbacks() {
        return callbacks;
    }
}
