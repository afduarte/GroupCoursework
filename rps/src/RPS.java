import java.util.ArrayList;

public class RPS {
    public ArrayList<String> symbols;
    public int game;
    public int userScore;
    public int compScore;
    public String user="";
    public String comp;
    public int outcome;

    public void newGame() {
        this.user ="";
        this.comp="";
        this.symbols.clear();
    }

    public void reset() {
        this.userScore = 0;
        this.compScore = 0;
        this.user ="";
        this.comp="";
    }

    public RPS(){
        this.symbols = new ArrayList<>();
        this.game=0;
        this.userScore = 0;
        this.compScore = 0;
    }

}
