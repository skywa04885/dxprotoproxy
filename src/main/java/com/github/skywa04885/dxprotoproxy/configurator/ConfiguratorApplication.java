package com.github.skywa04885.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.configurator.http.primary.PrimaryController;
import com.github.skywa04885.dxprotoproxy.configurator.http.primary.PrimaryWindowFactory;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class ConfiguratorApplication extends javafx.application.Application {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void start(@NotNull Stage stage) throws Exception {
        PrimaryWindowFactory.create(stage, new PrimaryController());
    }

    public static void main(String[] args) {
        launch();
    }
}