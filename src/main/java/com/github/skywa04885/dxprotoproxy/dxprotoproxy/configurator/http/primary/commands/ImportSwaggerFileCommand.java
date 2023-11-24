package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.PrimaryController;

public class ImportSwaggerFileCommand extends Command {
    public ImportSwaggerFileCommand(PrimaryController controller) {
        super(controller, "Importing OpenAPI specification");
    }

    @Override
    public void execute() throws InterruptedException {
        Thread.sleep(1000);
    }
}
