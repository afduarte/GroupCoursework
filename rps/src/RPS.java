
public class RPS {
    public String[] general;
    public int game;
    public int userScore;
    public int compScore;

    public void reset() {
        this.userScore = 0;
        this.compScore = 0;
    }

    public RPS(){
        this.game=0;
        this.general = new String[]{"rock", "paper", "scissors", "fire", "water", "sponge", "air"};
        this.userScore = 0;
        this.compScore = 0;
    }

}
