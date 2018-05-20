package realHTML.tomcat.environment;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.tomcat.routing.PathTemplate;
import realHTML.tomcat.routing.Route;
import realHTML.tomcat.routing.Routing;

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
	
	public void addRoutetoEnv(String enviroment, String template, Route route) throws EnviromentException {
		if(!this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " not found"));
		}
		
		this.enviroments.get(enviroment).routing.addRoute(template, route);
	}
	
	public Environment getEnviroment(String enviroment) throws EnviromentException {
		if(!this.enviroments.containsKey(enviroment)) {
			throw(new EnviromentException("Enviroment " + enviroment + " not found"));
		}
		
		return(this.enviroments.get(enviroment));
	}
	
	public String[] getEnviromentNames() {
		return this.enviroments.keySet().toArray(new String[this.enviroments.size()]);
	}
	
	public void saveToFile(String path) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("realHTML4Natural");
		doc.appendChild(rootElement);
		
		String[] envkeys = this.getEnviromentNames();
		for(int i = 0; i < envkeys.length; i++) {
			rootElement.appendChild(this.envtoString(doc, envkeys[i]));
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		
		transformer.transform(source, result);
	}
	
	private Element envtoString(Document doc, String envname) {
		Element envElement, natsrcElement, natparmElement; 
		Environment env = this.enviroments.get(envname);
		
		envElement = doc.createElement("environment");
		envElement.setAttribute("name", envname);
		
		natsrcElement = doc.createElement("natsrc");
		natsrcElement.setTextContent(env.natsourcepath);
		
		natparmElement = doc.createElement("natparms");
		natparmElement.setTextContent(env.natparms);
		
		envElement.appendChild(natsrcElement);
		envElement.appendChild(natparmElement);
		
		envElement.appendChild(this.routestoString(doc, env.routing));
		envElement.appendChild(this.environtoString(doc, env.environvars));
		
		return(envElement);
	}	
	
	private Element environtoString(Document doc, ArrayList<EnvironmentVar> environs) {
		Element environsElement, environElement, nameElement, contentElement, appendElement;
		EnvironmentVar envvar;
		
		environsElement = doc.createElement("environs");
		
		for(int i = 0; i < environs.size(); i++) {
			envvar = environs.get(i);
			environElement = doc.createElement("environ");
			
			nameElement = doc.createElement("name");
			nameElement.setTextContent(envvar.name);
			
			contentElement = doc.createElement("content");
			contentElement.setTextContent(envvar.value);
			
			appendElement = doc.createElement("append");
			if(envvar.append) {
				appendElement.setTextContent("true");
			} else {
				appendElement.setTextContent("false");
			}
			
			environElement.appendChild(nameElement);
			environElement.appendChild(contentElement);
			environElement.appendChild(appendElement);
			
			environsElement.appendChild(environElement);
		}
		
		return(environsElement);
	}
	
	private Element routestoString(Document doc, Routing routing) {
		Element routesElement, routeElement, natlibrary, natprogram;
		
		PathTemplate[] routes = routing.getRoutes();
		
		routesElement = doc.createElement("routes");
		
		for(int i = 0; i < routes.length; i++) {
			routeElement = doc.createElement("route");
			routeElement.setAttribute("path", routes[i].template);
			
			natlibrary = doc.createElement("natLibrary");
			natlibrary.setTextContent(routes[i].route.natLibrary);
			
			natprogram = doc.createElement("natProgram");
			natprogram.setTextContent(routes[i].route.natProgram);
			
			routeElement.appendChild(natlibrary);
			routeElement.appendChild(natprogram);
			
			routesElement.appendChild(routeElement);
		}
		
		return(routesElement);
	}
}
