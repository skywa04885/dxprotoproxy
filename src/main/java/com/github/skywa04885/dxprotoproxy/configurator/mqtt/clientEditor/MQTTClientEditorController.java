package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import com.github.skywa04885.dxprotoproxy.GlobalConstants;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientsConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MQTTClientEditorController implements Initializable {
    /**
     * Static constants.
     */
    public static final int CLIENT_PORT_INITIAL_VALUE = 40000;
    public static final int BROKER_PORT_INITIAL_VALUE = 1883;
    public static final int PORT_STEP = 1;

    /**
     * Member variables for FXML elements.
     */
    public @FXML TextField clientHostnameTextField;
    public @FXML Spinner<Integer> clientPortSpinner;
    public @FXML TextField brokerHostnameTextField;
    public @FXML Spinner<Integer> brokerPortSpinner;
    public @FXML TextField usernameTextField;
    public @FXML TextField passwordTextField;
    public @FXML TextField clientIdentifierTextField;
    public @FXML ListView<MQTTClientEditorSubscription> subscriptionsListView;
    public @FXML Button cancelButton;
    public @FXML Button submitButton;
    /**
     * Member variables.
     */
    private final @NotNull MQTTClientsConfig mqttClientsConfig;
    private final @Nullable MQTTClientConfig mqttClientConfig;
    private final @NotNull IMQTTClientEditorSubmissionCallback submissionCallback;
    private final @NotNull IMQTTClientEditorValidationCallback validationCallback;
    private @Nullable MQTTClientEditorWindow window;

    /**
     * Constructs a new mqtt client editor controller.
     *
     * @param mqttClientsConfig  the clients' config.
     * @param mqttClientConfig   the client config for when an existing client needs to be modified.
     * @param submissionCallback the submission callback.
     * @param validationCallback the validation callback.
     */
    public MQTTClientEditorController(@NotNull MQTTClientsConfig mqttClientsConfig,
                                      @Nullable MQTTClientConfig mqttClientConfig,
                                      @NotNull IMQTTClientEditorSubmissionCallback submissionCallback,
                                      @NotNull IMQTTClientEditorValidationCallback validationCallback) {
        this.mqttClientsConfig = mqttClientsConfig;
        this.mqttClientConfig = mqttClientConfig;
        this.submissionCallback = submissionCallback;
        this.validationCallback = validationCallback;
    }

    public MQTTClientEditorController(@NotNull MQTTClientsConfig mqttClientsConfig,
                                      @NotNull IMQTTClientEditorSubmissionCallback submissionCallback,
                                      @NotNull IMQTTClientEditorValidationCallback validationCallback) {
        this(mqttClientsConfig, null, submissionCallback, validationCallback);
    }

    public MQTTClientEditorController(@NotNull MQTTClientConfig mqttClientConfig,
                                      @NotNull IMQTTClientEditorSubmissionCallback submissionCallback,
                                      @NotNull IMQTTClientEditorValidationCallback validationCallback) {
        this(Objects.requireNonNull(mqttClientConfig.parent()), mqttClientConfig, submissionCallback, validationCallback);
    }

    /**
     * Sets the window.
     * @param window the new window.
     */
    public void setWindow(@Nullable MQTTClientEditorWindow window) {
        this.window = window;
    }

    /**
     * Updates the subscriptions list view by removing blank ones and creating a blank one
     * at the end.
     */
    private void updateSubscriptionsListView() {
        // Removes all the blank subscriptions.
        subscriptionsListView.getItems().removeIf(MQTTClientEditorSubscription::isBlank);

        // Adds a new blank subscription to the end (for when the user wants to create a new one).
        subscriptionsListView.getItems().add(new MQTTClientEditorSubscription());
    }

    /**
     * Initializes the client hostname text field.
     */
    private void initializeClientHostnameTextField() {
        if (mqttClientConfig != null) {
            clientHostnameTextField.setText(mqttClientConfig.clientHostname());
        }
    }

    /**
     * Initializes the client port spinner.
     */
    private void initializeClientPortSpinner() {
        // Sets the value factory.
        clientPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(GlobalConstants.PORT_MIN,
                GlobalConstants.PORT_MAX, CLIENT_PORT_INITIAL_VALUE, PORT_STEP));

        // Changes the port to the existing port if we're modifying an existing config.
        if (mqttClientConfig != null) {
            clientPortSpinner.getValueFactory().setValue(mqttClientConfig.clientPort());
        }
    }

    /**
     * Initializes the broker hostname text field.
     */
    private void initializeBrokerHostnameTextField() {
        if (mqttClientConfig != null) {
            brokerHostnameTextField.setText(mqttClientConfig.brokerHostname());
        }
    }

    /**
     * Initializes the broker port spinner.
     */
    private void initializeBrokerPortSpinner() {
        // Sets the value factory.
        brokerPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(GlobalConstants.PORT_MIN,
                GlobalConstants.PORT_MAX, BROKER_PORT_INITIAL_VALUE, PORT_STEP));

        // Changes the port to the existing port if we're modifying an existing config.
        if (mqttClientConfig != null) {
            brokerPortSpinner.getValueFactory().setValue(mqttClientConfig.brokerPort());
        }
    }

    /**
     * Initializes the subscriptions list view.
     */
    private void initializeSubscriptionsListView() {
        // Set the cell factory.
        subscriptionsListView.setCellFactory(listView -> new MQTTClientEditorSubscriptionsListViewCellFactory());
        subscriptionsListView.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                updateSubscriptionsListView();
            }
        });

        // Make the list editable.
        subscriptionsListView.setEditable(true);

        // If we're modifying an existing mqtt client config,
        //  insert its subscriptions into the list.
        if (mqttClientConfig != null) {
            subscriptionsListView.getItems().addAll(Objects.requireNonNull(mqttClientConfig)
                    .subscriptionConfigs().stream().map(MQTTClientEditorSubscription::fromSubscriptionConfig).toList());
        }

        // Updates the subscription list view.
        updateSubscriptionsListView();
    }

    /**
     * Initializes the username text field.
     */
    private void initializeUsernameTextField() {
        if (mqttClientConfig != null && mqttClientConfig.username() != null) {
            usernameTextField.setText(mqttClientConfig.username());
        }
    }

    /**
     * Initializes the password text field.
     */
    private void initializePasswordTextField() {
        if (mqttClientConfig != null && mqttClientConfig.password() != null) {
            passwordTextField.setText(mqttClientConfig.password());
        }
    }


    /**
     * Initializes the client identifier text field.
     */
    private void initializeClientIdentifierTextField() {
        if (mqttClientConfig != null && mqttClientConfig.clientIdentifier() != null) {
            clientIdentifierTextField.setText(mqttClientConfig.clientIdentifier());
        }
    }

    /**
     * Cancels the editing.
     */
    private void cancel() {
        // Closes the window.
        Objects.requireNonNull(window).getStage().close();
    }

    /**
     * The cancel button event handler.
     *
     * @param actionEvent the action event.
     */
    private void cancelButtonActionEventHandler(@NotNull ActionEvent actionEvent) {
        cancel();
    }

    /**
     * Initializes the cancel button.
     */
    private void initializeCancelButton() {
        cancelButton.setOnAction(this::cancelButtonActionEventHandler);
    }

    /**
     * Submits.
     */
    private void submit() {
        // Gets the fields.
        final @NotNull String clientHostname = clientHostnameTextField.getText();
        final @NotNull Integer clientPort = Objects.requireNonNull(clientPortSpinner.getValue());
        final @NotNull String brokerHostname = brokerHostnameTextField.getText();
        final @NotNull Integer brokerPort = Objects.requireNonNull(brokerPortSpinner.getValue());
        final @Nullable String username = usernameTextField.getText().isBlank()
                ? null : usernameTextField.getText();
        final @Nullable String password = passwordTextField.getText().isBlank()
                ? null : passwordTextField.getText();
        final @Nullable String clientIdentifier = clientIdentifierTextField.getText().isBlank()
                ? null : clientIdentifierTextField.getText();
        final @NotNull List<MQTTClientEditorSubscription> editorSubscriptionList = subscriptionsListView
                .getItems()
                .stream()
                .filter(MQTTClientEditorSubscription::isNotBlank)
                .toList();

        // Validates the fields.
        final @Nullable String error = validationCallback.validate(clientHostname, clientPort, brokerHostname,
                brokerPort, username, password, clientIdentifier, editorSubscriptionList);

        // Show an alert containing the error if the contents aren't valid.
        if (error != null) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid client configuration");
            alert.setContentText(error);
            alert.show();
            return;
        }

        // Submits the fields.
        submissionCallback.submit(clientHostname, clientPort, brokerHostname,
                brokerPort, username, password, clientIdentifier, editorSubscriptionList);

        // Closes the window.
        Objects.requireNonNull(window).getStage().close();
    }

    /**
     * The submit button event handler.
     *
     * @param actionEvent the action event.
     */
    private void submitButtonActionEventHandler(@NotNull ActionEvent actionEvent) {
        submit();
    }

    /**
     * Initializes the submit button.
     */
    private void initializeSubmitButton() {
        submitButton.setOnAction(this::submitButtonActionEventHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeClientHostnameTextField();
        initializeClientPortSpinner();
        initializeBrokerHostnameTextField();
        initializeBrokerPortSpinner();
        initializeUsernameTextField();
        initializePasswordTextField();
        initializeClientIdentifierTextField();
        initializeSubscriptionsListView();
        initializeCancelButton();
        initializeSubmitButton();
    }
}
