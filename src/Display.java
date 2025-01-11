import javafx.scene.layout.HBox;

public class Display {
    private Dealer dealer;
    private Player player;
    private HBox chips;
    private HBox drawCard;
    private HBox balInfo;
    private HBox dealerCards;
    private HBox playerCards;
    public Display(Player player, Dealer dealer) {
        this.dealer = dealer;
        this.player = player;
        drawChips();
    }
    public void drawChips(){
        chips = new HBox();
    }

}
