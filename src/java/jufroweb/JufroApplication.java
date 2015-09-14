
package jufroweb;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JufroApplication {
    private JufroMenu _menu=null;
    private Layout _lay=null;
    private String _footer="";
    private String _header="";
    private String _context="";
    private String _user="";
    private String _window="";
    private String _content="";
    private String error="no error";
    private LinkedList<String> _widget = new LinkedList();
    
    public JufroApplication() {
       
    }
    
    public void setMenu(JufroMenu theMenu){
        this._menu = theMenu;
    }
    
    public void setContent(String content,HttpServletRequest req,HttpSession ses) {
        
        this._content = content;
        String ct = req.getParameter("content");
        String html = req.getParameter("htmlFile");
        //String jsp = req.getParameter("jspFile");
        if (ct!=null) 
            this.setContentFromId(ct);
        else if (html!=null)
            this.setContentFromHtml(html,ses);
        //else if (jsp!=null)
        //    this.setContentFromJSP(jsp,ses,req,res);
        if (this._lay !=null) 
            this._lay.setContent(this._content);
    }
    
    private void setContentFromHtml(String file,HttpSession session){
        //String path = session.getServletContext().getContextPath();
        String web = this.readTextFile(file);
        if (web!=null) this._content = web;
        else this._content = "Problemas con archivo html "+file+" el error dice: "+this.error;
    }

    private void setContentFromId(String ctid){
            int id = Integer.parseInt(ctid);
            Content cbd = new Content();
            if (cbd.load(id)) this._content = cbd.getContent();
            else this._content ="contenido de id "+id+" no fue posible recuperar, error: "+cbd.error() ;
    }
    
    public void setWindowTitle(String window) {
        this._window = window;
        if (this._lay !=null) this._lay.setWindow(window);
    }
    
    public void setFooter(String footer){
        this._footer = footer;
        if (this._lay !=null) this._lay.setFooter(footer);
    }
    
    public void setHeader(String header){
        this._header = header;
        if (this._lay !=null) this._lay.setHeader(header);
    }
    
    public void setLayout(String pathAndFile) {
        this._lay = new Layout(pathAndFile);
        //this._lay.setWindow(this._window);
        //this._lay.setFooter(this._footer);
        //this._lay.setHeader(this._header);
        //this._lay.setContent(this._content);
    }
    
    public String getWebPage() {
        this._lay.setWidgets(this.getWidgets());
        this._lay.setMenu(this._menu.getHtml());
        return this._lay.getWebPage();
    }
    
    private String getWidgets() {
        Iterator it = this._widget.iterator();
        String ret="";
        while(it.hasNext()){
            ret+=it.next()+"<br/>";
        }
        return ret;
    }
    
    private String readTextFile(String pathFile){
      String cadena="";
      String ret="";
      try {
      FileReader f = new FileReader(pathFile);
      BufferedReader b = new BufferedReader(f);

      while((cadena = b.readLine())!=null) {
          ret+=cadena;
      }
      b.close();
      }
      catch (FileNotFoundException e){
          this.error = e.getMessage();
          return null;
      }
      catch (IOException e){
          this.error = e.getMessage();
          return null;
      }
      
      return ret;
        
    }
    
    public boolean addWidgetFromFile(String pathFile){
        String txt = this.readTextFile(pathFile);
        if (txt!=null) {
            this._widget.add(txt);
            return true;
        }
        else return false;
    }
    
} //class
