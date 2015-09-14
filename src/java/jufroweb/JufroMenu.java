package jufroweb;
import java.util.Iterator;
import java.util.LinkedList;


public class JufroMenu {
    private LinkedList<String> opc=null;
    private LinkedList<String> lnk=null;
    
    public JufroMenu() {
        this.opc=new LinkedList<String>();
        this.lnk=new LinkedList<String>();
    }
    
   public void addOptions(String setOfChoices){
       if (this.opc==null){
           this.opc = new LinkedList();
       }
       String op[] = setOfChoices.split(",");
       for(int k=0;k<op.length;k++){
           this.opc.add(op[k]);
       }
   } 
   
   public void addMenuItemFromContent(String choiceCaption,int contentID){
       this.opc.add(choiceCaption);
       this.lnk.add("index.jsp?content="+contentID);
   }
   
   public void addMenuItemFromHtml(String choiceCaption,String htmlFile){
       this.opc.add(choiceCaption);
       this.lnk.add("index.jsp?htmlFile="+htmlFile);
   }
   
   public void addMenuItemFromJSP(String choiceCaption,String jspFile){
       this.opc.add(choiceCaption);
       this.lnk.add(jspFile);
   }
   
   
           
           
           
   public String getHtml() {
       String o,lk,ret="<div class=\"menu\"><ul>";
       Iterator it = this.opc.iterator();
       Iterator i2 = this.lnk.iterator();
       while(it.hasNext()){
           o = (String)it.next();
           lk = (String)i2.next();
           ret+="<li><a href=\""+lk+"\" title=\"lnk\">"+o+"</a></li>";
       }
        ret+="</ul></div>";
        return ret;
   }
    
}
