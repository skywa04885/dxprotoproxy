package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.Window;

import java.io.IOException;

public class EndpointEditor extends Window<EndpointEditorController> {
    private final IEndpointEditorSaveCallback saveCallback;
    private final IEndpointEditorValidationCallback validationCallback;

    public EndpointEditor(IEndpointEditorSaveCallback saveCallback, IEndpointEditorValidationCallback validationCallback) throws IOException {
        super();

        this.saveCallback = saveCallback;
        this.validationCallback = validationCallback;

        controller = new EndpointEditorController(this);

        this.loadView(EndpointEditor.class.getResource("view.fxml"));
    }

    public IEndpointEditorSaveCallback saveCallback() {
        return saveCallback;
    }

    public IEndpointEditorValidationCallback validationCallback() {
        return validationCallback;
    }
}
