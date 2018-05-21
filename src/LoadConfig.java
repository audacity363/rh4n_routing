

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.xml.Import;

@WebServlet("/config/import")
public class LoadConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoadConfig() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Import xmlimport = new Import();
		ServletContext context = getServletContext();
		
		try {
			EnvironmentBuffer envs = xmlimport.importFromFile("/tmp/rh4nconfig.xml");
			context.setAttribute("environments", envs);
		} catch (Exception e) {
			throw(new ServletException(e));
		}
		response.sendRedirect("index.jsp");
	}

}
