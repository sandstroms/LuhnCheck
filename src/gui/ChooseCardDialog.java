package gui;

import constants.CardType;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class ChooseCardDialog extends VBox {
    private final ToggleGroup group;
    private HBox buttons;
    private Label cardMsg;
    private RadioButton visa;
    private RadioButton amex;
    private Button select;

    public ChooseCardDialog(Stage primaryStage) {
        setSpacing(20);
        setAlignment(Pos.CENTER);

        group = new ToggleGroup();
        buttons = new HBox();
        Font msgFont = new Font("Arial", 18);
        Font btFont = new Font("Arial", 14);

        cardMsg = new Label("Select what type of card you want to verify");
        visa = new RadioButton("Visa/Mastercard");
        amex = new RadioButton("American Express");
        select = new Button("Select");

        cardMsg.setFont(msgFont);
        visa.setFont(btFont);
        amex.setFont(btFont);
        visa.setToggleGroup(group);
        visa.setSelected(true);
        amex.setToggleGroup(group);

        buttons.getChildren().addAll(visa, amex);
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER);
        select.setAlignment(Pos.CENTER);
        getChildren().addAll(cardMsg, buttons, select);

        EventHandler<ActionEvent> handler = (e -> {
            EnterNumberDialog end;
            if(visa.isSelected()) {
                end = new EnterNumberDialog(CardType.VISA);
            } else {
                end = new EnterNumberDialog(CardType.AMEX);
            }
            Stage stg = new Stage();
            Scene scene = new Scene(end, 340, 160);
            stg.setTitle("Check card number");
            stg.setScene(scene);
            stg.show();
            primaryStage.close();
            e.consume();
        });

        select.setOnAction(handler);
    }
}
