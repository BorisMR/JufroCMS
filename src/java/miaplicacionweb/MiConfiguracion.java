
package miaplicacionweb;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jufroweb.*;

public class MiConfiguracion extends JufroApplication {
    
    String relPATH = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMS\\web\\";
    
    public MiConfiguracion() throws SQLException, ClassNotFoundException {
        
        int layoutSelected;
        String relPath = this.relPATH;
        
        JufroCMSConnection conec = new JufroCMSConnection();
        Statement stm = conec.createStatement();
        stm.execute("SELECT SELSTYLE FROM CONFIG");
        ResultSet result = stm.getResultSet();
        result.next();
        layoutSelected = result.getInt(1);
        //0 indica que fue borrado el layout que estaba siendo usado, por esto se establece la carga de un default
        if( layoutSelected == 0){
            this.setLayout(relPath+"layouts\\layoutDefault.html");
        }else{  
            stm.execute("SELECT NAME FROM STYLES WHERE ID="+layoutSelected);
            ResultSet result2 = stm.getResultSet();
            result2.next();
            String layoutName = result2.getString("NAME");
            this.setLayout(relPath+"layouts\\"+layoutName);
        }
        this.addWidgetFromFile(relPath+"widget1.html");
        this.addWidgetFromFile(relPath+"widget2.html");
        this.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
        this.setHeader("Can&iacute;bales del Tiempo, RAD to your life");
        JufroMenu mimenu = new JufroMenu();
        
        mimenu.addMenuItemFromContent("Home",1);
        mimenu.addMenuItemFromContent("Servicios",2);
        mimenu.addMenuItemFromHtml("Productos",this.relPATH+"estatico.html");
        mimenu.addMenuItemFromJSP("Servicios","dinamico.jsp");
        mimenu.addMenuItemFromJSP("Layouts", "lista.jsp");
        this.setMenu(mimenu);        
    }
    
    public String getRelPath(){
        String retValue = this.relPATH;
        return retValue;
    }
}