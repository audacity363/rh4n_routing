import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.xml.Export;

@WebServlet("/config/save")
public class SaveConfig extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
       
    public SaveConfig() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvironmentBuffer envs = EnvironmentBuffer.getEnvironmentsfromContext(getServletContext());
		if(envs == null) {
			throw(new ServletException(new EnviromentException("No enviroment defined")));
		}
		
		try {
			Export export = new Export(envs);
			export.exportToFile(this.configurationfile);
		} catch(Exception e) {
			throw(new ServletException(e));
		}
		
		getServletContext().setAttribute("message", "Successfully saved configuration to " + this.configurationfile);
		if(request.getHeader("referer") != null) {
			response.sendRedirect(request.getHeader("referer"));
		} else {
			response.sendRedirect("index.jsp");
		}
	}
}
