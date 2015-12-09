import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends HttpServlet {
    RPS rps = new RPS();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(rps.game==0 && request.getParameter("game")==null) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            printIndex(out);
        }else{
            if(request.getParameter("game")!=null)
                rps.game = Integer.parseInt(request.getParameter("game"));

            String[] symbols = new String[rps.game];
            System.arraycopy(rps.general, 0, symbols, 0, rps.game);
            PrintWriter out = response.getWriter();
            printMove(out, symbols, rps.game);


        }
    }

    public void printIndex(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Rock, Paper, Scissors Extreme</title>");
        out.println("<style>");
        out.println("label> input { visibility: hidden; position: absolute;}");
        out.println("label> input + img{ cursor:pointer; border:2px solid transparent; height: 50px;}");
        out.println("label> input:hover + img{ border:5px solid transparent; }");
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

    public void printMove(PrintWriter out, String[] symbols, int game) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Rock, Paper, Scissors Extreme</title>");
        out.println("<style>");
        out.println("label> input { visibility: hidden; position: absolute;}");
        out.println("label> input + img{ cursor:pointer; border:2px solid transparent; height: 50px;}");
        out.println("label> input:hover + img{ border:5px solid transparent; }");
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
}
