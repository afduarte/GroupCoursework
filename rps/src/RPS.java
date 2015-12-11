
public class RPS {
    public String[] general;
    public int game;
    public int userScore;
    public int compScore;
    public String user="";
    public String comp;
    public int outcome;

    public void reset() {
        this.userScore = 0;
        this.compScore = 0;
        this.user ="";
        this.comp="";
    }

    public RPS(){
        this.game=0;
        this.general = new String[]{"rock", "paper", "scissors", "fire", "water", "sponge", "air"};
        this.userScore = 0;
        this.compScore = 0;
    }

}
