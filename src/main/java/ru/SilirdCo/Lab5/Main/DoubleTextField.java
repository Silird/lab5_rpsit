package ru.SilirdCo.Lab5.Main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Lab5.Util.VarUtils;

public class DoubleTextField {
    private final static Logger logger = LoggerFactory.getLogger(DoubleTextField.class);

    private TextField textField = null;

    private ObjectProperty<Double> doubleProp = null;

    public final ObjectProperty<Double> doubleProperty() {
        if (doubleProp == null) {
            doubleProp = new SimpleObjectProperty<>();
            doubleProp.setValue(null);

        }
        return doubleProp;
    }

    public DoubleTextField(TextField textField) {
        this.textField = textField;
        initListeners();
    }

    private void initListeners() {
        if (textField != null) {

            textField.setAlignment(Pos.CENTER_RIGHT);

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals("")) {
                    try {
                        Double result = Double.parseDouble(newValue);

                        doubleProperty().setValue(VarUtils.round(result));
                    }
                    catch (NumberFormatException ex) {
                        textField.setText(oldValue);
                    }
                }
            });
        }
        else {
            logger.warn("Текстовое поле не назначено");
        }
    }

    public Double getDouble() {
        /*
        Float result = null;
        if (textField != null) {
            if (!textField.getText().equals("")) {
                try {
                    result = Float.parseFloat(textField.getText());
                }
                catch (NumberFormatException ex) {
                    ExceptionHandler.handle(logger, ex);
                    result = null;
                }
            }
        }
        else {
            logger.warn("Текстовое поле не назначено");
        }

        return result;
        */

        return doubleProperty().get();
    }

    public void setFloat(Float value) {
        if (value == null) {
            textField.setText("");
        }
        else {
            textField.setText(String.valueOf(value));
        }
    }
}
