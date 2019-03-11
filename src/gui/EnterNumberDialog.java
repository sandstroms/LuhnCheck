package gui;

import logic.LuhnAlgos;
import constants.CardType;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

public class EnterNumberDialog extends VBox {
    private Label enterNumMsg;
    private Label isValidMsg;
    private Label hyphen1;
    private Label hyphen2;
    private Label hyphen3;
    private TextField chunk1Field;
    private TextField chunk2Field;
    private TextField chunk3Field;
    private TextField chunk4Field;
    private Button submit;
    private LuhnAlgos luhnAlgos;

    public EnterNumberDialog(CardType cardType) {
        luhnAlgos = new LuhnAlgos();
        isValidMsg = new Label();
        submit = new Button("Submit");
        hyphen1 = new Label("-");
        hyphen2 = new Label("-");
        hyphen3 = new Label("-");
        Font msgFont = new Font("Arial", 18);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);

        if(cardType == CardType.VISA) {
            enterNumMsg = new Label("Enter the 16-digit card number");
            chunk1Field = new TextField();
            chunk1Field.setPrefWidth(42);
            addTextLimiter(chunk1Field, 4);
            chunk2Field = new TextField();
            chunk2Field.setPrefWidth(42);
            addTextLimiter(chunk2Field, 4);
            chunk3Field = new TextField();
            chunk3Field.setPrefWidth(42);
            addTextLimiter(chunk3Field, 4);
            chunk4Field = new TextField();
            chunk4Field.setPrefWidth(42);
            addTextLimiter(chunk4Field, 4);
            hBox.getChildren().addAll(chunk1Field, hyphen1, chunk2Field, hyphen2, chunk3Field,
                                      hyphen3, chunk4Field);
        } else {
            enterNumMsg = new Label("Enter the 15-digit card number");
            chunk1Field = new TextField();
            chunk1Field.setPrefWidth(42);
            addTextLimiter(chunk1Field, 4);
            chunk2Field = new TextField();
            chunk2Field.setPrefWidth(56);
            addTextLimiter(chunk2Field, 6);
            chunk3Field = new TextField();
            chunk3Field.setPrefWidth(48);
            addTextLimiter(chunk3Field, 5);
            hBox.getChildren().addAll(chunk1Field, hyphen1, chunk2Field, hyphen2, chunk3Field);
        }

        getChildren().addAll(enterNumMsg, hBox, submit, isValidMsg);
        setSpacing(15);
        setAlignment(Pos.CENTER);

        enterNumMsg.setFont(msgFont);
        hyphen1.setFont(msgFont);
        hyphen2.setFont(msgFont);
        hyphen3.setFont(msgFont);

        Font errFont = new Font("Arial", 14);
        isValidMsg.setFont(errFont);

        EventHandler<ActionEvent> handler = (e -> {
            boolean valid;
            String chunk1, chunk2, chunk3;
            if(cardType == CardType.VISA) {
                chunk1 = chunk1Field.getText();
                chunk2 = chunk2Field.getText();
                chunk3 = chunk3Field.getText();
                String chunk4 = chunk4Field.getText();
                valid = luhnAlgos.luhnCheckVisa(chunk1, chunk2, chunk3, chunk4);
            } else {
                chunk1 = chunk1Field.getText();
                chunk2 = chunk2Field.getText();
                chunk3 = chunk3Field.getText();
                valid = luhnAlgos.luhnCheckAmex(chunk1, chunk2, chunk3);
            }
            if(valid) {
                isValidMsg.setTextFill(Color.GREEN);
                isValidMsg.setText("Card number is valid");
            } else {
                isValidMsg.setTextFill(Color.RED);
                isValidMsg.setText("Card number is invalid");
            }
        });

        submit.setOnAction(handler);
    }

    // Referenced: https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
    private static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String oldValue, final String newValue) {
                if(tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}

