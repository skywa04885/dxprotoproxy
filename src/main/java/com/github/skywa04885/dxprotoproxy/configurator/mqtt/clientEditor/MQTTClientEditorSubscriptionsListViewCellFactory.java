package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.Nullable;

public class MQTTClientEditorSubscriptionsListViewCellFactory extends ListCell<MQTTClientEditorSubscription> {
    private @Nullable TextField textField;

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null)
        {
            createTextField();
        }

        setText(null);
        setGraphic(textField);
        textField.selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        textField.clear();
        setText(getItem().topic());
        setGraphic(null);
    }

    @Override
    public void commitEdit(MQTTClientEditorSubscription mqttClientEditorSubscription) {
        super.commitEdit(mqttClientEditorSubscription);

        setText(getItem().topic());
        setGraphic(null);
        this.getListView().requestFocus();
    }

    @Override
    protected void updateItem(MQTTClientEditorSubscription mqttClientEditorSubscription, boolean empty) {
        super.updateItem(mqttClientEditorSubscription, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getItem().topic());
                }

                setText(null);
                setGraphic(textField);
            } else {
                setText(getItem().topic());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getItem() == null ? "" : getItem().topic());
        textField.setPrefWidth(USE_COMPUTED_SIZE);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                getItem().setTopic(textField.getText());
                commitEdit(getItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
}
