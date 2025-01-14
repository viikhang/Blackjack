import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Display {
    private PlayerInput input;
    private Dealer dealer;
    private Player player;

    private Deck deck;

    private VBox chips;

    private HBox gameBoard;
    private HBox dealerCards;
    private HBox playerCards;


    private Text balInfo;
    private Text currentBid;
    private Button startButton;
    private Button drawCard;
    private Button standButton;
    private VBox balAndButton;

    public Display(Player player, Dealer dealer) {
        this.dealer = dealer;
        this.player = player;
        deck = new Deck();
        input = new PlayerInput(this);
        drawChips();
        createBalanceAndButton();
    }

    public void drawChips() {
        chips = new VBox();
        //TODO Draw Circles
    }

    private void createBalanceAndButton() {
        balInfo = new Text("Balance " + player.getBalance());
        currentBid = new Text("Current bid; 0");
        startButton = new Button("Start");
        drawCard = new Button("Draw");
        standButton = new Button("Stand");
        balAndButton = new VBox(balInfo, startButton, drawCard, standButton);
    }

    private void createGameBoard() {
        dealerCards = new HBox();
        playerCards = new HBox();

        dealerCards.setAlignment(Pos.CENTER);
        playerCards.setAlignment(Pos.CENTER);
        gameBoard = new HBox(10, dealerCards, playerCards);

        //TODO add single invisible card as place holders, or just have minimum size

    }

    public VBox getBalAndButton() {
        return balAndButton;
    }

    public HBox getGameBoard() {
        return gameBoard;
    }

    public VBox getChips() {
        return chips;
    }
}
