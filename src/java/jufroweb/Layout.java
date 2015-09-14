
package jufroweb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Layout {
    private StringBuffer html= new StringBuffer();
    private String error="no error";
    
    public Layout (String pathAndFile) {
        this.loadlayout(pathAndFile);
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
}
