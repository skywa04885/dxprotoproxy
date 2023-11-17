package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class OpenConfigSelectFileCommand extends ConfiguratorCommand {
    public OpenConfigSelectFileCommand(ConfiguratorController controller) {
        super(controller, "Selecting config file to open");
    }

    @Override
    public void execute() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            final var fileExtensionFilter = new FileChooser.ExtensionFilter("Config file (*.xml)",
                    "*.xml");

            final var fileChooser = new FileChooser();

            fileChooser.setTitle("Select config");
            fileChooser.setSelectedExtensionFilter(fileExtensionFilter);

            final Window window = controller.borderPane.getScene().getWindow();

            final File file = fileChooser.showOpenDialog(window);

            if (file != null)
            {
                controller.addCommand(new OpenConfigCommand(controller, file));
            }

            countDownLatch.countDown();
        });

        countDownLatch.await();
    }
}
