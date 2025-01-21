public class Dealer {
    private int currentCardValue = 0;
    private Card currentCard = null;

    public Dealer() {
    }

    public int getCurrentCardValue() {
        return currentCardValue;
    }

    public void updateCardValue(int cardValue) {
        currentCardValue += cardValue;
    }

    public void setCurrentCardValue(int currentCardValue) {
        this.currentCardValue = currentCardValue;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }
}
