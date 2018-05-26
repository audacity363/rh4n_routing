

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.xml.Import;

@WebServlet("/config/import")
public class LoadConfig extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
       
    public LoadConfig() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Import xmlimport = new Import();
		
		try {
			EnvironmentBuffer envs = xmlimport.importFromFile(this.configurationfile);
			EnvironmentBuffer.setEnvironmentsforContext(getServletContext(), envs);
		} catch (Exception e) {
			throw(new ServletException(e));
		}
		
		getServletContext().setAttribute("message", "Successfully loaded configuration from " + this.configurationfile);
		response.sendRedirect("index.jsp");
	}

}
