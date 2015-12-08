import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class Servlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(request.getParameter("game") ==null || request.getParameter("difficulty") ==null)
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        else{
            int game = Integer.parseInt(request.getParameter("game"));
        }
    }
}
