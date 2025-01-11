import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> args = getParameters().getRaw();
        if(args.size() != 1){
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }
        int balance = 0;
        try{
            balance = Integer.parseInt(args.getFirst());
        } catch (NumberFormatException e){
            System.out.println("Invalid argument given, please enter in an " +
                    "integer value!");
        }
        Deck deck = new Deck();
        Player player = new Player(balance, deck);
        Dealer dealer = new Dealer(deck);
        Display display = new Display(player,dealer);
    }
}
