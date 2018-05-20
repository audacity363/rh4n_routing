import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.routing.*;

import javax.servlet.ServletContext;

@WebServlet("/config/routes/addroute")
public class AddRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() {
		System.out.println("addroutes init");
	}
       
    public AddRoute() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		EnvironmentBuffer envs = (EnvironmentBuffer)context.getAttribute("environments");
		if(envs == null) {
			throw(new ServletException(new EnviromentException("No enviroment defined")));
		}
		
		Route route = new Route(request.getParameter("library"), request.getParameter("program"));
		String envname = request.getParameter("envname");
		String routetemplate = request.getParameter("routelink");
		
		try {
			envs.addRoutetoEnv(envname, routetemplate, route);
		} catch(EnviromentException e) {
			throw(new ServletException(e));
		}
		context.setAttribute("environments", envs);
		
		response.sendRedirect("../enviroment.jsp?name=" + envname);
	}
}