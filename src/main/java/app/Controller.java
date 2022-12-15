package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String root = null;
	String startscript = null;
	String stopscript = null;
	String cyclescript = null;
	String tempdir = null;
	String pluginpath = null;
	String serverlocation = null;
	ArrayList<Server> servers = new ArrayList<Server>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {

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

			// first get the global settings

			nList = doc.getElementsByTagName("global");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					root = eElement.getElementsByTagName("root").item(0)
							.getTextContent();
					startscript = eElement.getElementsByTagName("start")
							.item(0).getTextContent();
					stopscript = eElement.getElementsByTagName("stop").item(0)
							.getTextContent();
					cyclescript = eElement.getElementsByTagName("cycle")
							.item(0).getTextContent();
					tempdir = eElement.getElementsByTagName("tempdir").item(0)
							.getTextContent();
					pluginpath = eElement.getElementsByTagName("pluginpath")
							.item(0).getTextContent();
					serverlocation = eElement.getElementsByTagName("serverlocation")
							.item(0).getTextContent();

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
							+ eElement.getElementsByTagName("location").item(0)
									.getTextContent());
					System.out.println("Port : "
							+ eElement.getElementsByTagName("port").item(0)
									.getTextContent());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = "/index.jsp";
		String url = request.getParameter("url");
		PrintWriter out = response.getWriter();

		out.println(root);
		out.println(pluginpath);
		
		String message = request.getParameter("message");
		String pluginpath = request.getParameter("pluginpath");
		String root = request.getParameter("root");
		String servers = request.getParameter("servers");	
		String action = request.getParameter("action");	
		String location = request.getParameter("location");
		
		System.out.println("get location:" + location );
		System.out.println("get message:" + message );
		System.out.println("get pluginpath:" + pluginpath );
		System.out.println("get root:" + root );
		System.out.println("get servers:" + servers );
		System.out.println("get action:" + action );
		
		if (action.toString().equals("control")){
			page = "/controls.jsp";
		}
		
		request.setAttribute("local", location);
		request.setAttribute("message", message);
		request.setAttribute("pluginpath", pluginpath);
		request.setAttribute("root", root);
		request.setAttribute("servers", servers);
		request.setAttribute("action", action);
		request.getRequestDispatcher(page).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unused")
		PrintWriter out = response.getWriter();

		String local = null;
		String url = null;
		String servername = null;
		String action = null;

		String page = "/index.jsp";
		@SuppressWarnings("unused")
		String message = null;
		String warning = null;
		String port = null;
		String filename = null;
		String foldername = root + local + pluginpath;
		String dir2Delete = null;

		FileFunctions filestuff = new FileFunctions();
		@SuppressWarnings("unused")
		File target = null;

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);

		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Process the uploaded items
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();

			if (item.isFormField()) {
				String name = item.getFieldName();
				String value = item.getString();

				if (name.equals("filename")) {
					filename = value;
				}

				if (name.equals("action")) {
					action = value;
				}

				if (name.equals("dir2Delete")) {
					dir2Delete = value;
				}

				if (name.equals("name")) {
					servername = value;
				}

				if (name.equals("servername")) {
					servername = value;
				}

				if (name.equals("local")) {
					local = value;
				}

				if (name.equals("location")) {
					local = value;
				}

				if (name.equals("port")) {
					port = value;
				}

				if (name.equals("message")) {
					message = value;
				}

				if (name.equals("url")) {
					url = value;
				}
				
				if (name.equals("warning")) {
					warning = value;
				}

			} else {
				String contentType = item.getContentType();

				File uploadDir = new File(tempdir);
				
				action = "control";
				
				if (filename.length() > 0)
				{
				
				File file = File.createTempFile(filename, ".zip", uploadDir);
				try {
					item.write(file);
					//create a test file to see if the plugin.xml is at the right level
					File plugin = new File(root + local + pluginpath + filename
							+ "/plugin.xml");
					filestuff.extractFolder(file.toString(), root + local
							+ pluginpath + filename);

					if(!plugin.exists()){
						warning = "The plugin.xml file is not in place. It should be at the top level of the .zip file. <br /> We'll try to tunnel down into your package and see if we can find it somewhere else.<br />";
						
						FileSearch filefind = new FileSearch();
						
						filefind.searchDirectory(new File(root + local + pluginpath + filename), "plugin.xml");
						
						int count = filefind.getResult().size();
						if(count ==0){
							
						    warning = warning + "<br/>Nope, no plugin.xml file at all. Delete the package and try again.Delete the package you just added, and check your .zip file. <br />If the .zip file contains a folder before it contains any content, <br />correct it so that the content is at the very top level of the file.";
						    
						} else {	
							
						    for (String matched : filefind.getResult()){							
							int lastslash = matched.lastIndexOf("/");
							String pluginLocation = matched.substring(0, lastslash);
							warning = warning + "<br /> Found the plugin at " + pluginLocation + ". Now we'll move it to the top level.<br />";						
							
							File batchFile = new File("/infocenter/bin/./move_files.sh");
							
							ProcessBuilder processBuilder = null;
							try {
								processBuilder = new ProcessBuilder(batchFile.getCanonicalPath(), pluginLocation + "/*", root + local + pluginpath + filename );
								
								warning = warning + "<br /> Ok, it should be moved now. Everything's fine. Carry on.<br />";		
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    // Redirect any output (including error) to a file. This avoids deadlocks
						    // when the buffers get full. 
						    processBuilder.redirectErrorStream(true);
						    
						    // Set the working directory. The batch file will run as if you are in this
						    // directory.
						   						    
						    processBuilder.directory(new File("/infocenter/bin"));

						    // Start the process and wait for it to finish. 
						    Process process = null;
							try {
								process = processBuilder.start();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    int exitStatus = 0;
							try {
								exitStatus = process.waitFor();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						    System.out.println("Processed finished with status: " + exitStatus);								
							
						    }
						}
						
					}
					
					
					//Start new block to fix Infocenter issues related to plugin.properties files and META-INF/MANIFEST.MF files.
					
					FileSearch filefind = new FileSearch();
					
					filefind.searchDirectory(new File(root + local + pluginpath + filename), "plugin.xml");
					
					for (String matched : filefind.getResult()){							
						int lastslash = matched.lastIndexOf("/");
						String pluginLocation = matched.substring(0, lastslash);
						
						File fixInfocenter = new File("/infocenter/bin/infocenterfix/batchfiles/./batch.sh");
						
						ProcessBuilder processBuilder = null;
						try {
							processBuilder = new ProcessBuilder(fixInfocenter.getCanonicalPath(), root + local + pluginpath + filename );
															
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    // Redirect any output (including error) to a file. This avoids deadlocks
					    // when the buffers get full. 
					    processBuilder.redirectErrorStream(true);
					    
					    // Set the working directory. The batch file will run as if you are in this
					    // directory.
					   						    
					    processBuilder.directory(new File("/infocenter/bin/infocenterfix/batchfiles"));

					    // Start the process and wait for it to finish. 
					    Process process = null;
						try {
							process = processBuilder.start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    int exitStatus = 0;
						try {
							exitStatus = process.waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    System.out.println("Infocenter Fix process finished with status: " + exitStatus);								
						
					    }
					
					//End new block
					
					
					message = "Restart the Infocenter to see your new package.";		
					
					// filestuff.deleteDIR(file);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out
							.println("Something went wrong copying the zip file");
					message = "Something went wrong copying the zip file. Sorry I can't be more help.";

				}
				}
				else {
					warning = "Please enter a <b>Folder Name</b> for the folder containing the new package. The name must not contain any spaces or <br />special characters  and must not be the same as any of the existing folder names in the infocenter.";
					action = "control";
				}

			}

		}

		if (action.equals("chooseSystem")) {
			page = "/index.jsp";
		}

		if (action.equals("control")) {
			page = "/controls.jsp";
		}

		if (action.equals("deleteDIR")) {
			filestuff.deleteDIR(root + local + pluginpath + dir2Delete);
			message = "Don't forget to restart the infocenter after removing one or more packages. <br /><br />"
					+ "The items will still appear in the TOC and in search results but the topics <br />will no longer be resolve-able.";
			page = "/controls.jsp";

		}

		if (action.equals("restartInfocenter")) {
			
			filestuff.executeCMD(root + local + stopscript);
			
			filestuff.executeCMD(root + local + startscript); //The start script actually first shuts down the current infocenter, so stopping the infocenter first is unneeded.
			
			message = "Once the infocenter restarts, run a search to rebuild the new parts of the search indexes.<br /><br /> "
					+ "It will happen when the first person tries to use the infocenter anyway, and "
					+ "in larger infocenters it can take a while for the index to compile.<br /><br />" +
					" The " + servername + " infocenter is restarting. <br /><br /> In 30 seconds or so the link to "
					+ "<a target='_new' href='" + url +"'> " + url + "</a> should be live. If the infocenter still isn't live, drop "
					+ "<a href='mailto:jeff.hooker@microchip.com'>jeff.hooker@microchip.com</a> a line.";
			

			page = "/controls.jsp";

		}
		
		if (action.equals("restartInfocenterIndex")) {
			filestuff.executeCMD(root + local + cyclescript);

			message = "Once the infocenter restarts, run a search to rebuild the search indexes.<br /><br /> "
					+ "It will happen when the first person tries to use the infocenter anyway, and "
					+ "in larger infocenters it can take a while for the index to compile.<br /><br />" +
					" The " + servername + " infocenter is restarting. <br /><br /> In 30 seconds or so the link to "
					+ "<a target='_new' href='" + url +"'> " + url + "</a> should be live. If the infocenter still isn't live, drop "
					+ "<a href='mailto:jeff.hooker@microchip.com'>jeff.hooker@microchip.com</a> a line.";
			

			page = "/index.jsp";

		}
		
		if (action.equals("stopInfocenter")) {
			filestuff.executeCMD(root + local + stopscript);

			message = "Infocenter stopping. Refresh the screen to check status.<br /><br /> ";
			

			page = "/index.jsp";

		}
		
		if (action.equals("startInfocenter")) {
			filestuff.executeCMD(root + local + startscript);

			message = "Infocenter starting. Refresh the screen to check status.<br /><br /> ";
			

			page = "/index.jsp";

		}
		

		if (action.equals("addinfocenter")) {
			message = "Don't forget to delete the indexes after adding one or more packages.<br /><br /> "
					+ "The added packages will not appear in the TOC or search results until the <br />indexes have been reset.";

			page = "/controls.jsp";

		}

		if (action.equals("infocenteruploaded")) {
			message = "Don't forget to delete the indexes after adding one or more packages.<br /><br /> "
					+ "The added packages will not appear in the TOC or search results until the <br />indexes have been reset.";


			page = "/uploaded.jsp";
		}
		
		System.out.println("local:"  + local);
		System.out.println("url:"  + url);
		System.out.println("servername:"  + servername);
		System.out.println("action:"  + action);
		System.out.println("pluginpath:"  + pluginpath);
		System.out.println("root:"  + root);
		
		request.setAttribute("serverlocation", serverlocation);
		request.setAttribute("servername", servername);
		request.setAttribute("warning", warning);
		request.setAttribute("pluginpath", pluginpath);
		request.setAttribute("location", local);
		request.setAttribute("root", root);
		request.setAttribute("servers", servers);
		request.setAttribute("message", message);
		request.setAttribute("port", port);
		request.setAttribute("url", url);

		request.getRequestDispatcher(page).forward(request, response);

	}

}
