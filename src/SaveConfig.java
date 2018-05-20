import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.EnvironmentBuffer;

@WebServlet("/config/save")
public class SaveConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SaveConfig() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		EnvironmentBuffer envs = (EnvironmentBuffer)context.getAttribute("environments");
		if(envs == null) {
			throw(new ServletException(new EnviromentException("No enviroment defined")));
		}
		
		try {
			envs.saveToFile("/tmp/rh4nconfig.xml");
		} catch(Exception e) {
			throw(new ServletException(e));
		}
	}
}
