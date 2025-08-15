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
        if (player.getCurrentBid() == 0) {
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
        //Draw a single card and add it to players hand
        System.out.println("Draw button");
        Card card;
        while (true) {
            card = deck.drawRandomCard();
            System.out.println("Card drawn " + card.getSuit());
            try {
                //Check if dealer already has the card, if they do, then redraw
                if (display.checkDealerCards(card)) {
                    System.out.println("Happened");
                    continue;
                }

                int cardValue = card.getValue();

                //If the user draws an ace, then we need to check which value the ace should take on
                if (card.getSuit().equals("ace")) {
                    player.setHasAce(true);
                    //Ace can either be a 1 or a 11
                    if (player.getCurrentCardValue() >= 11) {
                        cardValue = 1; //If current hand greater than 11, then ace must be a one
                    } else {
                        cardValue = 11;
                    }
                }

                //TODO IF THE PLAYER DRAWS 21 JUST HAVE THEM WIN INSTANTLY

                display.getPlayerCards().getChildren().add(card.getCardPane());
                System.out.println("Card Value : " + cardValue);
                player.updateValue(cardValue);
                display.updateCurrentCardValue();

                if (player.greaterThanTwentyOne()) {
                    System.out.println("Player busted!");
                    handleBust();
                }

                break; // Exit the loop if no exception is thrown
            } catch (IllegalArgumentException e) {
                //Log the issue and retry drawing another card
                System.out.println(e.getMessage());
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
                //If dealer draws a card that player already has, then have the
                // dealer draw again
                if (display.checkPlayerCards(card)) {
                    System.out.println("Happened 2");
                    continue;

                }

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
        //than 17
        while (dealer.getCurrentCardValue() < 17) {
            handleDealerDraw();
        }

        Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
        boolean playerWon = false;
        boolean playersTied = false;

        int dealerValue = dealer.getCurrentCardValue();
        int playerValue = player.getCurrentCardValue();
        //If the dealer's card value is over 21, then they busted
        if (dealerValue > 21) {
            winnerAlert.setTitle("Player Won!");
            winnerAlert.setContentText("You win! Dealer busted!");
            playerWon = true;

            //If dealer's card value is less than player's card value, then
            // they've lost
        } else if (dealerValue < playerValue) {
            winnerAlert.setTitle("Player Won!");
            winnerAlert.setContentText("You win! Dealer's card value is less than " +
                    "yours!");
            playerWon = true;
            //Else, the dealer's card value is greater so they have won

        } else if (dealerValue == playerValue) {
            winnerAlert.setTitle("No One Won!");
            winnerAlert.setContentText("Both players have the same amount of points, no one wins!");
            playersTied = true;
        } else { //Else, the dealer's card value is greater so they have won
            winnerAlert.setTitle("Dealer Won!");
            winnerAlert.setContentText("You lost! Dealer's card value is " +
                    "greater than yours!");
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        boolean finalPlayerWon = playerWon;
        boolean finalPlayersTied = playersTied;
        pause.setOnFinished(event -> {
            winnerAlert.show();
            if (!finalPlayersTied) {
                if (finalPlayerWon) {
                    player.handleBidWin();
                } else {
                    player.handleBidLoss();
                }
            }

            resetGame();
        });
        pause.play();
    }


    private void resetGame() {
        startedGame = false;
        drewBack = false;
        player.setHasAce(false);
        handleClearFunds(); //Clear current bid amount
        display.clearGameBoard();
        display.updateBalText();
        player.setCurrentCardValue(0);
        dealer.setCurrentCardValue(0);
        display.updateCurrentCardValue();
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
