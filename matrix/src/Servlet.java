import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String word = request.getParameter("word");
        String set;
        Integer columns;
        Integer rows;
        Integer limit;
        String error = "";
        request.setAttribute("error", error);

        if (null != word) {
            if (word.isEmpty()) {
                error = "You must set the word to look for.";
            }
            set = request.getParameter("set");
            limit = Integer.parseInt(request.getParameter("limit"));

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

            if (0 >= rows) {
                error = "There must be more than 0 rows.";
            }

            if (null == set) {
                set = "abcdefghijklmnopqrstxyzw";
            }

            if (! error.equals("")) {
                request.getRequestDispatcher("/form.jsp").forward(request, response);
            } else {
                Matrix matrix = new Matrix(set, rows, columns);
                response.setContentType("image/gif");
                matrix.findWord(word, limit).encode(response.getOutputStream());
            }
        } else {
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        }

    }
}
