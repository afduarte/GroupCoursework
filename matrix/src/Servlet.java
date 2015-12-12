import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class Servlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String word = request.getParameter("word");
        String set;
        Integer originalSetSize;
        Integer columns;
        Integer rows;
        Integer limit;
        Integer interval;
        String error = "";
        Color fgColor = new Color(Integer.parseInt("00FF00",16));
        Color bgColor;
        Boolean variableJam = false;
        Boolean variableSize = false;
        Boolean dispIterations = false;
        Boolean dispIntro = false;
        Boolean dispOutro = false;
        Boolean dispResults = false;
        request.setAttribute("error", error);

        if (null != word) {
            if (word.isEmpty()) {
                error = "You must set the word to look for.";
            }
            set = request.getParameter("set");
            limit = Integer.parseInt(request.getParameter("limit"));
            interval = Integer.parseInt(request.getParameter("interval"));

            if (null == request.getParameter("cols")) {
                columns = 10;
            } else {
                columns = Integer.parseInt(request.getParameter("cols"));
            }

            if (null == request.getParameter("rows")) {
                rows = 10;
            } else {
                rows = Integer.parseInt(request.getParameter("rows"));
            }

            if (0 >= columns) {
                error = "There must be more than 0 columns.";
            }

            if (word.length() > columns){
                error = "The number of columns must be at least as high as the length of the word.";
            }

            if (0 >= rows) {
                error = "There must be more than 0 rows.";
            }

            if (null == set) {
                set = "abcdefghijklmnopqrstuvwxyz";
            }
            originalSetSize = set.length();

            if(null == request.getParameter("bgColor"))
            {
                bgColor = Color.black;

            } else {

                try
                {
                    bgColor = new Color(Integer.parseInt(request.getParameter("bgColor"),16));
                }
                catch(java.lang.NumberFormatException e)
                {
                    bgColor = Color.black;
                }
            }

            if (Boolean.valueOf(request.getParameter("cheat"))) {
                for(Integer i = 0; i < word.length()*5; i++)
                    set += word;
            }

            if (Boolean.valueOf(request.getParameter("iters"))) {
                dispIterations = true;
            }

            if (Boolean.valueOf(request.getParameter("intro"))) {
                dispIntro = true;
            }
            if (Boolean.valueOf(request.getParameter("outro"))) {
                dispOutro = true;
            }
            if (Boolean.valueOf(request.getParameter("results"))) {
                dispResults = true;
            }

            if( "0".equals(request.getParameter("radios"))){
                variableSize = false;
                variableJam = false;
            }

            if( "1".equals(request.getParameter("radios"))){
                variableSize = true;
                variableJam = false;
            }

            if( "2".equals(request.getParameter("radios"))){
                variableSize = false;
                variableJam = true;
            }

            if (! error.equals("")) {
                request.getRequestDispatcher("/form.jsp").forward(request, response);
            } else {
                Matrix matrix = new Matrix(set, rows, columns, fgColor, bgColor,variableSize,
                        variableJam,dispIterations,dispIntro,dispOutro,dispResults, originalSetSize);
                response.setContentType("image/gif");
                matrix.findWord(word, limit, interval).encode(response.getOutputStream());
            }
        } else {
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        }
    }
}
