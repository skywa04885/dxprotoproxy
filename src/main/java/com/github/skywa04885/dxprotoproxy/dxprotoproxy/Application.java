package com.github.skywa04885.dxprotoproxy.dxprotoproxy;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.ConfigReader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryController;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.client.HttpClientNetService;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.net.NetServiceRegistry;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Logger;

public class Application extends javafx.application.Application {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private ConfigRoot getConfigRoot() throws Exception {
        File file = new File("config.xml");

        if (file.exists()) {
            logger.info("Using config file present on file system");

            return ConfigReader.read(file);
        }

        return new ConfigRoot();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ConfigRoot configRoot = getConfigRoot();

        NetServiceRegistry netServiceRegistry = new NetServiceRegistry();
        netServiceRegistry.register(new HttpClientNetService(configRoot.httpConfig()));
        netServiceRegistry.activateAll();

        PrimaryWindowFactory.create(new PrimaryController(configRoot), "DX");
    }

    public static void main(String[] args) {
        launch();
    }
}