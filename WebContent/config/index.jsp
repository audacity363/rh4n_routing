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
<h2>Environments</h2>
<%
	ServletContext context = getServletContext();
	EnvironmentBuffer environments = (EnvironmentBuffer)context.getAttribute("environments");
	if(environments == null) {
		out.print("No Enviroments defined");
	} else {
		String[] envnames =  environments.getEnviromentNames();
%>
	<ul>
	<%
		for(int i = 0; i < envnames.length; i++) {
			out.print("<li><a href='enviroment.jsp?name=" + envnames[i] +"'>" + envnames[i] + "</a></li>");
		}
	%>
	</ul>
<% } %>
	<hr>
	<form method="post" action="env">
		<label for="envname">Environment name:</label>
		<input type="text" name="envname">
		<br>
		<label for="natparm">Natural Parameter:</label>
		<input type="text" name="natparm">
		<br>
		<label for="natsrc">Natural SRC Path:</label>
		<input type="text" name="natsrc">
		<br>
		<button type="submit">Add Env</button>
	</form>
	
	<input type="button" onclick="location.href='save'" value="Save config to file">
</body>
</html>