package realHTML.tomcat.environment;

import java.util.ArrayList;

import realHTML.tomcat.routing.Routing;

public class Environment {
	public String natparms;
	public String natsourcepath;
	public ArrayList<EnvironmentVar> environvars;
	public Routing routing;
	
	public Environment(String natparms, String natsrc) {
		this.natparms = natparms;
		this.natsourcepath = natsrc;
		this.routing = new Routing();
		this.environvars = new ArrayList<EnvironmentVar>();
	}
	
	public void addEnviron(String name, String value, boolean append) {
		this.environvars.add(new EnvironmentVar(name, value, append));
	}
	
	public EnvironmentVar[] getEnvirons() {
		return(this.environvars.toArray(new EnvironmentVar[this.environvars.size()]));
	}
}
