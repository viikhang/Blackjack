import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;
    private Image cardBackImage;
    private Card cardBack;
    private int numDecks;
    private Map<String, Image> images;
    //NEED TO MAKE THIS MAP BE USED, THE IDEA IS THAT WHEN A CARD IS DRAWN AND NEEDS TO BE DISPLAYED, INSTEAD OF HAVING
    // EACH CARD OBJECT HAVING THE IMAGE SAVED, HAVE THIS CLASS SAVE THE IMAGES
    public Deck(int numDecks) {
        this.numDecks = numDecks;
        cards = new ArrayList<>();
        loadCards();
    }

    private void loadCards() {
//        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
//        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        String[] suits = {"clubs"};
        String[] ranks = {"2"};
        String resourcePath = "CardImages/";
        for (String suit : suits) {
            for (String rank : ranks) {
                String fileName = resourcePath + rank + "_of_" + suit + ".png";
                File imageFile = new File(fileName);
                if (!imageFile.exists()) {
                    System.out.println("File not found");
                    System.exit(0);

                }
                String imagePath = "file:" + imageFile.getAbsolutePath();
                //Need to add the card this many times to the deck
                for(int i = 0; i < numDecks; i++) {
                    Card card = getCard(suit, rank, imagePath);
                    cards.add(card);
                }
            }
        }
        //TODO Why is String being passed for Image variable
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
        //TODO FIX THIS, DON'T SAVE IMAGE WITH EACH CARD
        return new Card(suit, value, image);
    }

    public Card drawRandomCard() {
        Random ran = new Random();
        int index = ran.nextInt(cards.size());
        System.out.println(index);
        Card drawnCard = cards.get(index);
        //After drawing card, make sure to remove it from the deck
        cards.remove(index);
        return drawnCard;
    }

    public Card getCardBack() {
        return cardBack;
    }
}
