package com.github.skywa04885.dxprotoproxy.fx;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ControllerFacade {
    protected @Nullable WindowFacade window;

    /**
     * Sets the window of the controller.
     *
     * @param window the new window.
     */
    void setWindow(@NotNull WindowFacade window) {
        this.window = window;
    }
}
