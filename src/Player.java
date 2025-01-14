public class Player {
    private int balance;

    private int currentCardValue = 0;

    public Player(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
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
}
