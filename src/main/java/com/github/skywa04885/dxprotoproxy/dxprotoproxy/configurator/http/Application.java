package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.PrimaryWindow;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        final var primaryWindow = new PrimaryWindow(stage);
        primaryWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}