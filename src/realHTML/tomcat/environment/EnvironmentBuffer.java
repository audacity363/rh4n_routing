package realHTML.tomcat.environment;

import java.util.HashMap;

import javax.servlet.ServletContext;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.routing.Route;

public class EnvironmentBuffer {

	HashMap<String, Environment> enviroments;
	
	public EnvironmentBuffer() {
		this.enviroments = new HashMap<String, Environment>();
	}
	
	public void addEnviroment(String enviroment, String natparms, String natsourcepath) throws EnviromentException {
		if(this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " already exists"));
		}
		
		Environment env = new Environment(natparms, natsourcepath);
		this.enviroments.put(enviroment, env);
	}
	
	public void deleteEnvironment(String name) {
		this.enviroments.remove(name);
	}
	
	public void addRoutetoEnv(String enviroment, String template, Route route) throws EnviromentException {
		if(!this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " not found"));
		}
		
		this.enviroments.get(enviroment).routing.addRoute(template, route);
	}
	
	public Environment getEnvironment(String enviroment) throws EnviromentException {
		if(!this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " not found"));
		}
		
		return(this.enviroments.get(enviroment));
	}
	
	public String[] getEnviromentNames() {
		return this.enviroments.keySet().toArray(new String[this.enviroments.size()]);
	}
	
	public void addEnvirontoEnv(String enviroment, String name, String content, boolean append) throws EnviromentException {
		if(!this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " not found"));
		}
		
		this.enviroments.get(enviroment).addEnvironmentVar(name, content, append);
	}
	
	public static EnvironmentBuffer getEnvironmentsfromContext(ServletContext context) {
		return (EnvironmentBuffer)context.getAttribute("environments");
	}
	
	public static void setEnvironmentsforContext(ServletContext context, EnvironmentBuffer envs) {
		context.setAttribute("environments", envs);
	}
}
