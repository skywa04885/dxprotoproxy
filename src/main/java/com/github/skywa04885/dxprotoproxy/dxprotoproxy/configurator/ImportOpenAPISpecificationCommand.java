package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

public class ImportOpenAPISpecificationCommand extends ConfiguratorCommand {
    public ImportOpenAPISpecificationCommand(ConfiguratorController controller) {
        super(controller, "Importing OpenAPI specification");
    }

    @Override
    public void execute() throws InterruptedException {
        Thread.sleep(1000);
    }
}
