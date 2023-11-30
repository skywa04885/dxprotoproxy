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

    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createApi;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createApi to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createInstance;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createInstance to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;
}