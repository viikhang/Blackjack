public class PlayerInput {
    private Display display;
    private boolean startedGame = false;
    public PlayerInput(Display display) {
        this.display = display;
    }

    private void handleStart() {
        if(!startedGame) {
            handleDraw();
            handleDraw();
            startedGame = true;
        }
    }

    private void handleDraw() {
        //Draw a single card and add it to players hand
    }

    private void handleStand() {
        //Have dealer draw now

        //Most logic should be written here.
    }

    private void handleChipClickedOn() {
        //When a chip is clicked on, need to make sure that the player currently
        //has enough funds to make a bid
    }
}
