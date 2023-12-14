package com.github.skywa04885.dxprotoproxy.server;

import com.github.skywa04885.dxprotoproxy.config.ConfigReader;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.server.http.client.HttpClientNetService;
import com.github.skywa04885.dxprotoproxy.server.net.NetServiceRegistry;
import javafx.application.Application;
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
