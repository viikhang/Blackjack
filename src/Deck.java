import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;
    public Deck() {
        cards = new ArrayList<>();
    }
    private void loadCards() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};

        for(String suit : suits){
            for(String rank : ranks) {
                String fileName = rank + "_of_" + suit;
                File imageFile = new File(fileName);
                if(!imageFile.exists()){
                    System.out.println("Image not found");
                    System.exit(0);
                }
                Card card = getCard(suit, rank, fileName);
                cards.add(card);
            }
        }
    }

    private static Card getCard(String suit, String rank, String fileName) {
        Image image = new Image(fileName);
        int value;
        if(rank.equals("jack")){
            value = 11;
        } else if(rank.equals("queen")){
            value = 12;
        } else if(rank.equals("king")){
            value = 13;
        } else if(rank.equals("ace")){
            value = 1;
        } else {
            value = Integer.parseInt(rank);
        }
        return new Card(suit,value, image);
    }

    public Card drawRandomCard(){
        Random ran  = new Random();
        int index = ran.nextInt(cards.size());
        return cards.get(index);
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.loadCards();
    }
}
