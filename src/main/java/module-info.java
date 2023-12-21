module com.github.skywa04885.dxprotoproxy.dxprotoproxy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires kotlin.stdlib;
    requires org.json;
    requires org.jetbrains.annotations;
    requires io.swagger.core;
    requires swagger.parser.v3;
    requires swagger.parser.core;
    requires io.swagger.models;
    requires io.swagger.v3.oas.models;
    requires java.logging;
    requires syntheticafx.theme.standard;
    requires java.net.http;
    requires org.eclipse.paho.client.mqttv3;

    exports com.github.skywa04885.dxprotoproxy.http;
    exports com.github.skywa04885.dxprotoproxy;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.primary;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.primary to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.apiEditor;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.apiEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.instanceEditor;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.instanceEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.endpointEditor;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.endpointEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.requestEditor;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.requestEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.config.http;
    opens com.github.skywa04885.dxprotoproxy.configurator.http.responseEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http.responseEditor;
    exports com.github.skywa04885.dxprotoproxy.server.net;
    exports com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor to javafx.fxml;

    exports com.github.skywa04885.dxprotoproxy.server;
    opens com.github.skywa04885.dxprotoproxy.server to javafx.fxml;

    exports com.github.skywa04885.dxprotoproxy.config;
    opens com.github.skywa04885.dxprotoproxy to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.server.http.client;
    exports com.github.skywa04885.dxprotoproxy.configurator;
    opens com.github.skywa04885.dxprotoproxy.configurator to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.configurator.http;
    opens com.github.skywa04885.dxprotoproxy.configurator.http to javafx.fxml;
}