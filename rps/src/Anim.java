import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

import net.jmge.gif.Gif89Encoder;


public class Anim{

    public static void getGif(String user, String comp, int outcome){
        int x = 0, velX = 2;

        try {
            Gif89Encoder genc = new Gif89Encoder();
            System.out.println("Working Directory = " +System.getProperty("user.dir"));
            BufferedImage img = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/symbols/"+user+".gif"));
            BufferedImage img2 =ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/symbols/"+comp+".gif"));

            BufferedImage image = new BufferedImage(800,400, BufferedImage.TYPE_INT_ARGB);


            while(x<450){
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, 800, 400);
                g.drawImage(img,x-100,0,null);
                g.drawImage(img2,800-x,0,null);
                genc.addFrame(image);
                g.dispose();

                x = x + 6*velX;
            }

            genc.setUniformDelay(5);
            genc.setLoopCount(0);
            FileOutputStream out = new FileOutputStream("/home/antero/SD1/GroupCoursework/rps/web/img/anim.gif");
            genc.encode(out);

        }catch(Exception e){
            System.err.println(e);
        }
    }
}
