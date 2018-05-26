import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.servlet.exceptions.XMLException;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.xml.Import;

public class RealHTMLInit extends HttpServlet {

	private static final long serialVersionUID = -6815603038617744076L;
	private final String version = "2.0";
	public String configurationfile;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.print("Starting realHTML4Natural Tomcat Connector");
		System.out.println(" Version: " + this.version);
		
		this.configurationfile = System.getenv("RH4NCONF");
		if(this.configurationfile == null) {
			throw(new ServletException("Enviroment variable RH4NCONF is missing"));
		}
		
		String logpath = System.getenv("RH4NLOG");
		if(logpath == null) {
			throw(new ServletException("Enviroment variable RH4NLOG is missing"));
		}
		
		Import xmlimport = new Import();
		EnvironmentBuffer envs;
		
		try {
			envs = xmlimport.importFromFile(this.configurationfile);
		} catch(FileNotFoundException e) {
			System.out.println("Warning: File " + configurationfile + " does not exist.");
			System.out.println("Warning: There will be no environments definied.");
			envs = new EnvironmentBuffer();
		} catch (ParserConfigurationException | SAXException | IOException | XMLException | EnviromentException e) {
			throw(new ServletException(e));
		}
		
		EnvironmentBuffer.setEnvironmentsforContext(getServletContext(), envs);
		
		System.out.println("Successfully initialised realHTML4Natural Tomcat Connector");
	}
}
