package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.Window;

import java.io.IOException;

public class RequestEditor extends Window<RequestEditorController> {
    private final IRequestEditorValidationCallback validationCallback;
    private final IRequestEditorSubmissionCallback submissionCallback;

    public RequestEditor(IRequestEditorValidationCallback validationCallback, IRequestEditorSubmissionCallback submissionCallback) throws IOException {
        super();

        this.validationCallback = validationCallback;
        this.submissionCallback = submissionCallback;

        controller = new RequestEditorController(this);

        loadView(RequestEditor.class.getResource("view.fxml"));
    }

    public IRequestEditorValidationCallback validationCallback() {
        return validationCallback;
    }

    public IRequestEditorSubmissionCallback submissionCallback() {
        return submissionCallback;
    }
}
