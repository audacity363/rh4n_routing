

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.Environment;
import realHTML.tomcat.environment.EnvironmentBuffer;

@WebServlet("/config/environ")
public class AddEnviron extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddEnviron() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		EnvironmentBuffer envs = (EnvironmentBuffer)context.getAttribute("environments");
		if(envs == null) {
			throw(new ServletException(new EnviromentException("No enviroment defined")));
		}
		
		String envname = request.getParameter("envname");
		String varname = request.getParameter("varname");
		String content = request.getParameter("content");
		String append_s = request.getParameter("append");
		
		boolean append = false;
		if(append_s != null && append_s.equals("on")) {
			append = true;
		}
		
		Environment env;
		try {
			env = envs.getEnviroment(envname);
		} catch (EnviromentException e) {
			throw(new ServletException(e));
		}
		env.addEnviron(varname, content, append);
		
		response.sendRedirect("enviroment.jsp?name=" + envname);
	}

}
