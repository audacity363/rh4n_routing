import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.Environment;
import realHTML.tomcat.environment.EnvironmentBuffer;

public class RealHTMLManageEnvironment extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
       
    public RealHTMLManageEnvironment() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvironmentBuffer envs = (EnvironmentBuffer)EnvironmentBuffer.getEnvironmentsfromContext(getServletContext());
		
		String envname = request.getParameter("envname");
		String natparm = request.getParameter("natparm");
		String natsrc = request.getParameter("natsrc");
		String method = request.getParameter("_method");
		
		try {
			if(method.equals("put")) {
				envs.addEnviroment(envname, natparm, natsrc);
				getServletContext().setAttribute("message", "Successfully added environment " + envname);
			} else if(method.equals("post")) {
				Environment env = envs.getEnvironment(envname);
				env.natparms = natparm;
				env.natsourcepath = natsrc;
				getServletContext().setAttribute("message", "Successfully edited environment " + envname);
			} else if(method.equals("delete")) {
				envs.deleteEnvironment(envname);
				getServletContext().setAttribute("message", "Successfully deleted environment " + envname);
			}
		} catch (EnviromentException e) {
			throw(new ServletException(e));
		}
		
		EnvironmentBuffer.setEnvironmentsforContext(getServletContext(), envs);
		getServletContext().setAttribute("settingssaved", false);
		if(method.equals("post")) {
			response.sendRedirect("environment.jsp?name=" + envname);
		} else {
			response.sendRedirect("index.jsp");
		}
	}
}
