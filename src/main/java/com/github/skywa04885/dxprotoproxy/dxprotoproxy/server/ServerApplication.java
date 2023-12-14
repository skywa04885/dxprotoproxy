package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.ConfigReader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.http.client.HttpClientNetService;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetServiceRegistry;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;

public class ServerApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        final var file = new File("config.xml");
        if (!file.exists()) {
            return;
        }

        final ConfigRoot configRoot = ConfigReader.read(file);

        NetServiceRegistry netServiceRegistry = new NetServiceRegistry();
        netServiceRegistry.register(new HttpClientNetService(configRoot.httpConfig()));
        netServiceRegistry.activateAll();

        ServerWindow serverWindow = ServerWindowFactory.create(stage, new ServerController(netServiceRegistry));
    }
}
