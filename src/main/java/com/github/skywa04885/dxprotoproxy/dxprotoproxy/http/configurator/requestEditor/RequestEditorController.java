package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorQueryParameter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RequestEditorController implements Initializable {
    // Headers table view.
    @FXML
    public TableView<EditorHeader> headersTableView;
    @FXML
    public TableColumn<EditorHeader, String> headersTableViewKeyColumn;
    @FXML
    public TableColumn<EditorHeader, String> headersTableViewNameColumn;
    @FXML
    public TableColumn<EditorHeader, String> headersTableViewValueColumn;

    // Fields table view.
    @FXML
    public TableView<EditorField> bodyTableView;
    @FXML
    public TableColumn<EditorField, String> bodyTableViewFieldColumn;
    @FXML
    public TableColumn<EditorField, String> bodyTableViewNameColumn;
    @FXML
    public TableColumn<EditorField, String> bodyTableViewValueColumn;

    // Query parameters table view.
    @FXML
    public TableView<EditorQueryParameter> uriQueryParametersTableView;
    @FXML
    public TableColumn<EditorQueryParameter, String> uriQueryParametersTableViewKeyColumn;
    @FXML
    public TableColumn<EditorQueryParameter, String> uriQueryParametersTableViewValueColumn;

    // Text fields.
    @FXML
    public TextField pathTextField;

    // Combo boxes.
    @FXML
    public ComboBox<DXHttpRequestMethod> methodComboBox;
    @FXML
    public ComboBox<DXHttpFieldsFormat> formatComboBox;

    // Buttons.
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    // Instance variables.
    private final RequestEditor window;

    /**
     * Constructs a new request editor controller.
     *
     * @param window the window.
     */
    public RequestEditorController(RequestEditor window) {
        this.window = window;
    }

    /**
     * Shows the validation error alert with the given message.
     * @param error error.
     */
    private void showValidationErrorAlert(String error) {
        final var alert = new Alert(Alert.AlertType.ERROR);

        final String title = "Invalid endpoint values";

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(error);

        alert.show();
    }

    /**
     * Saves the edited request.
     */
    private void save() {
        // Gets all the values.
        final String pathTemplate = pathTextField.getText();
        final DXHttpRequestMethod requestMethod = methodComboBox.getValue();
        final DXHttpFieldsFormat bodyFormat = formatComboBox.getValue();
        final List<EditorField> fields = bodyTableView.getItems().stream()
                .filter(field -> !field.isBlank()).toList();
        final List<EditorHeader> headers = headersTableView.getItems().stream()
                .filter(field -> !field.isBlank()).toList();
        final List<EditorQueryParameter> queryParameters = uriQueryParametersTableView.getItems().stream()
                .filter(field -> !field.isEmpty()).toList();

        // Validates the values.
        final String error = window.validationCallback().validate(pathTemplate, queryParameters,requestMethod,
                headers, fields, bodyFormat);

        // Shows the error if it's there.
        if (error != null) {
            showValidationErrorAlert(error);
            return;
        }

        // Submits the request.
        window.submissionCallback().submit(pathTemplate, queryParameters,requestMethod, headers, fields, bodyFormat);

        // Closes the editor.
        window.close();
    }

    /**
     * Gets called on the save button action.
     *
     * @param actionEvent the action event.
     */
    private void onSaveButtonAction(ActionEvent actionEvent) {
        save();
    }

    /**
     * Cancels the editing of the request.
     */
    private void cancel() {
        window.close();
    }

    /**
     * Gets called on cancel button action.
     *
     * @param actionEvent the action event.
     */
    private void onCancelButtonAction(ActionEvent actionEvent) {
        cancel();
    }

    /**
     * Initializes the method combo box.
     */
    private void initializeMethodComboBox() {
        // Sets the items of the method combo box and sets get as the default method.
        methodComboBox.setItems(FXCollections.observableList(Arrays.stream(DXHttpRequestMethod.values()).toList()));
        methodComboBox.getSelectionModel().select(DXHttpRequestMethod.Get);
    }

    /**
     * Initializes the format combo box.
     */
    private void initializeFormatComboBox() {
        // Sets the items of the format combo box and sets json as the default format.
        formatComboBox.setItems(FXCollections.observableList(Arrays.stream(DXHttpFieldsFormat.values()).toList()));
        formatComboBox.getSelectionModel().select(DXHttpFieldsFormat.JSON);
    }

    /**
     * Updates the headers table (removing empty ones and making sure the last one is meant for creation).
     */
    private void updateHeadersTableView() {
        headersTableView.getItems().removeIf(EditorHeader::isBlank);
        headersTableView.getItems().add(new EditorHeader());
    }

    /**
     * Updates the body table view (removing empty ones and making sure the last one is meant for creation).
     */
    private void updateBodyTableView() {
        bodyTableView.getItems().removeIf(EditorField::isBlank);
        bodyTableView.getItems().add(new EditorField());
    }

    /**
     * Updates the uri query parameters table view (removing empty ones and making sure the last one is meant for
     *  creation).
     */
    private void updateUriQueryParametersTableView() {
        uriQueryParametersTableView.getItems().removeIf(EditorQueryParameter::isEmpty);
        uriQueryParametersTableView.getItems().add(new EditorQueryParameter());
    }

    /**
     * Gets called when a value in the key column was changed.
     *
     * @param event the event.
     */
    private void onHeadersTableViewKeyColumnEditCommit(TableColumn.CellEditEvent<EditorHeader, String> event) {
        event.getRowValue().setKey(event.getNewValue());

        updateHeadersTableView();
    }

    /**
     * Gets called when a value in the value column was changed.
     *
     * @param event the event.
     */
    private void onHeadersTableViewValueColumnEditCommit(TableColumn.CellEditEvent<EditorHeader, String> event) {
        event.getRowValue().setValue(event.getNewValue());

        updateHeadersTableView();
    }

    /**
     * Gets called when a value in the name column was changed.
     *
     * @param event the event.
     */
    private void onHeadersTableViewNameColumnEditCommit(TableColumn.CellEditEvent<EditorHeader, String> event) {
        event.getRowValue().setName(event.getNewValue());

        updateHeadersTableView();
    }

    /**
     * Gets called when a value in the field column was changed.
     *
     * @param event the event.
     */
    private void onBodyTableViewFieldColumnEditCommit(TableColumn.CellEditEvent<EditorField, String> event) {
        event.getRowValue().setPath(event.getNewValue());

        updateBodyTableView();
    }

    /**
     * Gets called when a value in the value column was changed.
     *
     * @param event the event.
     */
    private void onBodyTableViewValueColumnEditCommit(TableColumn.CellEditEvent<EditorField, String> event) {
        event.getRowValue().setValue(event.getNewValue());

        updateBodyTableView();
    }

    /**
     * Gets called when a value in the name column was changed.
     *
     * @param event the event.
     */
    private void onBodyTableViewNameColumnEditCommit(TableColumn.CellEditEvent<EditorField, String> event) {
        event.getRowValue().setName(event.getNewValue());

        updateBodyTableView();
    }

    /**
     * Gets called when a value in the value key was changed.
     *
     * @param event the event.
     */
    private void onUriQueryParametersTableViewKeyColumnEditCommit(TableColumn.CellEditEvent<EditorQueryParameter, String> event) {
        event.getRowValue().setKey(event.getNewValue());

        updateUriQueryParametersTableView();
    }

    /**
     * Gets called when a value in the value column was changed.
     *
     * @param event the event.
     */
    private void onUriQueryParametersTableViewValueColumnEditCommit(TableColumn.CellEditEvent<EditorQueryParameter, String> event) {
        event.getRowValue().setValue(event.getNewValue());

        updateUriQueryParametersTableView();
    }

    /**
     * Gets called when a key has been pressed on the headers table.
     *
     * @param keyEvent the key event.
     */
    private void onHeadersTableViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            headersTableView.getItems().removeAll(headersTableView.getSelectionModel().getSelectedItems().stream()
                    .toList());

            updateHeadersTableView();
        }
    }

    /**
     * Gets called when a key has been pressed on the body table.
     *
     * @param keyEvent the key event.
     */
    private void onBodyTableViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            bodyTableView.getItems().removeAll(bodyTableView.getSelectionModel().getSelectedItems().stream()
                    .toList());

            updateBodyTableView();
        }
    }

    /**
     * Gets called when a key has been pressed on the uri query parameters table.
     *
     * @param keyEvent the key event.
     */
    private void onUriQueryParametersTableViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            uriQueryParametersTableView.getItems().removeAll(uriQueryParametersTableView.getSelectionModel()
                    .getSelectedItems().stream().toList());

            updateUriQueryParametersTableView();
        }
    }

    /**
     * Initializes the headers table view.
     */
    private void initializeHeadersTableView() {
        // Makes the table editable.
        headersTableView.setEditable(true);

        // Sets the key press handler for the headers table.
        headersTableView.setOnKeyPressed(this::onHeadersTableViewKeyPressed);

        // Sets the cell factories for the columns.
        headersTableViewKeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        headersTableViewValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        headersTableViewNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Makes the columns editable.
        headersTableViewKeyColumn.setEditable(true);
        headersTableViewValueColumn.setEditable(true);
        headersTableViewNameColumn.setEditable(true);

        // Sets the cell value factories for the columns.
        headersTableViewKeyColumn.setCellValueFactory(header -> header.getValue().keyProperty());
        headersTableViewValueColumn.setCellValueFactory(header -> header.getValue().valueProperty());
        headersTableViewNameColumn.setCellValueFactory(header -> header.getValue().nameProperty());

        // Sets the on commit listeners.
        headersTableViewKeyColumn.setOnEditCommit(this::onHeadersTableViewKeyColumnEditCommit);
        headersTableViewValueColumn.setOnEditCommit(this::onHeadersTableViewValueColumnEditCommit);
        headersTableViewNameColumn.setOnEditCommit(this::onHeadersTableViewNameColumnEditCommit);

        // Updates the headers table view.
        updateHeadersTableView();
    }

    /**
     * Initializes the body table view.
     */
    private void initializeBodyTableView() {
        // Makes the table editable.
        bodyTableView.setEditable(true);

        // Sets the key press handler for the body table.
        bodyTableView.setOnKeyPressed(this::onBodyTableViewKeyPressed);

        // Sets the cell factories for the columns.
        bodyTableViewFieldColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bodyTableViewValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bodyTableViewNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Makes the columns editable.
        bodyTableViewFieldColumn.setEditable(true);
        bodyTableViewValueColumn.setEditable(true);
        bodyTableViewNameColumn.setEditable(true);

        // Sets the cell value factories for the columns.
        bodyTableViewFieldColumn.setCellValueFactory(header -> header.getValue().pathProperty());
        bodyTableViewValueColumn.setCellValueFactory(header -> header.getValue().valueProperty());
        bodyTableViewNameColumn.setCellValueFactory(header -> header.getValue().nameProperty());

        // Sets the on commit listeners.
        bodyTableViewFieldColumn.setOnEditCommit(this::onBodyTableViewFieldColumnEditCommit);
        bodyTableViewValueColumn.setOnEditCommit(this::onBodyTableViewValueColumnEditCommit);
        bodyTableViewNameColumn.setOnEditCommit(this::onBodyTableViewNameColumnEditCommit);

        // Updates the columns.
        updateBodyTableView();
    }

    /**
     * Initializes the uri query parameters table view.
     */
    private void initializeUriQueryParametersTableView() {
        // Makes the table editable.
        uriQueryParametersTableView.setEditable(true);

        // Sets the key press handler for the body table.
        uriQueryParametersTableView.setOnKeyPressed(this::onUriQueryParametersTableViewKeyPressed);

        // Sets the cell factories for the columns.
        uriQueryParametersTableViewKeyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        uriQueryParametersTableViewValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Makes the columns editable.
        uriQueryParametersTableViewKeyColumn.setEditable(true);
        uriQueryParametersTableViewValueColumn.setEditable(true);

        // Sets the cell value factories for the columns.
        uriQueryParametersTableViewKeyColumn.setCellValueFactory(header -> header.getValue().keyProperty());
        uriQueryParametersTableViewValueColumn.setCellValueFactory(header -> header.getValue().valueProperty());

        // Sets the on commit listeners.
        uriQueryParametersTableViewKeyColumn.setOnEditCommit(this::onUriQueryParametersTableViewKeyColumnEditCommit);
        uriQueryParametersTableViewValueColumn.setOnEditCommit(this::onUriQueryParametersTableViewValueColumnEditCommit);

        // Updates the columns.
        updateUriQueryParametersTableView();
    }

    /**
     * Initializes the request editor controller.
     *
     * @param url            the url.
     * @param resourceBundle the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets all the button action listeners.
        saveButton.setOnAction(this::onSaveButtonAction);
        cancelButton.setOnAction(this::onCancelButtonAction);

        // Calls the other initialization methods.
        initializeMethodComboBox();
        initializeFormatComboBox();
        initializeHeadersTableView();
        initializeBodyTableView();
        initializeUriQueryParametersTableView();
    }
}
