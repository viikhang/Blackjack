import javafx.scene.image.Image;

public class Card {
    private String suit;
    private int value;
    //Jack = 11,
    // Queen = 12,
    // King = 13,
    // Ace = 1 or 11,
    // depends on which value keeps player's hand closer to 21
    private Image cardImage;
    public Card(String suit, int value, Image cardImage) {
        this.suit = suit;
        this.value = value;
        this.cardImage = cardImage;
    }
}
