package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Objects;

public class DXConfiguratorImageCache {
    private static final DXConfiguratorImageCache instance = new DXConfiguratorImageCache();

    public static DXConfiguratorImageCache instance() {
        return instance;
    }

    private final HashMap<String, Image> images = new HashMap<>();

    public Image read(final String resource) {
        Image image = images.get(resource);
        if (image != null) return image;

        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(resource)));
        images.put(resource, image);

        return image;
    }
}
