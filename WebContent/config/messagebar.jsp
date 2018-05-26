<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String message = (String)application.getAttribute("message"); 
if(message != null) {
%>
<div style="width: 100%; text-align: center">
	<p><% out.print(message); %></p>
</div>
<%
application.removeAttribute("message");
}	
%>
<input type="button" onclick="location.href='save'" value="Save config to file">
<input type="button" onclick="location.href='import'" value="Load config from file">
<br>
<input type="button" onclick="location.href='index.jsp'" value="Go to home">