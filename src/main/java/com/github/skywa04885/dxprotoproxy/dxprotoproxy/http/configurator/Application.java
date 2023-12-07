package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryController;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.swagger.SwaggerImporter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        PrimaryWindowFactory.create(new PrimaryController(), "DX");
    }

    public static void main(String[] args) {
        launch();
    }
}