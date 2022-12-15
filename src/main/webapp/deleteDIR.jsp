<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body {
	font-family: arial;
	font-size: 10pt;
	padding: 5px;
	margin: 50px;
	border-width: 1pt;
	border-style: solid;
	border-color: gray;
	padding: 20px;
	border-radius: 4px;
	background-color: #F8F8E0;
}

.note {
	display: block;
	padding: 5px;
	margin-bottom: 1.33ex;
	border-width: 1pt;
	border-style: solid;
	border-color: gray;
	padding: 4px;
	border-radius: 4px;
	background-color: #C2D6FF;
}

note:before {
	display: inline;
	content: 'Note: ';
	font-size: medium;
	font-weight: bold;
	color: #404040;
}

fieldset {width 500px
	
}

legend {
	font-size: 10pt;
}

label.field {
	text .align: right;
	width: 100px;
	float: left;
	font-weight: bold;
	padding: 10px;
}

input.textbox-300 {
	width: 300px;
	float: left
}

fieldset p {
	clear: both;
	padding: 5px
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Plugins</title>
</head>
<body>
<form><input Type="button" VALUE="Back" onClick="history.go(-1);return true;"></form>	
	<form method="post" action="Controller" encType="multipart/form-data">
		<input type="hidden" name="action" value="home" /> <input
			type="submit" value="Home" />
	</form>
	<hr />

	<%
		/** if the parameters are already in place, grab them **/
		String destFileName = request.getParameter("destFileName");
		String servername = (String) request.getAttribute("servername");
		String pluginpath = (String) request.getAttribute("pluginpath");
		String location = (String) request.getAttribute("location");
		String root = (String) request.getAttribute("root");
		String port = (String) request.getAttribute("port");
		String url = (String) request.getAttribute("url");		
		String message = (String) request.getAttribute("message");
	%>

	<h3>
		Project:
		<%
		out.print(servername);
	%>
	</h3>

	<%
	if (!(message == null)) {

			out.println("<p class='note'>" + message + "<p>");

		}
	%>


<%@ page
import="java.util.ArrayList,
java.io.IOException,
java.io.PrintWriter,
java.io.File,
java.io.IOException,
java.net.MalformedURLException,
java.net.URL,
javax.servlet.ServletException,
javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,
javax.servlet.http.HttpServletResponse,
javax.xml.parsers.DocumentBuilder,
javax.xml.parsers.DocumentBuilderFactory,
javax.xml.parsers.ParserConfigurationException,
javax.xml.xpath.XPath,
javax.xml.xpath.XPathConstants,
javax.xml.xpath.XPathExpressionException,
javax.xml.xpath.XPathFactory,
org.w3c.dom.Document,
org.w3c.dom.Element,
org.w3c.dom.Node,
org.w3c.dom.NodeList,
org.xml.sax.SAXException,
app.FileFunctions,
app.GenerateUUID"%>

	<%
		FileFunctions dirList = new FileFunctions();

	/** out.println("<p>root:" + root + "</p>");
	    out.println("<p>root:" + location + "</p>");
	    out.println("<p>root:" + pluginpath + "</p>");
	 **/
		ArrayList<String> dirlisting = dirList.listDIR(root + location + pluginpath);

		for (int x = 0; x < dirlisting.size(); x++) {
			out.println("<p><form method='post' action='Controller' encType='multipart/form-data'>");
			out.println("<input type='hidden' name='action' value='deleteDIR'/>");
			out.println("<input type='hidden' name='location' value='"
					+ location + "'/>");			
			out.println("<input type='hidden' name='port' value='"
					+ port + "'/>");			
			out.println("<input type='hidden' name='url' value='"
					+ url + "'/>");			
			out.println("<input type='hidden' name='servername' value='"
					+ servername + "'/>");
			out.println("<input type='hidden' name='dir2Delete' value='"
					+ dirlisting.get(x).toString() + "'/>");
			out.println("<input type='submit' value='Delete'/>");
			out.println(dirlisting.get(x).toString());
			out.println("</form></p>");
		}
	%>

</body>
</html>