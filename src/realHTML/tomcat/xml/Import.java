package realHTML.tomcat.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import realHTML.servlet.exceptions.EnviromentException;
import realHTML.servlet.exceptions.XMLException;
import realHTML.tomcat.environment.EnvironmentBuffer;
import realHTML.tomcat.routing.Route;

public class Import {
	public EnvironmentBuffer importFromFile(String path) throws ParserConfigurationException, SAXException, IOException, XMLException, EnviromentException {
		EnvironmentBuffer envs = new EnvironmentBuffer();
		
		DocumentBuilderFactory dbFactory; 
        DocumentBuilder dBuilder;
        Document doc;

        File inputFile = new File(path);

        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();

        doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        
        envs = this.readEnvironments(doc, envs);
		
		return(envs);
	}
	
	private EnvironmentBuffer readEnvironments(Document doc, EnvironmentBuffer envs) throws XMLException, EnviromentException {
		NodeList environmentsElements, tmpList;
		Element targetElement, tmpElement;
		
		String envname, natsrc = "", natparms = "";
		
		environmentsElements = doc.getElementsByTagName("environment");
		for(int i = 0; i < environmentsElements.getLength(); i++) {
			targetElement = (Element)environmentsElements.item(i);
			envname = targetElement.getAttribute("name");
			if(envname.length() == 0) {
				throw(new XMLException("environment name is empty or not existent. Environment No." + i));
			}
			
			tmpList = targetElement.getElementsByTagName("natsrc");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				natsrc = tmpElement.getTextContent();
			}
			
			tmpList = targetElement.getElementsByTagName("natparms");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				natparms = tmpElement.getTextContent();
			}
			
			envs.addEnviroment(envname, natparms, natsrc);
			envs = this.readRoutes(targetElement, envname, envs);
			envs = this.readEnvirons(targetElement, envname, envs);
			envname = natsrc = natparms = "";
		}
		
		return(envs);
	}
	
	private EnvironmentBuffer readRoutes(Element env, String envname, EnvironmentBuffer envs) throws XMLException, EnviromentException {
		NodeList routesList, tmpList;
		Element targetElement, tmpElement;
		
		String template, natLibrary = "", natProgram = "";
		Route route;
		
		
		routesList = env.getElementsByTagName("route");
		for(int i = 0; i < routesList.getLength(); i++) {
			targetElement = (Element)routesList.item(i);
			template = targetElement.getAttribute("path");
			if(template.length() == 0) {
				throw(new XMLException("route in environment " + envname + " is empty"));
			}
			
			tmpList = targetElement.getElementsByTagName("natLibrary");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				natLibrary = tmpElement.getTextContent();
			}
			
			tmpList = targetElement.getElementsByTagName("natProgram");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				natProgram = tmpElement.getTextContent();
			}
			
			route = new Route(natLibrary, natProgram);
			envs.addRoutetoEnv(envname, template, route);
			
			template = natLibrary = natProgram = "";
		}
		
		return(envs);
	}
	
	private EnvironmentBuffer readEnvirons(Element env, String envname, EnvironmentBuffer envs) throws XMLException, EnviromentException {
		NodeList environsList, tmpList;
		Element targetElement, tmpElement;
		
		String name = "", content = "", append_s = "";
		boolean append = false;
		
		environsList = env.getElementsByTagName("environ");
		for(int i = 0; i < environsList.getLength(); i++) {
			targetElement = (Element)environsList.item(i);
			
			tmpList = targetElement.getElementsByTagName("name");
			if(tmpList.getLength() == 0) {
				throw(new XMLException("Environ name in environment " + envname + " is empty"));
			}
			tmpElement = (Element)tmpList.item(0);
			name = tmpElement.getTextContent();
			
			tmpList = targetElement.getElementsByTagName("content");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				content = tmpElement.getTextContent();
			}
			
			tmpList = targetElement.getElementsByTagName("append");
			if(tmpList.getLength() != 0) {
				tmpElement = (Element)tmpList.item(0);
				append_s = tmpElement.getTextContent();
				if(append_s.equals("true")) {
					append = true;
				} else {
					append = false;
				}
			}
			
			envs.addEnvirontoEnv(envname, name, content, append);
			
			name = content = append_s = "";
		}
		
		return(envs);
	}
}