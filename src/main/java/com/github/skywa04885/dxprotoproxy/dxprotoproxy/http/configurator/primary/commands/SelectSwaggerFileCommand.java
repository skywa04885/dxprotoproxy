package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryController;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class SelectSwaggerFileCommand extends Command {
    public SelectSwaggerFileCommand(PrimaryController controller) {
        super(controller, "Selecting file for OpenAPI specification import");
    }

    @Override
    public void execute() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            final var fileExtensionFilter = new FileChooser.ExtensionFilter("OpenAPI Spec (*.yaml, *.yml, *.json)",
                    "*.yaml", "*.yml", "*.json");

            final var fileChooser = new FileChooser();

            fileChooser.setTitle("Select OpenAPI specification");
            fileChooser.setSelectedExtensionFilter(fileExtensionFilter);

            final Window window = controller.borderPane.getScene().getWindow();

            final File file = fileChooser.showOpenDialog(window);

            if (file != null) {
                controller.addCommand(new ImportSwaggerFileCommand(controller));
            }

            countDownLatch.countDown();
        });

        countDownLatch.await();
    }
}
