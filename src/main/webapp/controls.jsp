<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style>

body {
	font-family: arial;
	font-size: 10pt;
	padding: 5px;
    margin: 50px;   
    padding: 20px;
    border-radius: 4px;
    background-color: #ffffff;
}


.note {
    display: block;
    padding: 30px;
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
    color: #404040;}

.warning {
    display: block;
    padding: 30px;
    margin-bottom: 1.33ex;
    border-width: 1pt;
    border-style: solid;
    border-color: gray;
    padding: 4px;
    border-radius: 4px;
    background-color: #ff9999;
}

warning:before {
    display: inline;
    content: 'Warning: ';
    font-size: medium;
    font-weight: bold;
    color: #404040;}
    
#tabs-2{padding: 30px;
padding-top: 0px}

.capsule {
padding: 20px;
padding-top: 0px;
border-color: #C2D6FF;
border-style: solid;
border-width: 1pt;
border-radius: 5px;
margin-top: 20px;}

</style>

	<%
		/** if the parameters are already in place, grab them **/
		String destFileName = request.getParameter("destFileName");	
		String servername = (String) request.getAttribute("servername");
		String serverlocation = (String) request.getAttribute("serverlocation");
		String pluginpath = (String) request.getAttribute("pluginpath");
		String location = (String) request.getAttribute("location");
		String root = (String) request.getAttribute("root");
		String url = (String) request.getAttribute("url");
		String message = (String) request.getAttribute("message");
		String port = (String) request.getAttribute("port");	
		String warning = (String) request.getAttribute("warning");
	%>

<head>

<link rel="stylesheet" type="text/css"
	href="http://bby1xdocs02:8080${pageContext.request.contextPath}/jquery/jquery-ui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="http://bby1xdocs02:8080${pageContext.request.contextPath}/jquery/jquery.dataTables.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project: <% out.print(servername); %></title>
</head>
<body>	
<form method="post" action="Controller" encType="multipart/form-data">
<input type="hidden" name="action" value="home"/>
<input type="submit" value="Home"/>
</form>
<p></p>

	
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

<div id="tabs">
			<ul>
				<li><a href="#tabs-1">Project: <% out.print(servername); %></a></li>
			</ul>			
			<div id="tabs-2">  
			

	<%
	if (!(message == null)) {

			out.println("<p class='note'>" + message + "<p>");
			
		}
	
	
	if (!(warning == null)) {

			out.println("<p class='warning'>" + warning + "<p>");
			
		}
	
	
		FileFunctions dirList = new FileFunctions();

	/** out.println("<p>root:" + root + "</p>");
	    out.println("<p>root:" + location + "</p>");
	    out.println("<p>root:" + pluginpath + "</p>");
	 **/
		ArrayList<String> dirlisting = dirList.listDIR(root + location + pluginpath);

	    out.println("<div class='capsule'><h5>Delete an existing package</h5>");
	 
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
		out.println("</div>");
	%>
	
<form method="post" action="Controller" encType="multipart/form-data">

<div class="capsule">
<h5>Add a new package</h5>

<input type="hidden" name="action" value="infocenteruploaded"/>



<input type="hidden" name="servername" value="<% out.print(servername); %>"/>



<input type="hidden" name="url" value="<% out.print(url); %>"/>



<input type="hidden" name="root" value="<% out.print(root); %>"/>



<input type="hidden" name="location" value="<% out.print(location); %>"/>


<input type="hidden" name="pluginpath" value="<% out.print(pluginpath); %>"/>
<p>
<field><input type="text" name="filename" size="20"/><span> Folder name (No spaces, no special characters, no repetition with existing folder names)</span></field>
</p>
<p>
<field><input type="file" name="zipname" value="Select .zip file"/><br/></field>
</p>
<p>
<field><input type="submit" value="Upload"/></field>
</p>
</div>
</form>



<form method="post" id="restartForm" action="Controller" encType="multipart/form-data">
<div class="capsule">
<h5>Restart and engage changes</h5>
<input type="hidden" name="action" value="restartInfocenter"/>
<input type="hidden" name="location" value="<%out.print(location);%>"/>
<input type="hidden" name="servername" value="<%out.print(servername);%>"/>
<input type="hidden" name="url" value="<%out.print(url);%>"/>
<input type="hidden" name="port" value="<%out.print(port);%>"/>
<input type="submit" value="Restart Infocenter"/>
</div></form>


</div>
</div>

 
<script src="<%out.print(serverlocation);%>${pageContext.request.contextPath}/jquery/jquery-ui/external/jquery/jquery.js"></script>
<script src="<%out.print(serverlocation);%>${pageContext.request.contextPath}/jquery/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" charset="utf8" src="<%out.print(serverlocation);%>${pageContext.request.contextPath}/jquery/jquery.dataTables.min.js"></script>
<script>
	$("#tabs").tabs();	
	
	$("#tabs-message").tabs();

	$(document).ready(function() {
		$('table.display').DataTable();
	});
	
	 function toggle_visibility(id) {
	       var e = document.getElementById(id);
	       if(e.style.display == 'block')
	          e.style.display = 'none';
	       else
	          e.style.display = 'block';
	 }

</script>


</body>
</html>