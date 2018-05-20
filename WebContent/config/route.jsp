<%@page import="realHTML.tomcat.environment.EnvironmentBuffer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="realHTML.tomcat.routing.*" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="realHTML.tomcat.environment.*" %>
<%@ page import="javax.servlet.ServletContext" %>    
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
	Route route = environment.routing.getRouteById(Integer.parseInt(request.getParameter("id")));
	%>
	<label for="library">Natural Library:</label><span id="library"><% out.print(route.natLibrary); %></span><br>
	<label for="program">Natural Library:</label><span id="program"><% out.print(route.natProgram); %></span><br>
</body>
</html>