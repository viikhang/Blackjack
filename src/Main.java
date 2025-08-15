import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> args = getParameters().getRaw();
        //Make it so that user can pick number of deck's they would like to play with
        if (!((args.size() == 1) || args.size() == 2)) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }
        int balance = 0;
        String fileName = args.getFirst();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line = reader.readLine();
            balance = Integer.parseInt(line);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        int numDecks = 20;
        if (args.size() == 2) {
            numDecks = Integer.parseInt(args.get(1));
        }

        Player player = new Player(balance);
        Dealer dealer = new Dealer();
        Display display = new Display(player, dealer, numDecks);

        VBox rightInfo = display.getBalAndButton();
        rightInfo.setMinWidth(120);
        VBox leftChips = display.getChips();
        VBox gameBoard = display.getGameBoard();
        BorderPane pane = new BorderPane();
        pane.setRight(rightInfo);
        pane.setLeft(leftChips);
        pane.setCenter(gameBoard);

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("Poker");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            try {
                FileWriter myWriter = new FileWriter("balance.txt");
                String value = String.valueOf(player.getBalance());
                myWriter.write(value);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Error occurred!");
            }
            System.exit(0);
        });
        primaryStage.show();
    }

}
