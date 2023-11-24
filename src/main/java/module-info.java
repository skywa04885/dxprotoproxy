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

    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor to javafx.fxml;
    exports com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;
    opens com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor to javafx.fxml;
}