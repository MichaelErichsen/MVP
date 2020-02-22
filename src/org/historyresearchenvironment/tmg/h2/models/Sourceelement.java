/*    */ package org.historyresearchenvironment.tmg.h2.models;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Sourceelement
/*    */ {
/*    */   private int recno;
/*    */   private int dsid;
/*    */   private String element;
/*    */   private BigDecimal groupnum;
/*    */   private String tt;
/*    */   
/*    */   public int getDsid()
/*    */   {
/* 27 */     return this.dsid;
/*    */   }
/*    */   
/*    */   public String getElement() {
/* 31 */     return this.element;
/*    */   }
/*    */   
/*    */   public BigDecimal getGroupnum() {
/* 35 */     return this.groupnum;
/*    */   }
/*    */   
/*    */   public int getRecno() {
/* 39 */     return this.recno;
/*    */   }
/*    */   
/*    */   public String getTt() {
/* 43 */     return this.tt;
/*    */   }
/*    */   
/*    */   public void setDsid(int dsid) {
/* 47 */     this.dsid = dsid;
/*    */   }
/*    */   
/*    */   public void setElement(String element) {
/* 51 */     this.element = element;
/*    */   }
/*    */   
/*    */   public void setGroupnum(BigDecimal groupnum) {
/* 55 */     this.groupnum = groupnum;
/*    */   }
/*    */   
/*    */   public void setRecno(int recno) {
/* 59 */     this.recno = recno;
/*    */   }
/*    */   
/*    */   public void setTt(String tt) {
/* 63 */     this.tt = tt;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\tmg\h2\models\Sourceelement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */