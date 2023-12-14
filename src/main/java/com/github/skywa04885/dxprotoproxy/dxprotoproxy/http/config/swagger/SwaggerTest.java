package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.swagger;

import java.io.File;

public class SwaggerTest {
    public static void main(String[] args) {
        SwaggerImporter swaggerImporter = new SwaggerImporter();
        swaggerImporter.importFromFile(new File("./mir100.yaml"));
    }
}
