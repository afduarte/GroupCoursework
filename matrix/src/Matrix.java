import net.jmge.gif.Gif89Encoder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

public class Matrix {
    char[] charSet;
    Integer rows;
    Integer cols;
    Color bg;
    Color font;
    Color highlight;
    Boolean variableSize = true;
    Boolean variableIntensity = true;

    public Matrix(String chars, Integer rows, Integer cols,
                  Color fgColor, Color bgColor, Boolean variableSize, Boolean variableIntensity) {
        this.charSet = chars.toCharArray();
        this.rows = rows;
        this.cols = cols;
        this.bg = bgColor;
        this.font = fgColor;
        this.highlight = Color.white;
        this.variableSize = variableSize;
        this.variableIntensity = variableIntensity;
    }

    public Gif89Encoder findWord(String word, Integer limit, Integer interval) throws IOException{

        Gif89Encoder genc = new Gif89Encoder();

        Boolean found = false;
        Integer i = 0;
        Integer width = this.cols * 16 + 2*16;
        Integer height = (this.rows+1) * 20 + 2*5;
        String powResult = getExponential(word.length(),charSet.length);
        Font defFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);
        ArrayList<Integer> indexes = new ArrayList<>();

        IntStream.range(1,this.cols+1).forEach(x -> indexes.add(x));
        Collections.shuffle(indexes);


        for(Integer j = 1; j <= rows+cols+2; j++)
        {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setFont(defFont);
            this.drawIntro(g,j,indexes);
            genc.addFrame(image);
            g.dispose();
        }


        while (!found && i < limit) {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setColor(this.font);
            g.setFont(defFont);
            found = this.drawFrame(g, word, i++,defFont);
            genc.addFrame(image);
            g.dispose();
        }

        if (!found) {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setColor(this.font);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
            g.drawString(" Could not find a result in " + limit + " iterations.", 0,20);
            g.drawString(" The chances of finding a word of length "+word.length(),0, 80);
            g.drawString(" with an alphabet of size "+this.charSet.length+" are only 1 on " + powResult,0, 100);
            genc.addFrame(image);
        }

        genc.setUniformDelay(interval);
        genc.setLoopCount(1);

        return genc;
    }

    private Color getRandomColorBrightness(Color color) {
        Integer r = new Random().nextInt(3);

        if (r.equals(0)) {
            return color;
        }

        if (r.equals(1)) {
            return color.brighter();
        }

        return color.darker();
    }


    private Boolean drawFrame(Graphics2D g, String highlight, Integer iteration,Font defFont) {
        Boolean found = false;
        for (Integer row = 1; row <= this.rows; row++) {
            g.translate(0, 20);
            String line = this.getRandomLine();
            Integer startHighlight = line.indexOf(highlight);
            Integer endHighlight = startHighlight + highlight.length();
            for (Integer col = 0; col < line.length(); col++) {
                if (startHighlight != -1 && !found && startHighlight <= col && endHighlight > col) {
                    g.setColor(this.highlight);
                    g.setFont(defFont);
                } else {
                    if(variableIntensity)
                        g.setColor(this.getRandomColorBrightness(this.font));
                    else
                        g.setColor(this.font);
                    if(this.variableSize)
                        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, randomSize(15,5)));
                }
                g.drawString(String.valueOf(line.charAt(col)), (col+1)*16, 0);

            }

            if (! startHighlight.equals(-1)) {
                found = true;
            }
        }
        g.translate(0, 20);

        g.setColor(this.font);
        g.setFont(defFont);
        g.drawString("Iteration:" + iteration, 0, 0);
        return found;
    }

    protected String getRandomLine() {
        String line = "";
        for (Integer col = 0; col < this.cols; col++) {
            line += charSet[randomSize(this.charSet.length,0)];
        }

        return line;
    }

    String getExponential(Integer exp, Integer base)
    {
            Double res = pow(base,exp);
            return res.toString()+"!";
    }

    Integer randomSize(Integer value, Integer offset)
    {
        Random rand = new Random();
        return rand.nextInt(value)+offset;
    }

    void drawIntro(Graphics2D g, Integer iterations,ArrayList<Integer> indexes)
    {
        for(Integer i: indexes)
        {
            g.translate(i*16,0);
            for(Integer j = 1; j<iterations; j++)
            {

                g.setColor(Color.green);
                String entry = "" + charSet[randomSize(this.charSet.length,0)];
                if(this.variableSize)
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, randomSize(15,5)));
                if(variableIntensity)
                    g.setColor(this.getRandomColorBrightness(this.font));
                g.drawString(entry,0,0);
                g.translate(0,20);
            }
            g.translate(-i*16,-iterations*20);
        }
    }

}