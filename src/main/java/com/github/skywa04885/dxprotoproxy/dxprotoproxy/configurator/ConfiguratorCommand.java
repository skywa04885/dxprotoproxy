package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

public abstract class ConfiguratorCommand {
    protected final ConfiguratorController controller;
    public final String name;

    public ConfiguratorCommand(ConfiguratorController controller, String name) {
        this.controller = controller;
        this.name = name;
    }

    public abstract void execute() throws InterruptedException;
}
