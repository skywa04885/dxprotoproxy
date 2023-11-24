package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.Window;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryWindow extends Window<PrimaryController> {
    /**
     * Creates a new primary window.
     * @param stage the stage.
     * @throws IOException gets thrown when the loading of the view fails.
     */
    public PrimaryWindow(Stage stage) throws IOException {
        super(stage);

        controller = new PrimaryController();

        loadView(PrimaryWindow.class.getResource("view.fxml"));
    }
}
