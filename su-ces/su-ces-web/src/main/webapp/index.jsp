<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
<jsp:directive.page import="java.security.Principal"/>
	<jsp:directive.page contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8" session="false"/>
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<h2>Hello World!</h2>
<form method="POST" action="logout.jsp">
	<p>Authenticated successfully!</p><br/>
	<p>
<jsp:scriptlet>
Principal userPrincipal = request.getUserPrincipal();
if(userPrincipal != null) {
</jsp:scriptlet>
	user:<jsp:expression>userPrincipal.getName()</jsp:expression><br/>
	isAdmin:<jsp:expression>request.isUserInRole("admin")</jsp:expression><br/>
	isStudent:<jsp:expression>request.isUserInRole("student")</jsp:expression><br/>
<jsp:scriptlet>
}
</jsp:scriptlet>
	</p>	
	<input type="submit" value="Logout" />
</form>
</body>
</html>
</jsp:root>