import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;
    private Image cardBackImage;
    private Card cardBack;

    public Deck() {
        cards = new ArrayList<>();
        loadCards();
    }

    private void loadCards() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        String resourcePath = "CardImages/";
        for (String suit : suits) {
            for (String rank : ranks) {
                String fileName = resourcePath + rank + "_of_" + suit + ".png";
                File imageFile = new File(fileName);
                if (!imageFile.exists()) {
//                    System.out.println("Absolute path: " + imageFile.getAbsolutePath());
//                    System.out.println("Image not found");
                    System.exit(0);
                }
                String imagePath = "file:" + imageFile.getAbsolutePath();
                Card card = getCard(suit, rank, imagePath);
                cards.add(card);
            }
        }
        cardBackImage = new Image("file:" + resourcePath + "cardBack.png");

        cardBack = new Card(cardBackImage);
    }

    private Card getCard(String suit, String rank, String fileName) {
        Image image = new Image(fileName);

        //THIS ISN't CLEAN CODE
        int value;
        if (rank.equals("jack")) {
            value = 10;
        } else if (rank.equals("queen")) {
            value = 10;
        } else if (rank.equals("king")) {
            value = 10;
        } else if (rank.equals("ace")) {
            value = 1;
        } else {
            value = Integer.parseInt(rank);
        }
        return new Card(suit, value, image);
    }

    public Card drawRandomCard() {
        Random ran = new Random();
        int index = ran.nextInt(cards.size());
        return cards.get(index);
    }

    public Card getCardBack() {
        return cardBack;
    }
}
