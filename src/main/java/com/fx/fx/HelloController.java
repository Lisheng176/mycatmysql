package com.fx.fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField textField;

    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonClick() {
        textField.setText(label.getText());
    }
}