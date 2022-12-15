<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="http://bby1xdocs02:8080${pageContext.request.contextPath}/jquery/jquery-ui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="http://bby1xdocs02:8080${pageContext.request.contextPath}/jquery/jquery.dataTables.min.css" />

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
    color: #404040;}

.up{color: green}

.down{color: red}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Select Your Project</title>
</head>
<body>


	<%@ page
		import="java.io.File,
java.io.IOException,
java.io.PrintWriter,
java.util.ArrayList,
java.util.Iterator,
java.util.List,
javax.servlet.ServletConfig,
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
org.apache.commons.fileupload.FileItem,
org.apache.commons.fileupload.FileItemFactory,
org.apache.commons.fileupload.FileUploadException,
org.apache.commons.fileupload.disk.DiskFileItemFactory,
org.apache.commons.fileupload.servlet.ServletFileUpload,
org.w3c.dom.Document,
org.w3c.dom.Element,
org.w3c.dom.Node,
org.w3c.dom.NodeList,
org.xml.sax.SAXException,
app.Server,
app.PortOpen"%>

	<%
		String root = null;
		String startscript = null;
		String stopscript = null;
		String cyclescript = null;
		String tempdir = null;
		String pluginpath = null;
		String serverlocation = null;
		ArrayList<Server> servers = new ArrayList<Server>();

		try {

			File fXmlFile = new File("/infocenter/conf/conf2.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList;

			//first get the global settings

			nList = doc.getElementsByTagName("global");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					root = eElement.getElementsByTagName("root").item(0)
							.getTextContent();
					startscript = eElement.getElementsByTagName("start")
							.item(0).getTextContent();
					stopscript = eElement.getElementsByTagName("stop")
							.item(0).getTextContent();
					cyclescript = eElement.getElementsByTagName("cycle")
							.item(0).getTextContent();
					tempdir = eElement.getElementsByTagName("tempdir")
							.item(0).getTextContent();
					pluginpath = eElement
							.getElementsByTagName("pluginpath").item(0)
							.getTextContent();
					serverlocation = eElement
							.getElementsByTagName("serverlocation").item(0)
							.getTextContent();

				}
			}

			// then read the servers into an arraylist

			nList = doc.getElementsByTagName("server");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					Server tempServer = new Server(eElement
							.getElementsByTagName("name").item(0)
							.getTextContent(), eElement
							.getElementsByTagName("location").item(0)
							.getTextContent(), eElement
							.getElementsByTagName("port").item(0)
							.getTextContent(), eElement
							.getElementsByTagName("url").item(0)
							.getTextContent());

					servers.add(tempServer);

					System.out.println("Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					System.out.println("Location : "
							+ eElement.getElementsByTagName("location")
									.item(0).getTextContent());
					System.out.println("Port : "
							+ eElement.getElementsByTagName("port").item(0)
									.getTextContent());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	%>


<div id="tabs">
			<ul>
				<li id="li-1"><a href="#tabs-1">Infocenters</a></li>
				<li id="li-2"><a href="#tabs-2">Manage</a></li>
			</ul>			
			<div id="tabs-1">
			<p></p>
			<table id="viewInfocenters" class="display">
					<thead text-align="left" align="left">
						<tr text-align="left" align="left">							
							<th text-align="left" align="left">Infocenter</th>
							<th text-align="left" align="left">Status</th>													
						</tr>
					</thead>
					<tbody>


	<%
	    PortOpen po = new PortOpen(); 
	
		for (Server server : servers) {

			out.print("<tr><td>");
			out.print("<span class='label'><a target='_blank' href='" + server.getUrl() + "'> "
					+ server.getName() + " </span></a>");		

			out.print("</td><td>");
			if (po.isUp(Integer.parseInt(server.getPort()), "localhost") == false){
				
				%>
				
				 <span class="down">[DOWN]</span>
				
				<%
				
			} else {
				
                 %>
				
				<span class="up">[UP]</span>
				
				<%
			}
			out.print("</td></tr>");	
		}
	%>
	</tbody>
	</table>
	</div>
	
	<div id="tabs-2">
			<p></p>
			<table id="manageInfocenters" class="display">
					<thead text-align="left" align="left">
						<tr text-align="left" align="left">
							<th text-align="left" align="left">Select</th>
							<th text-align="left" align="left">Infocenter</th>
							<th text-align="left" align="left">Status</th>	
							<th text-align="left" align="left">Stop</th>	
							<th text-align="left" align="left">Start</th>		
							<th text-align="left" align="left">Delete Indexes</th>							
						</tr>
					</thead>
					<tbody>


	<%
	  for (Server server : servers) {

			out.print("<tr><td>");
			
			out.print("<form method='post' action='Controller' encType='multipart/form-data'><input type='hidden' name='action' value='control'/>");
			
			out.print("<input type='hidden' name='name' value='"
					+ server.getName() + "'/>");
			out.print("<input type='hidden' name='url' value='"
					+ server.getUrl() + "'/>");
			out.print("<input type='hidden' name='local' value='"
					+ server.getLocation() + "'/>");
			out.print("<input type='hidden' name='port' value='"
					+ server.getPort() + "'/>");
			out.print("<input type='submit' value='Select'/></form>");
			
			out.print("</td><td>");
			
			out.print("<span class='label'><a target='_blank' href='" + server.getUrl() + "'> "
					+ server.getName() + " </span></a>");		

			out.print("</td><td>");
			
			if (po.isUp(Integer.parseInt(server.getPort()), "localhost") == false){
				
				%>
				
				 <span class="down">[DOWN]</span>
				
				<%
				
			} else {
				
                 %>
				
				<span class="up">[UP]</span>
				
				<%
			}
			
			
           out.print("</td><td>");				
             
			
			out.print("<form method='post' action='Controller' encType='multipart/form-data'><input type='hidden' name='action' value='control'/>");
			out.print("<input type='hidden' name='action' value='stopInfocenter'/>");
			out.print("<input type='hidden' name='name' value='"
					+ server.getName() + "'/>");
			out.print("<input type='hidden' name='url' value='"
					+ server.getUrl() + "'/>");
			out.print("<input type='hidden' name='local' value='"
					+ server.getLocation() + "'/>");
			out.print("<input type='hidden' name='port' value='"
					+ server.getPort() + "'/>");
			out.print("<input type='submit' value='Stop'/></form>");
			
			
			 out.print("</td><td>");				
             
				
				out.print("<form method='post' action='Controller' encType='multipart/form-data'><input type='hidden' name='action' value='control'/>");
				out.print("<input type='hidden' name='action' value='startInfocenter'/>");
				out.print("<input type='hidden' name='name' value='"
						+ server.getName() + "'/>");
				out.print("<input type='hidden' name='url' value='"
						+ server.getUrl() + "'/>");
				out.print("<input type='hidden' name='local' value='"
						+ server.getLocation() + "'/>");
				out.print("<input type='hidden' name='port' value='"
						+ server.getPort() + "'/>");
				out.print("<input type='submit' value='Start'/></form>");
			
			
			
			
			out.print("</td><td>");				
             
			
			out.print("<form method='post' action='Controller' encType='multipart/form-data'><input type='hidden' name='action' value='control'/>");
			out.print("<input type='hidden' name='action' value='restartInfocenterIndex'/>");
			out.print("<input type='hidden' name='name' value='"
					+ server.getName() + "'/>");
			out.print("<input type='hidden' name='url' value='"
					+ server.getUrl() + "'/>");
			out.print("<input type='hidden' name='local' value='"
					+ server.getLocation() + "'/>");
			out.print("<input type='hidden' name='port' value='"
					+ server.getPort() + "'/>");
			out.print("<input type='submit' value='Delete Indexes'/></form>");
			out.print("</td></tr>");		
			
		}
	%>
	</tbody>
	</table>
	</div>
	
</div>
	
	<script type="text/javascript">
    function foo() {
    	location.reload();
    	window.stop();
        return true;
    }
</script>

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