import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card {
    private final String suit;
    private final int value;
    //Jack = 11,
    // Queen = 12,
    // King = 13,
    // Ace = 1 or 11,
    // depends on which value keeps player's hand closer to 21
    private Image cardImage;
    private StackPane cardPane;
    private ImageView imageView;

    public Card(String suit, int value, Image cardImage) {
        this.suit = suit;
        this.value = value;
        this.cardImage = cardImage;

        imageView = new ImageView(cardImage);
        imageView.setFitHeight(160);
        imageView.setFitWidth(80);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setPreserveRatio(true);

        cardPane = new StackPane(imageView);

    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public Image getCardImage() {
        return cardImage;
    }

    public StackPane getCardPane() {
        return cardPane;
    }
}
