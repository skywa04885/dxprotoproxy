package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OpenConfigCommand extends ConfiguratorCommand {
    private final File file;

    public OpenConfigCommand(ConfiguratorController controller, File file) {
        super(controller, "Openen van configuratiebestand: " + file.getName());
        this.file = file;
    }

    @Override
    public void execute() throws InterruptedException {
        // Sleeps a bit to make the UI more fancy.
        Thread.sleep(100);

        try {
            final var config = DXHttpConfig.ReadFromFile(file);

            controller.addCommand(new ChangeConfigCommand(controller, config));
        } catch (Exception exception) {
            Platform.runLater(() -> {
                final var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Configuratie kon niet worden geopend");
                alert.setContentText("Foutmelding: " + exception.getMessage());
                alert.show();
            });
        }

    }
}
