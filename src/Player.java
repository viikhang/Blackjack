public class Player {
    private int balance;
    private int currentBid = 0;
    private int currentCardValue = 0;
    private boolean hasAce = false;

    public Player(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void updateValue(int value) {
        currentCardValue += value;
        System.out.println("current card value " + currentCardValue);
    }

    public void handleBidWin() {
        balance += currentBid;
    }

    public void handleBidLoss() {
        balance -= currentBid;
    }

    public void updateCurrentBid(int value) {
        currentBid += value;
    }

    public void resetCurrentBid() {
        currentBid = 0;
    }


    public boolean greaterThanTwentyOne() {
        return currentCardValue > 21;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCurrentCardValue() {
        return currentCardValue;
    }

    public void setCurrentCardValue(int currentCardValue) {
        this.currentCardValue = currentCardValue;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public boolean isHasAce() {
        return hasAce;
    }

    public void setHasAce(boolean hasAce) {
        this.hasAce = hasAce;
    }
}
