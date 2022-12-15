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

.warning {
    display: block;
    padding: 5px;
    margin-bottom: 1.33ex;
    border-width: 1pt;
    border-style: solid;
    border-color: gray;
    padding: 4px;
    border-radius: 4px;
    background-color: red;
}

warning:before {
    display: inline;
    content: 'Warning: ';
    font-size: medium;
    font-weight: bold;
    color: #404040;}

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
<title>Uploaded</title>
</head>
<body>

	<%
		/** if the parameters are already in place, grab them **/
		String destFileName = (String) request.getAttribute("destFileName");
		String servername = (String) request.getAttribute("servername");
		String pluginpath = (String) request.getAttribute("pluginpath");
		String location = (String) request.getAttribute("location");
		String root = (String) request.getAttribute("root");
		String message = (String) request.getAttribute("message");
		String warning = (String) request.getAttribute("warning");
		String port = (String) request.getAttribute("port");
		String url = (String) request.getAttribute("url");		
	%>

    <form><input Type="button" VALUE="Back" onClick="history.go(-1);return true;"></form>	
	<form method="post" action="Controller" encType="multipart/form-data">
		<input type="hidden" name="action" value="home" /> <input
			type="submit" value="Home" />
	</form>
	<hr />

	<h3>
		Project:
		<%
		out.print(servername);
	%>
	</h3>

	<p class='note'>Package uploaded.</p>

	<%
	if (!(message == null)) {

			out.println("<p class='note'>" + message + "<p>");

		}
	%>

	<%
	if (!(warning == null)) {

			out.println("<p class='warning'>" + warning + "<p>");
			
		}
	%>

</body>
</html>