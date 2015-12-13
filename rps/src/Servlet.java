import net.jmge.gif.Gif89Encoder;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Servlet extends HttpServlet {
    RPS rps = new RPS();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        rps.game=0;
        rps.user="";
        rps.comp="";
        if(request.getParameter("reset")!=null)
            rps.reset();
        response.sendRedirect("Servlet");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(rps.game==0 && request.getParameter("game")==null) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            printIndex(out);
            rps.newGame();
        }else if(rps.user.equals("") && request.getParameter("symbol")==null){

            if(request.getParameter("game")!=null)
                rps.game = Integer.parseInt(request.getParameter("game"));

            rps.symbols.add("rock");
            rps.symbols.add("paper");
            rps.symbols.add("scissors");

            if (rps.game >= 5) {
                rps.symbols.add("fire");
                rps.symbols.add("water");
            }

            if (rps.game == 7) {
                rps.symbols.add("sponge");
                rps.symbols.add("air");

            }


            PrintWriter out = response.getWriter();
            printMove(out, rps.symbols, rps.game);

        }else if(rps.game!=0 && request.getParameter("symbol")!=null){
            rps.user = request.getParameter("symbol");
            int index = randInt(0,(rps.game*100+99)/100);
            rps.comp= rps.symbols.get(index);
            rps.outcome = getOutcome(rps.user,rps.comp);

            if (rps.outcome == 1) {
                rps.userScore++;
            } else if (rps.outcome == -1) {
                rps.compScore++;
            } else{
                System.out.println("Error in outcome");
            }

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            printResult(out,rps.userScore,rps.compScore);
            printExplanation(out,rps.game);

        }

        if(rps.game!=0 && request.getParameter("img")!=null) {
            response.setContentType("image/gif");
            getGif(rps.user, rps.comp, rps.outcome).encode(response.getOutputStream());
        }

    }

    public void printIndex(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Rock, Paper, Scissors Extreme</title>");
        out.println("<style>");
        out.println("label> input { visibility: hidden; position: absolute;}");
        out.println("label> input + img{ cursor:pointer; border:2px solid transparent; height: 50px;}");
        out.println("label> input:not(:checked):hover + img{ border:5px solid transparent; }");
        out.println("label> input:checked + img{ height: 75px;}");
        out.println("td{ width: 140px; height: 100px; text-align: center;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table align=\"center\">");
        out.println("<thead><th colspan=\"3\"><h1>Rock, Paper, Scissors</h1></th></thead>");
        out.println("<tbody>");
        out.println("<form>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<label>");
        out.println("<input type=\"radio\" name=\"game\" value=\"3\" checked />");
        out.println("<img src=\"img/buttons/3_symb.png\">");
        out.println("</label>");
        out.println("</td>");
        out.println("<td>");
        out.println("<label>");
        out.println("<input class=\"btn\" type=\"radio\" name=\"game\" value=\"5\" />");
        out.println("<img src=\"img/buttons/5_symb.png\">");
        out.println("</label>");
        out.println("</td>");
        out.println("<td>");
        out.println("<label>");
        out.println("<input type=\"radio\" name=\"game\" value=\"7\" />");
        out.println("<img src=\"img/buttons/7_symb.png\">");
        out.println("</label>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan=\"3\">");
        out.println("<label>");
        out.println("<input type=\"submit\"/>");
        out.println("<img src=\"img/buttons/start.gif\">");
        out.println("</label>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</form>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    public void printMove(PrintWriter out, ArrayList<String> symbols, int game) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Rock, Paper, Scissors Extreme</title>");
        out.println("<style>");
        out.println("label> input { visibility: hidden; position: absolute;}");
        out.println("label> input + img{ cursor:pointer; border:2px solid transparent; height: 50px;}");
        out.println("label> input:not(:checked):hover + img{ border:5px solid transparent; }");
        out.println("label> input:checked + img{ height: 75px;}");
        out.println("td{ width: 140px; height: 100px; text-align: center; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table align = \"center\">");
        out.println("<thead><th colspan=\""+game+"\"><h1>Pick your weapon!</h1></th></thead>");
        out.println("<tbody>");
        out.println("<form>");
        out.println("<tr>");

        for(String symbol:symbols) {
            out.println("<td>");
            out.println("<label>");
            out.println("<input type=\"radio\" name=\"symbol\" value =\""+symbol+"\">");
            out.println("<img src = \"img/symbols/"+symbol+".gif\">");
            out.println("<p style=\"clear:both;\">"+symbol.substring(0,1).toUpperCase()+symbol.substring(1,symbol.length())+"</p>");
            out.println("</label>");
            out.println("</td>");
        }

        out.println("</tr>");
        out.println("<tr>");
        out.println("<td colspan=\""+game+"\">");
        out.println("<label>");
        out.println("<input type = \"submit\" />");
        out.println("<img src = \"img/buttons/start.gif\">");
        out.println("</label>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</form>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
    }

    public void printExplanation(PrintWriter out, int game){

        if(game==3){
            out.println("<p>");
            out.println("<font color=\"00bb00\"><b>ROCK</b></font> CRUSHES <font color=\"brown\"><b>SCISSORS</b></font>.<br>");
            out.println("<font color=\"purple\"><b>PAPER</b></font> COVERS <font color=\"00bb00\"><b>ROCK</b></font>.<br>");
            out.println("<font color=\"brown\"><b>SCISSORS</b></font> CUT <font color=\"purple\"><b>PAPER</b></font>.<br>");
            out.println("</p>");
        }else if(game==5){
            out.println("<p>");
            out.println("<font color=\"00bb00\"><b>ROCK</b></font> POUNDS OUT<font color=\"dddd00\"><b> FIRE</b></font>&amp; CRUSHES <font color=\"brown\"><b>SCISSORS</b></font>.<br>");
            out.println("<font color=\"purple\"><b>PAPER</b></font> COVERS <font color=\"00bb00\"><b>ROCK</b></font>, FLOATS ON <font color=\"0000bb\"><b>WATER</b></font>.<br>");
            out.println("<font color=\"brown\"><b>SCISSORS</b></font> CUT <font color=\"purple\"><b>PAPER</b></font> &amp; <font color=\"ff5555\"><b>SPONGE</b></font>.<br>");
            out.println("<font color=\"dddd00\"><b>FIRE</b></font> MELTS <font color=\"brown\"><b>SCISSORS</b></font>&amp; BURNS <font color=\"purple\"><b>PAPER</b></font>.<br>");
            out.println("<font color=\"0000bb\"><b>WATER</b></font> ERODES <font color=\"00bb00\"><b>ROCK</b></font>&amp; PUTS OUT<font color=\"dddd00\"><b> FIRE</b></font>.");
            out.println("</p>");
        }else if(game==7){
            out.println("<p>");
            out.println("<font color=\"00bb00\"><b>ROCK</b></font> POUNDS OUT<font color=\"dddd00\"><b>FIRE</b></font>, CRUSHES <font color=\"brown\"><b>SCISSORS</b></font> &amp;<font color=\"ff5555\"><b>SPONGE</b></font>.<br>");
            out.println("<font color=\"purple\"><b>PAPER</b></font> FANS <font color=\"5555ff\"><b>AIR</b></font>, COVERS <font color=\"00bb00\"><b>ROCK</b></font>, FLOATS ON <font color=\"0000bb\"><b>WATER</b></font>.<br>");
            out.println("<font color=\"brown\"><b>SCISSORS</b></font> SWISH THROUGH <font color=\"5555ff\"><b>AIR</b></font>, CUT <font color=\"purple\"><b>PAPER</b></font> &amp; <font color=\"ff5555\"><b>SPONGE</b></font>.<br>");
            out.println("<font color=\"dddd00\"><b>FIRE</b></font> MELTS <font color=\"brown\"><b>SCISSORS</b></font>, BURNS <font color=\"purple\"><b>PAPER</b></font> &amp; <font color=\"ff5555\"><b>SPONGE</b></font>.<br>");
            out.println("<font color=\"0000bb\"><b>WATER</b></font> ERODES <font color=\"00bb00\"><b>ROCK</b></font>, PUTS OUT<font color=\"dddd00\"><b>FIRE</b></font>, RUSTS <font color=\"brown\"><b>SCISSORS</b></font>.<br>");
            out.println("<font color=\"ff5555\"><b>SPONGE</b></font> SOAKS <font color=\"purple\"><b>PAPER</b></font>, USES <font color=\"5555ff\"><b>AIR</b></font> POCKETS, ABSORBS <font color=\"0000bb\"><b>WATER</b></font>.<br>");
            out.println("<font color=\"5555ff\"><b>AIR</b></font> BLOWS OUT <font color=\"dddd00\"><b>FIRE</b></font>, ERODES <font color=\"00bb00\"><b>ROCK</b></font>, EVAPORATES <font color=\"0000bb\"><b>WATER</b></font>.<br>");
            out.println("</p>");
        }
    }

    public void printResult(PrintWriter out, int userScore, int compScore) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Rock, Paper, Scissors Extreme</title>");
        out.println("<style>");
        out.println("label> input { visibility: hidden; position: absolute;}");
        out.println("label> input + img{ cursor:pointer; border:2px solid transparent; height: 50px;}");
        out.println("label> input:not(:checked):hover + img{ border:5px solid transparent; }");
        out.println("label> input:checked + img{ height: 75px;}");
        out.println("#gif{ width: 700px;}");
        out.println("td{ width: 140px; height: 100px; text-align: center;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table align=\"center\">");
        out.println("<thead><th><h1>Fight</h1></th></thead>");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<img id=\"gif\" src=\"Servlet?img=\"yes\"\"/>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<p>Score:</p><p>Player: "+userScore+" Computer: "+compScore+"</p>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<form method=\"POST\">");
        out.println("<input type=\"checkbox\" name=\"reset\">Reset?<br>");
        out.println("<label>");
        out.println("<input type=\"submit\"/>");
        out.println("<img src=\"img/buttons/start.gif\">");
        out.println("</label>");
        out.println("</form>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }


    private int randInt(int min, int max) {
            Random r = new Random();
            min++;
            int result = r.nextInt(max - min) + min;
            return result;

    }

    private int getOutcome(String user, String comp) {

        if (user.equals(comp))
            return 0;

        switch (user) {
            case "rock":
                if("scissors".equals(comp) || "fire".equals(comp) || "sponge".equals(comp))
                    return (1);
                else
                    return (-1);

            case "paper":
                if("air".equals(comp) || "water".equals(comp) || "rock".equals(comp) )
                    return (1);
                else
                    return (-1);

            case "scissors":
                if("sponge".equals(comp) || "paper".equals(comp) || "air".equals(comp) )
                    return (1);
                else
                    return (-1);

            case "water":
                if("rock".equals(comp) || "fire".equals(comp) || "scissors".equals(comp))
                    return (1);
                else
                    return (-1);

            case "air":
                if("water".equals(comp) || "fire".equals(comp) || "rock".equals(comp))
                    return (1);
                else
                    return (-1);

            case "sponge":
                if("paper".equals(comp) || "air".equals(comp) || "water".equals(comp))
                    return (1);
                else
                    return (-1);

            case "fire":
                if("sponge".equals(comp) || "paper".equals(comp) || "scissors".equals(comp))
                    return (1);
                else
                    return (-1);

            default:
                return 0;
        }
    }

    public Gif89Encoder getGif(String user, String comp, int outcome){

        int time = 0;
        int x = 0, velX = 2;
        int yPosition = 0;
        boolean goingDown = true;

        BufferedImage img = null;
        BufferedImage img2 = null;
        BufferedImage img3 = null;





        try {
            Gif89Encoder genc = new Gif89Encoder();
            img = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/symbols/"+user + ".gif"));


            img2 = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/symbols/"+comp + ".gif"));

            img3 = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/buttons/expl.gif"));




            BufferedImage image = new BufferedImage(1000,280, BufferedImage.TYPE_INT_ARGB);



            while(time<200){
                if(time<38){
                    Graphics2D g = image.createGraphics();
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, 1000, 300);
                    g.drawImage(img,x-100,yPosition+40,null);
                    g.drawImage(img2,1000-x,yPosition+40,null);
                    genc.addFrame(image);
                    g.dispose();


                }else if(time > 45 && time < 55){
                    Graphics2D g = image.createGraphics();
                    g.drawImage(img3,320,15,null);
                    genc.addFrame(image);
                    g.dispose();
                }else if(time > 55 && time < 70){
                    Graphics2D g = image.createGraphics();
                    g.fillRect(0, 0, 1000, 280);
                    genc.addFrame(image);
                    g.dispose();
                }else if(time > 70 && time < 200){
                    Graphics2D g = image.createGraphics();
                    g.fillRect(0, 0, 1000, 280);
                    BufferedImage outcomeImg;

                    if(outcome == 1)
                        outcomeImg = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/buttons/win.gif"));
                        //outcomeImg = ImageIO.read(getClass().getResource("img/buttons/win.gif"));
                    else if(outcome == 0)
                        outcomeImg = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/buttons/draw.gif"));
                        //outcomeImg = ImageIO.read(getClass().getResource("img/buttons/draw.gif"));
                    else if(outcome == -1)
                        outcomeImg = ImageIO.read(new File("/home/antero/SD1/GroupCoursework/rps/web/img/buttons/lose.gif"));
                        //outcomeImg = ImageIO.read(getClass().getResource("img/buttons/lose.gif"));
                    else
                        throw new Exception("ERROR in outcome");

                    g.drawImage(outcomeImg,380,yPosition-20,null);
                    genc.addFrame(image);
                    g.dispose();
                }

                x = x + 7*velX;
                time++;




                //up and down movement
                if (goingDown == true){
                    if (yPosition <= 0){
                        goingDown = false;
                        yPosition= yPosition+4;
                    }
                    else {
                        yPosition= yPosition-4;;
                    }
                }
                else {
                    // if we're going up and we reach 0, go down again
                    if (yPosition >= 50){
                        goingDown = true;
                        yPosition= yPosition-4;;
                    }
                    else {
                        yPosition= yPosition+4;;
                    }
                }
            }



            genc.setUniformDelay(5);
            genc.setLoopCount(0);
            //FileOutputStream out = new FileOutputStream("/usr/share/tomcat7/bin/anim.gif");
            //genc.encode(out);
            return genc;
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }



}
