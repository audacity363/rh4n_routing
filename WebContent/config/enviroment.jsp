<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.ServletContext" %>    
<%@ page import="realHTML.tomcat.routing.*" %>
<%@ page import="realHTML.tomcat.environment.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	ServletContext context = getServletContext();
	EnvironmentBuffer environments = (EnvironmentBuffer)context.getAttribute("environments");
	String envname = request.getParameter("name");
	
	Environment environment = environments.getEnviroment(envname);
	String[] routes = environment.routing.getRoutesTemplates();
	EnvironmentVar[] environs = environment.getEnvirons();
%>
<h1>Environment: <% out.print(envname); %></h1>
<label for="library">Natural Parms:</label><span id="library"><% out.print(environment.natparms); %></span><br>
<label for="program">Natural RSC PATH:</label><span id="program"><% out.print(environment.natsourcepath); %></span><br>
<h2>Routes</h2>
<%
	out.print("<ul>");
	for(int i = 0; i < routes.length; i++) {
		out.print("<li><a href='route.jsp?id=" + i + "&name=" + envname + "'>" + routes[i] + "</a></li>");
	}
	out.print("</ul>");
%>
	<form method="post" action="routes/addroute">
		<label for="routelink">Route Template:</label>
		<input type="text" name="routelink">
		<br>
		<label for="routelink">Natural Library:</label>
		<input type="text" name="library">
		<br>
		<label for="routelink">Natural Program:</label>
		<input type="text" name="program">
		<br>
		<input value=<%= envname %> name="envname" style="display: none">
		<button type="submit">Add Route</button>
	</form>
<hr>
<h2>Environment Variablen</h2>
<%
	out.print("<ul>");
	for(int i = 0; i < environs.length; i++) {
		out.print("<li>" + environs[i].name + "</li>");
	}
	out.print("</ul>");
%>
<form method="post" action="environ">
		<label for="varname">Variablename:</label>
		<input type="text" name="varname">
		<br>
		<label for="content">Content:</label>
		<input type="text" name="content">
		<br>
		<label for="append">Append?:</label>
		<input type="checkbox" name="append">
		<br>
		<input value=<%= envname %> name="envname" style="display: none">
		<button type="submit">Add Environ</button>
	</form>
<hr>
</body>
</html>