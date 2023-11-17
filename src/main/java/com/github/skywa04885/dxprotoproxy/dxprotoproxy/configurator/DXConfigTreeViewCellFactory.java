package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class DXConfigTreeViewCellFactory implements Callback<TreeView<IDXTreeItem>, TreeCell<IDXTreeItem>> {
    private final DXHttpConfiguratorTreeContextMenuFactory configuratorTreeContextMenuPicker;

    public DXConfigTreeViewCellFactory(DXHttpConfiguratorTreeContextMenuFactory configuratorTreeContextMenuPicker) {
        this.configuratorTreeContextMenuPicker = configuratorTreeContextMenuPicker;
    }

    @Override
    public TreeCell<IDXTreeItem> call(TreeView<IDXTreeItem> stringTreeView) {
        final var treeCell = new TreeCell<IDXTreeItem>() {
            @Override
            protected void updateItem(IDXTreeItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    textProperty().unbind();
                    setText(null);
                    setGraphic(null);
                } else {
                    textProperty().bind(item.treeItemText());
                    setGraphic(item.treeItemGraphic());
                }
            }
        };

        treeCell.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY && !treeCell.isEmpty()) {
                final var contextmenu = configuratorTreeContextMenuPicker.getContextMenuForTreeCell(treeCell);
                contextmenu.setUserData(treeCell);
                contextmenu.show(treeCell, event.getScreenX(), event.getScreenY());
            }
        });

        return treeCell;
    }
}
