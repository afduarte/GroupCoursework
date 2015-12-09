import net.jmge.gif.Gif89Encoder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class RPS {
    public String[] general;
    public int game;


    public RPS(){
        this.game=0;
        this.general = new String[]{"rock", "paper", "scissors", "fire", "water", "sponge", "air"};
    }

}
