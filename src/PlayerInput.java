import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;

public class PlayerInput {
    private final Display display;
    private boolean startedGame = false;
    private final Player player;
    private final Dealer dealer;
    private final Deck deck;

    private final Card backCard;
    private boolean drewBack = false;

    public PlayerInput(Display display, Player player, Dealer dealer, Deck deck) {
        this.display = display;
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;

        backCard = deck.getCardBack();
    }

    public void handleStart() {
        System.out.println("Start Button");
        if(player.getCurrentBid() == 0) {
            Alert noBidMade = new Alert(Alert.AlertType.INFORMATION);
            noBidMade.setTitle("Please place a bid!");
            noBidMade.setContentText("Please place a bid to play!");
            noBidMade.showAndWait();
            return;
        }
        
        if (!startedGame) {
            //Have the player draw two cards, and then have the dealer draw
            //two cards but one of them is not revealed
            handlePlayerDraw();
            handlePlayerDraw();
            startedGame = true;
            handleDealerDraw();
        }
    }

    public void handlePlayerDraw() {
        //TODO NEED TO CHECK IF THE OTHER PLAYER ATTEMPTS TO DRAW THE SAME
        // CARD, IF THEY DO, THEN WE NEED TO SKIP

        //TODO NEED TO ALSO HANDLE ACE'S
        //Draw a single card and add it to players hand
        System.out.println("Draw button");
        Card card;
        while (true) {
            card = deck.drawRandomCard();
            try {
                int cardValue = card.getValue();
                display.getPlayerCards().getChildren().add(card.getCardPane());
                System.out.println("Card Value : " + cardValue);
                player.updateValue(cardValue);

                if (player.greaterThanTwentyOne()) {
                    System.out.println("went here");
                    handleBust();
                }

                break; // Exit the loop if no exception is thrown
            } catch (IllegalArgumentException e) {
                // Log the issue and retry drawing another card
                System.out.println("Duplicate card drawn, retrying...");
            }
        }
    }

    private void handleBust() {
        System.out.println("In bust");

        Alert playerBusted = new Alert(Alert.AlertType.INFORMATION);
        playerBusted.setTitle("Dealer Won!");
        playerBusted.setContentText("You Lost! You busted!");

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            playerBusted.show();
            resetGame();
        });
        pause.play();
    }

    public void handleDealerDraw() {
        Card card;
        while (true) {
            card = deck.drawRandomCard();
            try {
                int cardValue = card.getValue();

                display.getDealerCards().getChildren().add(card.getCardPane());
                dealer.updateCardValue(cardValue);

                break; // Exit the loop if no exception is thrown
            } catch (IllegalArgumentException e) {
                // Log the issue and retry drawing another card
                System.out.println("Duplicate card drawn, retrying...");
            }
        }

        if (!drewBack) {
            handleDrawCardBack();
            drewBack = true;
        }
    }

    public void handleDrawCardBack() {
        display.getDealerCards().getChildren().add(backCard.getCardPane());
    }

    private void handleRemoveCardBack() {
        display.getDealerCards().getChildren().remove(backCard.getCardPane());
        //After removing the card back, we want to have the dealer draw a card

        //Have the dealer continue drawing cards until their total is greater
        // than 17
        while (dealer.getCurrentCardValue() < 17) {
            handleDealerDraw();
        }

        Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
        boolean playerWon = false;

        //If the dealer's card value is over 21, then they busted
        if (dealer.getCurrentCardValue() > 21) {
            winnerAlert.setTitle("Player Won!");
            winnerAlert.setContentText("You win! Dealer busted!");
            playerWon = true;

            //If dealer's card value is less than player's card value, then
            // they've lost
        } else if (dealer.getCurrentCardValue() < player.getCurrentCardValue()) {
            winnerAlert.setTitle("Player Won!");
            winnerAlert.setTitle("You win! Dealer's card value is less than " +
                    "yours!");
            playerWon = true;
            //Else, the dealer's card value is greater so they have won
        } else {
            winnerAlert.setTitle("Dealer Won!");
            winnerAlert.setContentText("You lost! Dealer's card value is " +
                    "greater than yours!");
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        boolean finalPlayerWon = playerWon;

        pause.setOnFinished(event -> {
            winnerAlert.show();
            if (finalPlayerWon) {
                player.handleBidWin();
            } else {
                player.handleBidLoss();
            }

            resetGame();
        });
        pause.play();
    }

    private void resetGame() {
        startedGame = false;
        drewBack = false;
        handleClearFunds(); //Clear current bid amount
        display.clearGameBoard();
        display.updateBalText();
    }

    public void handleStand() {
        System.out.println("Stand button");
        //Have dealer draw now
        handleRemoveCardBack();
    }

    public EventHandler<MouseEvent> handleChipClickedOn(StackPane stackPane) {
        //When a chip is clicked on, need to make sure that the player currently
        //has enough funds to make a bid
        Chip chip = (Chip) stackPane.getUserData();
        return event -> {
            System.out.println("Chip Value: " + chip.getValue());
            System.out.println("Chip clicked!");
            int chipAmount = chip.getValue();
            int playerBalance = player.getBalance();
            //Check if the player has enough funds to place the bid
            if (chipAmount > playerBalance) {
                handleInvalidFunds();
            } else {
                //update player current bid and text
                player.updateCurrentBid(chipAmount);
                display.updateCurrentBidText();
            }
        };
    }

    private void handleInvalidFunds() {
        Alert invalidFundsAlert = new Alert(Alert.AlertType.INFORMATION);
        invalidFundsAlert.setTitle("Invalid Funds");
        invalidFundsAlert.setContentText("You do not have enough funds to " +
                "make this bid, please place a new bid amount!");
        handleClearFunds();
        invalidFundsAlert.showAndWait();
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

    public void handleClearFunds() {
        player.resetCurrentBid();
        display.updateCurrentBidText();
    }
}
