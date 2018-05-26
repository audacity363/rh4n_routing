package realHTML.tomcat.routing;

public class Route {

	public String natLibrary;
	public String natProgram;
	public Boolean login;
	public String loglevel;
	
	public Route(String library, String program, Boolean login, String loglevel) {
		this.natLibrary = library;
		this.natProgram = program;
		this.login = login;
		this.loglevel = loglevel;
	}
}
