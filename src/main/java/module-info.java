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
}