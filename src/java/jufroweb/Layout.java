
package jufroweb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import miaplicacionweb.MiConfiguracion;


public class Layout {
    private StringBuffer html= new StringBuffer();
    private String error="no error";
    
    public Layout (String pathAndFile) {
        this.loadlayout(pathAndFile);
    }
    public Layout () {
        
    }
    
    public boolean loadlayout(String pathFile) {
      String cadena="";
      this.html.delete(0, this.html.length());
      
      try {
      FileReader f = new FileReader(pathFile);
      BufferedReader b = new BufferedReader(f);

      while((cadena = b.readLine())!=null) {
          this.html.append("\n");          
          this.html.append(cadena);
      }
      b.close();
      }
      catch (FileNotFoundException e){
          this.error = e.getMessage();
          return false;
      }
      catch (IOException e){
          this.error = e.getMessage();
          return false;
      }
      return true;
    }
    
    public String error() {
        return this.error;
    }
    
    public String getWebPage() {
        this.getOutLayoutFlags();
        return this.html.toString();
    }
    
    public void getOutLayoutFlags() {
        int end;
        int ini = this.html.indexOf("***");
        while (ini>0) {
            end = this.html.indexOf("***", ini+1);
            this.html.delete(ini, end+3);
            ini = this.html.indexOf("***");
        }
    }
    
    public boolean setContent(String innerContent){
        return replaceString("***content***",innerContent);
    }
    
    public boolean setFooter(String theFooter){
        return replaceString("***footer***",theFooter);
    }
    
    public boolean setWidgets(String theWidgets) {
        return replaceString("***widget***",theWidgets);
    }
    
    public boolean setWindow(String theWindowName) {
        return replaceString("***window***",theWindowName);
    }
    
    public boolean setHeader(String theHeader) {
        return replaceString("***header***",theHeader);
    }
    
    public boolean setUser(String theUser) {
        return replaceString("***user***",theUser);
    }
    
    public boolean setContext(String theContext) {
        return replaceString("***context***",theContext);
    }
    
    public boolean setMenu(String theMenu) {
        return replaceString("***menu***",theMenu);
    }
    
    private boolean replaceString(String oldString,String newString){
        int pos = this.html.indexOf(oldString);
        int len = oldString.length();
        if (pos<0) return false;
        else this.html.replace(pos, pos+len,newString);
        return true;
    }
    
    public String generarHtmlLista() throws SQLException, ClassNotFoundException{
        String htmlInterno = "";
        String htmlListaContent = "<table border='1' align='center'>\n"
            + "<tr>\n"
                + "<td> ";
            
                htmlInterno += generarHtmlGestorBasico();
                htmlListaContent += htmlInterno;
            htmlListaContent+="</td>"
            +"<td>";
                htmlInterno = null;
                htmlInterno = generarHtmlGestorSubida();
                htmlListaContent+= htmlInterno;    
                htmlListaContent+="</td>"
            +"</tr>";
        htmlListaContent+="</table>";
        return htmlListaContent;
    }
    
    private String generarHtmlGestorBasico() throws SQLException, ClassNotFoundException{
        
        String htmlGB = "";
        JufroCMSConnection c = new JufroCMSConnection();
        Statement s = c.createStatement();
        s.execute("Select ID, NAME from STYLES");
        ResultSet r = s.getResultSet();
        if (r!=null){
            //htmlGB += generarScript(); //LINEA SCRIPT
            htmlGB += "<form method='POST' action='StyleChang'>\n"
                +"<img id='prevImg' align='middle' height='200' width='200' src='images/noImage.png?nocache=1'/>\n"
                +"<div id='divVistaPrevia'></div>"
                + "<select size=30 name='opsel' id='opsel' onChange='cambiarImagen(this.value)' >"
                    + "<optgroup label='Selecciona Un Layout'>\n";

            while (r.next()){
                htmlGB += "<option value='"+r.getInt("ID")+"'>"+r.getString("NAME")+"</option>\n";
            }

            htmlGB += "</select>\n"
                +"<input type='submit' id='cambiar' value='Cargar'>"
                +"<input type='submit' id='borrar' value='Eliminar' name='action'/>"
            +"</form>";
   
        }
        return htmlGB;
    }
    
    private String generarHtmlGestorSubida(){
        String htmlS = "<h2>Uploader Layouts</h2>"
            +"<form method='POST' action='Uploader' enctype='multipart/form-data'>"
                +"<label for='uploadFile'>Imagen previa para Layout: </label>\n"
                +"<input type='file' name='pic' accept='image/*'/>"
                +"<label for='uploadFile'>Layout para Subir: </label>"
                +"<input type='file'/ name='layout' required>"
                +"+<input type='submit' value='Subir Layout' />"
            +"</form>";
        return htmlS;
    }
    
    public void eliminarLayout(int idLayoutToDel, boolean inUse){
        //inUse modifica el selstyle a 0
        if( inUse == true ){
            
        }else{
            
        }
    }
    
    private String generarScript()throws SQLException, ClassNotFoundException{
        String script="";
        script = "<script type='text/javascript'>\n"
                +"function cambiarImagen(valID){"
                    + "console.log(valID);"
                + "}\n";
                
        //script += "</script>";
        /*
        JufroCMSConnection c = new JufroCMSConnection();
        Statement s = c.createStatement();
        s.execute("SELECT COUNT(*) FROM STYLES");
        ResultSet r = s.getResultSet();
        r.next();
        int ctdad = r.getInt(1);
        String arrString = "var matriz=new Array(2);"
            + "for (i = 0; i < 2; i++){"
                + "matriz[i]=new Array("+ctdad+")"
            + "}";
        
        s.execute("Select ID, IMAGEPATH from STYLES");
        r = s.getResultSet();
        int contador = 0;
        while(r.next()){
            arrString+= "matriz[0]["+contador+"]="+r.getString("ID")+";\n"
            + "matriz[1]["+contador+"]="+r.getString("IMAGEPATH")+";\n";
        }
        
        script = "<script type='text/javascript'>\n";
        script += arrString;
        String valSel = "var id= document.getElementById('opsel');"
                +"for(con = 0; con < "+ctdad+"; con++){"
                    +"console.log('matriz(0)(ctdad)'); "
                + "}"
                +"function cambiarImagen(valID){"
                    + "console.log(valID);"
                + "}\n";
                //+ "document.getElementById('divVistaPrevia').innerHTML='<img src=images+path+'/>';"
                //+ "document.getElementById('divVistaPrevia').innerHTML='<img src=images+path+'/>';"
                
          */  
        script += "</script>";
                
        return script;
    }
    
    public String getImagePath(int id) throws ClassNotFoundException, SQLException{
        JufroCMSConnection c = new JufroCMSConnection();
        Statement s = c.createStatement();
        s.execute("SELECT IMAGEPATH FROM STYLES WHERE ID="+id);
        ResultSet r = s.getResultSet();
        r.next();
        MiConfiguracion miHelp = new MiConfiguracion();
        
        String fix = r.getString("IMAGEPATH").replace(miHelp.getRelPath(), "./");
        fix = fix.replace("\\", "/");
        
        return fix;
    }
}
