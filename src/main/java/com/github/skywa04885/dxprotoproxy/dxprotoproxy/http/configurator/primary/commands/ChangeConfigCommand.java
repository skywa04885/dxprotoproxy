package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryController;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class ChangeConfigCommand extends Command {
    private final DXHttpConfig config;

    public ChangeConfigCommand(PrimaryController controller, DXHttpConfig config) {
        super(controller, "Changing config");
        this.config = config;
    }

    @Override
    public void execute() throws InterruptedException {
        Thread.sleep(100);

        final var countDownLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.config.setValue(config);

            countDownLatch.countDown();
        });

        countDownLatch.await();
    }
}
