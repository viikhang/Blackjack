import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> args = getParameters().getRaw();
        if (args.size() != 1) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }
        int balance = 0;
        try {
            balance = Integer.parseInt(args.getFirst());
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument given, please enter in an " +
                    "integer value!");
        }

        Player player = new Player(balance);
        Dealer dealer = new Dealer();
        Display display = new Display(player, dealer);

        VBox rightInfo = display.getBalAndButton();
        VBox leftChips = display.getChips();
        HBox gameBoard = display.getGameBoard();
        BorderPane pane = new BorderPane();
        pane.setRight(rightInfo);
        pane.setLeft(leftChips);
        pane.setCenter(gameBoard);


        Scene root = new Scene(pane);
    }

}
