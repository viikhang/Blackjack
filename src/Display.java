import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Display {
    private PlayerInput input;
    private Dealer dealer;
    private Player player;

    private Deck deck;

    private VBox chips;

    private VBox gameBoard;
    private HBox dealerCards;
    private HBox playerCards;


    private Text balInfo;
    private Text currentBid;
    private Text currentCardValue;
    private Button startButton;
    private Button drawCard;
    private Button standButton;
    private Button exitButton;
    private Button clearFunds;

    private VBox balAndButton;

    public Display(Player player, Dealer dealer) {
        this.dealer = dealer;
        this.player = player;
        deck = new Deck();
        input = new PlayerInput(this, player, dealer, deck);
        drawChips();
        createBalanceAndButton();
        createGameBoard();
    }

    public void drawChips() {
        chips = new VBox();
        chips.setAlignment(Pos.CENTER);
        chips.setSpacing(10);
        chips.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));


        String chipPath = "ChipImages/";
        Chip chip5 = new Chip(5, chipPath + "Chip5.png");
        StackPane chipStack5 = chip5.getStackPane();
        chipStack5.setUserData(chip5);
        chipStack5.setOnMouseClicked(input.handleChipClickedOn(chipStack5));

        Chip chip10 = new Chip(10, chipPath + "Chip10.png");
        StackPane chipStack10 = chip10.getStackPane();
        chipStack10.setUserData(chip10);
        chipStack10.setOnMouseClicked(input.handleChipClickedOn(chipStack10));

        Chip chip20 = new Chip(20, chipPath + "Chip20.png");
        StackPane chipStack20 = chip20.getStackPane();
        chipStack20.setUserData(chip20);
        chipStack20.setOnMouseClicked(input.handleChipClickedOn(chipStack20));

        Chip chip50 = new Chip(50, chipPath + "Chip50.png");
        StackPane chipStack50 = chip50.getStackPane();
        chipStack50.setUserData(chip50);
        chipStack50.setOnMouseClicked(input.handleChipClickedOn(chipStack50));

        Chip chip100 = new Chip(100, chipPath + "Chip100.png");
        StackPane chipStack100 = chip100.getStackPane();
        chipStack100.setUserData(chip100);
        chipStack100.setOnMouseClicked(input.handleChipClickedOn(chipStack100));

        chips.getChildren().addAll(chipStack5, chipStack10, chipStack20, chipStack50,
                chipStack100);
    }

    private void createBalanceAndButton() {
        balInfo = new Text("Balance " + player.getBalance());
        currentBid = new Text("Current bid: 0");
        currentCardValue = new Text("Current card value: 0");

        startButton = new Button("Start");
        startButton.setOnAction(event -> input.handleStart());
        drawCard = new Button("Draw");
        drawCard.setOnAction(event -> input.handlePlayerDraw());
        standButton = new Button("Stand");
        standButton.setOnAction(event -> input.handleStand());
        exitButton = new Button("Exit");
        exitButton.setOnAction(event -> input.handleExit());
        clearFunds = new Button("Reset Bid");
        clearFunds.setOnAction(event -> input.handleClearFunds());

        balAndButton = new VBox(15, balInfo, currentBid, currentCardValue,
                startButton, drawCard, standButton, clearFunds, exitButton);
        balAndButton.setAlignment(Pos.CENTER);
        balAndButton.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
    }

    public void updateBalText() {
        balInfo.setText("Balance: " + player.getBalance());
    }

    public void updateCurrentBidText() {
        currentBid.setText("Current bid: " + player.getCurrentBid());
    }

    public void updateCurrentCardValue() {
        currentCardValue.setText("Current card value: " + player.getCurrentCardValue());
    }

    private void createGameBoard() {
        dealerCards = new HBox(5);
        dealerCards.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, null)));
        playerCards = new HBox(5);
        playerCards.setBackground(new Background(new BackgroundFill(Color.BLUE,
                CornerRadii.EMPTY, null)));

        dealerCards.setAlignment(Pos.CENTER);
        playerCards.setAlignment(Pos.CENTER);
        gameBoard = new VBox(10, dealerCards, playerCards);
        gameBoard.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, null)));
        gameBoard.setAlignment(Pos.CENTER);
        //TODO add single invisible card as place holders, or just have minimum size

        //TODO, FIX THIS, IF BOTH PLAYERS DRAW THE SAME CARD, ONLY ONE WILL
        // BE DISPLAYED!
        Card playerCard = deck.drawRandomCard();
        Card dealerCard;
        //Ensure that the dealer and the player don't draw the same card.
        do {
            dealerCard = deck.drawRandomCard();
        } while (dealerCard.equals(playerCard));


//        playerCards.getChildren().add(playerCard.getCardPane());
//        dealerCards.getChildren().add(dealerCard.getCardPane());
//        System.out.println("Testing player: " + playerCard.getValue());
//        System.out.println("Testing dealer: " + dealerCard.getValue());
    }

    public boolean checkDealerCards(Card cardDrawn){
        StackPane cardPane = cardDrawn.getCardPane();
        //If the card drawn is in the dealers hand, then we need to redraw
        return dealerCards.getChildren().contains(cardPane);
    }

    public boolean checkPlayerCards(Card cardDrawn){
        StackPane cardPane = cardDrawn.getCardPane();
        //If the card drawn is in the players hand, then we need to redraw a new card
        return playerCards.getChildren().contains(cardPane);
    }

    public void clearGameBoard() {
        dealerCards.getChildren().clear();
        playerCards.getChildren().clear();
    }

    public VBox getBalAndButton() {
        return balAndButton;
    }

    public VBox getGameBoard() {
        return gameBoard;
    }

    public VBox getChips() {
        return chips;
    }

    public Player getPlayer() {
        return player;
    }

    public HBox getDealerCards() {
        return dealerCards;
    }

    public HBox getPlayerCards() {
        return playerCards;
    }
}
