package realHTML.tomcat.gui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspWriter;

import realHTML.tomcat.routing.PathEntry;
import realHTML.tomcat.routing.PathType;

public class RouteTree {
	public ArrayList<RouteTree> childs;
	public String path;
	public int id;
	public PathEntry route;
	
	public RouteTree() {
		this.childs = new ArrayList<RouteTree>();
		this.id = -1;
		this.path = "/";
	}
	
	public void addChild(String path) {
		RouteTree tmp = new RouteTree();
		tmp.path = path;
		tmp.id = -1;
		
		this.childs.add(tmp);
	}
	
	public void addChild(String path, int id, PathEntry route) {
		RouteTree tmp = new RouteTree();
		tmp.path = path;
		tmp.id = id;
		tmp.route = route;
		
		this.childs.add(tmp);
	}
	
	public Boolean checkPath(String path) {
		for(int i = 0; i < this.childs.size(); i++) {
			if(this.childs.get(i).path.equals(path)) {
				return(true);
			}
		}
		return(false);
	}
	
	public RouteTree getChildPath(String path) {
		for(int i = 0; i < this.childs.size(); i++) {
			if(this.childs.get(i).path.equals(path)) {
				return(this.childs.get(i));
			}
		}
		return(null);
	}
	
	public void printHTML(JspWriter out, String environmentname, int level) throws IOException {
		if(level == 0) { out.print("<ul class=\"tree\">"); }
		else { out.print("<ul>"); }
		if(this.id != -1) { out.print("<a href=\"route.jsp?id=" + this.id +"&name=" + environmentname + "\">"); }
		else { out.print("<a>"); }
		if(this.route != null) {
			if(this.route.getType() == PathType.VARIABLE) { out.print(":"); }
			if(this.route.getType() == PathType.SELECTION) { out.print("|"); }
		}
		out.print(this.path);
		out.print("</a>");
		for(int i = 0; i < this.childs.size(); i++) {
			out.print("<li>");
			this.childs.get(i).printHTML(out, environmentname, level+1);
			out.print("</li>");
		}
		out.print("</ul>");
	}
	
	public void print(int level) {
		this.printtabs(level);
		System.out.println("Name: " + this.path);
		if(this.id != -1) {
			this.printtabs(level);
			System.out.println("ID: " + this.id);
		}
		for(int i = 0; i < this.childs.size(); i++) {
			this.childs.get(i).print(level+1);
		}
	}
	
	private void printtabs(int level) {
		for(int i = 0; i < level; i++) { System.out.print("\t"); }
	}
}
