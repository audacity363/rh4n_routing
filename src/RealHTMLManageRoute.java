import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.routing.*;

@WebServlet("/config/routes")
public class RealHTMLManageRoute extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
	
	public void init() {
		System.out.println("addroutes init");
	}
       
    public RealHTMLManageRoute() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvironmentBuffer envs = (EnvironmentBuffer)EnvironmentBuffer.getEnvironmentsfromContext(getServletContext());
		
		String envname = request.getParameter("_envname");
		String method = request.getParameter("_method");
		String routetemplate = request.getParameter("routelink");
		String routeid = request.getParameter("_routeid");
		String login_s = request.getParameter("login");
		Boolean login;
		if(login_s == null) { login = false; }
		else { login = true; }
		
		Route route = new Route(request.getParameter("library"), request.getParameter("program"), 
				login, request.getParameter("loglevel"));
		
		try {
			if(method.equals("put")) {
				envs.addRoutetoEnv(envname, routetemplate, route);
				getServletContext().setAttribute("message", "Successfully added route " + routetemplate);
			} else if(method.equals("post")) {
				Route targetroute = envs.getEnvironment(envname).routing.getRouteById(Integer.parseInt(routeid));
				targetroute.natLibrary = route.natLibrary;
				targetroute.natProgram = route.natProgram;
				targetroute.login = route.login;
				targetroute.loglevel = route.loglevel;
				getServletContext().setAttribute("message", "Successfully edited route");
			} else if(method.equals("delete")) {
				envs.getEnvironment(envname).routing.deleteRoute(Integer.parseInt(routeid));
				getServletContext().setAttribute("message", "Successfully deleted route");
			}
		} catch(EnviromentException e) {
			throw(new ServletException(e));
		}
		EnvironmentBuffer.setEnvironmentsforContext(getServletContext(), envs);
		
		response.sendRedirect("environment.jsp?name=" + envname);
	}
}