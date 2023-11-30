package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.PrimaryController;

public abstract class Command {
    protected final PrimaryController controller;
    public final String name;

    public Command(PrimaryController controller, String name) {
        this.controller = controller;
        this.name = name;
    }

    public abstract void execute() throws InterruptedException;
}
