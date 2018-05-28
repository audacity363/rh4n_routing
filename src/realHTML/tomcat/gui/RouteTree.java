package realHTML.tomcat.gui;

import java.util.ArrayList;

public class RouteTree {
	public ArrayList<RouteTree> childs;
	public String path;
	public int id;
	
	public RouteTree() {
		this.childs = new ArrayList<RouteTree>();
		this.id = -1;
	}
	
	public void addChild(String path) {
		RouteTree tmp = new RouteTree();
		tmp.path = path;
		tmp.id = -1;
		
		this.childs.add(tmp);
	}
	
	public void addChild(String path, int id) {
		RouteTree tmp = new RouteTree();
		tmp.path = path;
		tmp.id = id;
		
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
