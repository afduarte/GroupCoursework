import net.jmge.gif.Gif89Encoder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Matrix {
    char[] charSet;
    Integer rows;
    Integer cols;
    Color bg;
    Color font;
    Color highlight;

    public Matrix(String chars, Integer rows, Integer cols) {
        this.charSet = chars.toCharArray();
        this.rows = rows;
        this.cols = cols;
        this.bg = Color.BLACK;
        this.font = Color.green;
        this.highlight = Color.white;
    }

    public Gif89Encoder findWord(String word, Integer limit) throws IOException{

        Gif89Encoder genc = new Gif89Encoder();

        Boolean found = false;
        Integer i = 0;
        Integer width = this.cols * 16 + 2*16;
        Integer height = (this.rows+1) * 20 + 2*5;
        while (!found && i < limit) {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setColor(this.font);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
            found = this.drawFrame(g, word, i++);
            genc.addFrame(image);
            g.dispose();
        }

        if (!found) {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setColor(this.font);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
            g.drawString("Could not find a result in " + limit + " iterations.", 0,20);
            genc.addFrame(image);
        }

        genc.setUniformDelay(5);
        genc.setLoopCount(1);

        return genc;
    }


    private Boolean drawFrame(Graphics2D g, String highlight, Integer iteration) {
        Boolean found = false;
        for (Integer row = 1; row <= this.rows; row++) {
            g.translate(0, 20);
            String line = this.getRandomLine();
            g.setColor(this.font);
            Integer startHighlight = line.indexOf(highlight);
            Integer endHighlight = startHighlight + highlight.length();
            for (Integer col = 0; col < line.length(); col++) {
                if (!found && startHighlight.equals(col)) {
                    g.setColor(this.highlight);
                } else if (endHighlight.equals(col)) {
                    g.setColor(this.font);
                }

                g.drawString(String.valueOf(line.charAt(col)), (col+1)*16, 0);

            }

            if (! startHighlight.equals(-1)) {
                found = true;
            }
        }
        g.translate(0, 20);

        g.drawString("Iteration:" + iteration, 0, 0);
        return found;
    }

    protected String getRandomLine() {
        String line = "";
        for (Integer col = 0; col < this.cols; col++) {
            line += charSet[new Random().nextInt(this.charSet.length)];
        }

        return line;
    }

}
