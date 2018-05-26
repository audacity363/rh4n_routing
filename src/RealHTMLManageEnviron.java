

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realHTML.tomcat.environment.EnvironmentBuffer;

@WebServlet("/config/environ")
public class RealHTMLManageEnviron extends RealHTMLInit {
	private static final long serialVersionUID = 1L;
       
    public RealHTMLManageEnviron() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvironmentBuffer envs = EnvironmentBuffer.getEnvironmentsfromContext(getServletContext());
		
		String envname = request.getParameter("_envname");
		String method = request.getParameter("_method");
		String varname = request.getParameter("varname");
		String content = request.getParameter("content");
		String append_s = request.getParameter("append");
		
		boolean append = false;
		if(append_s != null && append_s.equals("on")) {
			append = true;
		}
		
		try {
			if(method.equals("put")) {
				envs.getEnvironment(envname).addEnvironmentVar(varname, content, append);
				getServletContext().setAttribute("message", "Successfully added environment variable " +  varname);
			} else if(method.equals("delete")) {
				envs.getEnvironment(envname).deleteEnvironmentVar(varname);
				getServletContext().setAttribute("message", "Successfully deleted environment variable " +  varname);
			}
		} catch(Exception e) {
			throw(new ServletException(e));
		}
		
		EnvironmentBuffer.setEnvironmentsforContext(getServletContext(), envs);
		response.sendRedirect("environment.jsp?name=" + envname);
	}
}
