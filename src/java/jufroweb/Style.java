
package jufroweb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Style {

    
    private int idStyle=-1;
    /*
    private String name="";
    private String pathFile="";
    private String pathImg="";
    */
    private String content="";
    private String error="";
  
public void Style () {
    //this.load(id);
   
}
  
public String getContent() {
    return this.content;
}
  
public String generateHtml(){
    String ret = "<table border='1' align='center'>\n<tr>\n<td> ";
    String con = "";
    try {
        JufroCMSConnection c = new JufroCMSConnection();
        Statement s = c.createStatement();
        s.execute("Select ID, NAME from STYLES");
        ResultSet r = s.getResultSet();
        if (r!=null){
            con = "<form method='POST' action='Estilo'>";
            con += "<select size=30 name='opsel' id='opsel'><optgroup label='Selecciona Un Layout'>";
            while (r.next()){
                con += "<option value='"+r.getInt("ID")+"'>"+r.getString("NAME")+"</option>\n";
            }
            con += "</select>"
                +"<input type='submit' id='cambiar' value='Establecer Layout'>"
                +"<input type='submit' id='borrar' value='Borrar Layout' name='action'/>"
            +"</form>";
            ret += con;
        }
        ret+="</td>"
                +"<td>"
                    +"<img align='middle' height='200' width='200' src='images/noImage.png'/>"
                /*    
                +"<form method='GET' action='subidor.jsp'>"
                    
                    +"<p>Dirección imágen</p><input type='file' name='pic' accept='image/*' placeholder='Archivo Imagen'required/>"
                    +"<p>Dirección html</p><input type='file' accept='layout/*'required/>"
                    +"<input type='submit' value='Subir  Layout'/>"
                +"</form>"
        */
                    +"<h2>Uploader Layouts</h2>"
                    +"<form method='POST' action='upload_file.jsp' enctype='multipart/form-data'>"
                        +"<label for='uploadFile'>Selecciona imagen a Subir: </label>\n"
                        +"<input type='file' name='pic' accept='image/*' placeholder='Archivo Imagen'/>"
                        +"<label for='uploadFile'>Selecciona layout a Subir: </label>"
                        +"<input type='file'/ name='layout' required>"
                        +"+<input type='submit' value='Subir Layout' />"
                    +"</form>"
                +"</td>"
            +"</tr>"
        +"</table>";
    }catch (SQLException e){
        this.tonull(-2);
        this.error = e.getMessage();
    }
    
    catch (ClassNotFoundException e){
        this.tonull(-3);
    }
    return ret;
}
  
  
    public void tonull(int id){
        if (id>=0) id=id*-1;
        this.idStyle = id;
        this.content="";  
    }
      
    public int currentId(){
        return this.idStyle;
    }

    public String error() {
        return this.error;
    }
}