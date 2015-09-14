/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jufroweb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JufroCMSConnection {
        private Connection c;
        
    public JufroCMSConnection ()  {

    }
    
    public void init()throws ClassNotFoundException,SQLException {
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        this.c = DriverManager.getConnection("jdbc:derby://localhost:1527/webcontainer;user=admin;password=admin");
        this.c.setSchema("ADMIN");
    }
    
    public Statement createStatement() throws ClassNotFoundException,SQLException {
        this.init();
        return this.c.createStatement();
    }
}