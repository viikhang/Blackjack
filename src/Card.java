import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card {
    private String suit;
    private int value;
    //Jack = 11,
    // Queen = 12,
    // King = 13,
    // Ace = 1 or 11,
    // depends on which value keeps player's hand closer to 21
    private Image cardImage;
    private StackPane cardPane;
    private ImageView imageView;

    public Card(Image cardImage) {
        this.cardImage = cardImage;
        createImageView();
    }

    public Card(String suit, int value, Image cardImage) {
        this.suit = suit;
        this.value = value;
        this.cardImage = cardImage;
        createImageView();
    }

    private void createImageView() {
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

    @Override
    public boolean equals(Object obj) {


        return super.equals(obj);
    }
}
