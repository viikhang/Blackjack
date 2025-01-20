import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.FileWriter;
import java.io.IOException;

public class PlayerInput {
    private Display display;
    private boolean startedGame = false;
    private Player player;
    private Dealer dealer;
    private Deck deck;

    public PlayerInput(Display display, Player player, Dealer dealer, Deck deck) {
        this.display = display;
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;

    }

    public void handleStart() {
        System.out.println("Start Button");
        if(!startedGame) {
            handlePlayerDraw();
            handlePlayerDraw();
            startedGame = true;
        }

    }

    public void handlePlayerDraw() {
        //Draw a single card and add it to players hand
        System.out.println("Draw button");
        Card card;
        while (true) {
            card = deck.drawRandomCard();
            try {
                display.getPlayerCards().getChildren().add(card.getCardPane());
                break; // Exit the loop if no exception is thrown
            } catch (IllegalArgumentException e) {
                // Log the issue and retry drawing another card
                System.out.println("Duplicate card drawn, retrying...");
            }
        }
    }

    public void handleDealerDraw(){
        Card card;
        while (true) {
            card = deck.drawRandomCard();
            try {
                display.getDealerCards().getChildren().add(card.getCardPane());
                break; // Exit the loop if no exception is thrown
            } catch (IllegalArgumentException e) {
                // Log the issue and retry drawing another card
                System.out.println("Duplicate card drawn, retrying...");
            }
        }
    }

    public void handleStand() {
        System.out.println("Stand button");
        //Have dealer draw now

        //Most logic should be written here.
    }

    public EventHandler<MouseEvent> handleChipClickedOn(StackPane stackPane) {
        //When a chip is clicked on, need to make sure that the player currently
        //has enough funds to make a bid
        Chip chip = (Chip) stackPane.getUserData();

        return event -> {
            System.out.println("Chip Value: " + chip.getValue());
            System.out.println("Chip clicked!");
            //TODO
        };
    }

    public void handleExit() {
        System.out.println("Exit Button");

        try {
            FileWriter myWriter = new FileWriter("balance.txt");
            String value = String.valueOf(player.getBalance());
            myWriter.write(value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurred!");
        }

        System.exit(0);
    }
}
