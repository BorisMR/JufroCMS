<%@page import="miaplicacionweb.MiConfiguracion"%>
<%@page import="java.sql.Statement"%>
<%@page import="jufroweb.JufroCMSConnection"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*" %>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.servlet.*" %>
<%@page import="java.util.List" %>
<%
    //Directorios
    String filePathLayout = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMS\\web\\layouts\\";
    String filePathImages = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMS\\web\\images\\";
    String fileAuxLay = null;
    String fileAuxImg = null;
    String fileName = null;
    String fileExtension = null;

    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    JufroCMSConnection conec = new JufroCMSConnection();
    Statement stmnt = conec.createStatement();

    //Captura los archivos desde el request
    List<FileItem> fileItems = upload.parseRequest(request);
    for(FileItem item : fileItems) {
        if(!item.isFormField()) {
            fileName = item.getName();
            int index = fileName.lastIndexOf('.');
            fileExtension = (fileName.substring(index + 1)).toLowerCase();

            if( fileExtension.equals("html") ){
                fileAuxLay = filePathLayout + item.getName();
                File fileH = new File(filePathLayout + item.getName());
                item.write(fileH);
            }else if ( fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("bmp") ){
                fileAuxImg = filePathImages + item.getName();
                File fileI = new File(filePathImages + item.getName());
                item.write(fileI);
            }

        } else {
            System.out.println("NO file!");
        }
    }
    stmnt.execute("INSERT INTO STYLES ( NAME, LAYOUTPATH, IMAGEPATH) VALUES ( '"+fileName+"','"+fileAuxLay+"','"+fileAuxImg+"')");

    MiConfiguracion miweb = new MiConfiguracion();
    miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
    miweb.setContent("<b>Archivo Subido</b>",request,session);
    miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");

    out.print(miweb.getWebPage());
%>
