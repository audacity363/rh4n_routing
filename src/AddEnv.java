

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.EnvironmentBuffer;

@WebServlet("/config/env")
public class AddEnv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddEnv() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		EnvironmentBuffer envs = (EnvironmentBuffer)context.getAttribute("environments");
		if(envs == null) {
			envs = new EnvironmentBuffer();
		}
		
		String envname = request.getParameter("envname");
		String natparm = request.getParameter("natparm");
		String natsrc = request.getParameter("natsrc");
		
		try {
			envs.addEnviroment(envname, natparm, natsrc);
		} catch (EnviromentException e) {
			throw(new ServletException(e));
		}
		
		context.setAttribute("environments", envs);
		response.sendRedirect("index.jsp");
	}

}
