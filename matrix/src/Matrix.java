import net.jmge.gif.Gif89Encoder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    Boolean variableSize;
    Boolean charJam;
    Boolean displayIterations;
    Boolean intro;
    Boolean outro;
    Boolean results;
    Font defFont;
    Font matrixFont = null;
    Font matrixCode = null;

    public Matrix(String chars, Integer rows, Integer cols, Color fgColor, Color bgColor,
                  Boolean variableSize, Boolean charJam, Boolean displayIterations, Boolean intro,
                  Boolean outro, Boolean results) {
        this.charSet = chars.toCharArray();
        this.rows = rows;
        this.cols = cols;
        this.bg = bgColor;
        this.font = fgColor;
        this.highlight = Color.white;
        this.variableSize = variableSize;
        this.charJam = charJam;
        this.defFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);
        this.intro = intro;
        this.outro = outro;
        this.results = results;
        this.displayIterations = displayIterations;
        try {
            this.matrixFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("C:\\Users\\Elohimir\\Documents\\GitHub\\SD1-GCW\\matrix\\web\\matrixFont.ttf")); //TODO fix catalina wd
        } catch (FontFormatException | IOException e) {
            matrixFont = new Font(Font.MONOSPACED,Font.PLAIN,16);
            e.printStackTrace();
        }
        matrixFont = matrixFont.deriveFont(16f);
        try {
            this.matrixCode = Font.createFont(Font.TRUETYPE_FONT,
                    new File("C:\\Users\\Elohimir\\Documents\\GitHub\\SD1-GCW\\matrix\\web\\matrix-code.ttf")); //TODO fix catalina wd
        } catch (FontFormatException | IOException e) {
            matrixCode = new Font(Font.MONOSPACED,Font.PLAIN,16);
            e.printStackTrace();
        }
        matrixCode = matrixCode.deriveFont(16f);
    }

    public Gif89Encoder findWord(String word, Integer limit, Integer interval) throws IOException{

        Gif89Encoder genc = new Gif89Encoder();

        Boolean found = false;
        Integer i = 0;
        Integer width = this.cols * 16 + 2*16;
        Integer height = (this.rows+1) * 20 + 2*5;
        String powResult = getExponential(word.length(),charSet.length);

        ArrayList<Integer> indexes = new ArrayList<>();

        IntStream.range(1,this.cols+1).forEach(x -> indexes.add(x));
        Collections.shuffle(indexes);


        if(intro)
        {
            for(Integer j = 1; j <= rows+cols+2; j++)
            {
                BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g.setColor(this.bg);
                g.fillRect(0,0,width, height);
                g.setFont(this.defFont);
                this.drawInOut(g,j,indexes,0,20);
                genc.addFrame(image);
                g.dispose();
            }
        }


        while (!found && i < limit) {
            BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g.setColor(this.bg);
            g.fillRect(0,0,width, height);
            g.setColor(this.font);
            g.setFont(this.defFont);
            found = this.drawFrame(g, word, i++);
            if(found)
                for(Integer j = 0; j < 25; j++)
                    genc.addFrame(image);
            genc.addFrame(image);
            g.dispose();
        }

        if(outro)
        {
            for(Integer j = this.cols; j >0; j--)
            {
                BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = image.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g.setColor(this.bg);
                g.fillRect(0,0,width, height);
                g.setFont(this.defFont);
                this.drawInOut(g,j,indexes,height,-20);
                genc.addFrame(image);
                g.dispose();
            }
        }


        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setColor(this.bg);
        g.fillRect(0,0,width, height);
        if(results)
        {
            g.setColor(this.font);
            if (!found) {
                g.setFont(matrixFont);
                g.drawString("SORRY!",(cols*8)-30,40);
                g.setFont(defFont);
                g.drawString(" Could not find a result in " + limit + " iterations.", 70,110);
            }
            else
            {
                g.setFont(matrixFont);
                g.drawString("FOUND!",(cols*8)-30,40);
                g.setFont(defFont);
                g.drawString(" Result found in " + limit + " iterations.", 70,110);
            }
            g.drawString(" The chances to generate the word '" + word + "' of length "+word.length(),70, 150);
            g.drawString(" with an alphabet of size "+this.charSet.length+" are only 1 in " + powResult,70, 190);
            genc.addFrame(image);
            g.dispose();
        }

        genc.setUniformDelay(interval);
        genc.setLoopCount(1);

        return genc;
    }


    private Boolean drawFrame(Graphics2D g, String highlight, Integer iteration) {
        Boolean found = false;
        for (Integer row = 1; row <= this.rows; row++) {
            g.translate(0, 20);
            String line = this.getRandomLine();
            Integer startHighlight = line.indexOf(highlight);
            Integer endHighlight = startHighlight + highlight.length();
            for (Integer col = 0; col < line.length(); col++) {
                if (startHighlight != -1 && !found && startHighlight <= col && endHighlight > col) {
                    g.setColor(this.highlight);
                    g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
                } else {
                    g.setColor(this.font);
                    if(this.variableSize)
                    {
                        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, randomSize(15,5)));
                    }
                    if(this.charJam)
                    {
                        randomFont(g);
                    }
                }
                g.drawString(String.valueOf(line.charAt(col)), (col+1)*16, 0);
                g.setFont(this.defFont);
            }

            if (! startHighlight.equals(-1)) {
                found = true;
            }
        }
        g.translate(0, 20);

        if(displayIterations)
        {
            g.setColor(this.font);
            g.setFont(this.defFont);
            g.drawString("Iteration:" + iteration+1, 0, 0);
        }
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


    void drawInOut(Graphics2D g, Integer iterations,ArrayList<Integer> indexes,
                   Integer vertStart, Integer vertDelta)
    {
        g.translate(0,vertStart);
        for(Integer i: indexes)
        {
            g.translate(i*16,0);
            for(Integer j = 1; j<iterations; j++)
            {
                g.setColor(this.font);
                String entry = "" + charSet[randomSize(this.charSet.length,0)];
                if(this.variableSize)
                    g.setFont(new Font("matrixCode", Font.PLAIN, randomSize(15,5)));
                if(this.charJam)
                    g.setFont(this.matrixCode);
                if(j == iterations -1)
                    if(vertDelta>0)
                        g.setColor(Color.white);
                g.drawString(entry,0,0);
                g.translate(0,vertDelta);
            }
            g.translate(-i*16,-iterations*vertDelta);
        }
    }


    void randomFont(Graphics2D g)
    {
        Integer temp = randomSize(2,0);
        if(temp == 0)
            g.setFont(this.matrixCode);
        else
            g.setFont(this.matrixFont);
    }
}