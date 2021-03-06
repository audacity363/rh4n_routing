import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.environment.Environment;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.routing.PathTemplate;

@WebServlet("/nat/*")
public class MainServlet extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
       
    public MainServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		EnvironmentBuffer envs = (EnvironmentBuffer)context.getAttribute("environments");
		if(envs == null) {
			throw(new ServletException(new EnviromentException("No enviroment defined")));
		}
		
		String path = request.getRequestURI().substring((request.getContextPath() + "/nat").length());
		int index = path.indexOf("/", 1);
        if(index == -1) {
            throw(new ServletException("No Route was given"));
        }

        String enviroment = path.substring(1, index);
        path = path.replace("/" + enviroment, "");
        
        Environment env;
        try {
        	env = envs.getEnvironment(enviroment);
        } catch (Exception e) {
        	throw(new ServletException(e));
        }
        
        PathTemplate route = env.routing.getRoute(path);
        if(route == null) {
        	throw(new ServletException("Unkown route"));
        }
        System.out.println(route);
        ServletOutputStream out = response.getOutputStream();
        out.print("<html><body><ul>");
        HashMap<String, String> parms = route.getParms();
        for(String key: parms.keySet() ) {
        	out.print("<li>[" + key + "] = [" + parms.get(key) + "]</li>");
        }
        out.print("</ul><br>");
        out.print("<span>Library: " + route.route.natLibrary + "</span><br>");
        out.print("<span>Program: " + route.route.natProgram + "</span><br>");
        out.print("<span>Loglevel: " + route.route.loglevel+ "</span><br>");
        out.print("<span>Login: " + route.route.login + "</span><br>");
        out.print("</body></html>");
	}

}
