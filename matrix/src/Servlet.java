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
        Integer columns;
        Integer rows;
        Integer limit;
        Integer interval;
        String error = "";
        Color fgColor;
        Color bgColor;
        Boolean variableIntensity = false;
        Boolean variableSize = false;
        request.setAttribute("error", error);
        SafeColor checkColor = new SafeColor();

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

            if(null == request.getParameter("fgColor"))
            {
                fgColor = new Color(Integer.parseInt(request.getParameter("00FF00"),16));

            } else {

                try
                {
                    fgColor = new Color(Integer.parseInt(request.getParameter("fgColor"),16));
                }
                catch(java.lang.NumberFormatException e)
                {
                    fgColor = new Color(Integer.parseInt(request.getParameter("00FF00"),16));
                }
            }

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
                set = word + word + set + word + word;
            }

            if(Boolean.valueOf(request.getParameter("varsize"))){
                variableSize = true;
            }

            if(Boolean.valueOf(request.getParameter("varint"))){
                variableIntensity = true;
            }




            if (! error.equals("")) {
                request.getRequestDispatcher("/form.jsp").forward(request, response);
            } else {
                Matrix matrix = new Matrix(set, rows, columns, fgColor, bgColor,variableSize,
                        variableIntensity && checkColor.isSafe(request.getParameter("fgColor"))
                        && checkColor.isSafe(request.getParameter("bgColor")));
                response.setContentType("image/gif");
                matrix.findWord(word, limit, interval).encode(response.getOutputStream());
            }
        } else {
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        }
    }
}
