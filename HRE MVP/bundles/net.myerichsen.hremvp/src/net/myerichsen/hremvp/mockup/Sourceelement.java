package net.myerichsen.hremvp.mockup;
 
 import java.math.BigDecimal;
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class Sourceelement
 {
   private int recno;
   private int dsid;
   private String element;
   private BigDecimal groupnum;
   private String tt;
   
   public int getDsid()
   {
     return this.dsid;
   }
   
   public String getElement() {
     return this.element;
   }
   
   public BigDecimal getGroupnum() {
     return this.groupnum;
   }
   
   public int getRecno() {
     return this.recno;
   }
   
   public String getTt() {
     return this.tt;
   }
   
   public void setDsid(int dsid) {
     this.dsid = dsid;
   }
   
   public void setElement(String element) {
     this.element = element;
   }
   
   public void setGroupnum(BigDecimal groupnum) {
     this.groupnum = groupnum;
   }
   
   public void setRecno(int recno) {
     this.recno = recno;
   }
   
   public void setTt(String tt) {
     this.tt = tt;
   }
 }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Sourceelement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */