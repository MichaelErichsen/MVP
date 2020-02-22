/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventAssociatesModel
/*    */ {
/*    */   private String type;
/*    */   
/*    */ 
/*    */ 
/*    */   private String role;
/*    */   
/*    */ 
/*    */   private String summary;
/*    */   
/*    */ 
/*    */ 
/*    */   public EventAssociatesModel(String type, String role, String summary)
/*    */   {
/* 21 */     this.type = type;
/* 22 */     this.role = role;
/* 23 */     this.summary = summary;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getRole()
/*    */   {
/* 30 */     return this.role;
/*    */   }
/*    */   
/*    */   public String[] getStrings()
/*    */   {
/* 35 */     String[] sa = new String[3];
/* 36 */     sa[0] = this.type;
/* 37 */     sa[1] = this.role;
/* 38 */     sa[2] = this.summary;
/*    */     
/* 40 */     return sa;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getSummary()
/*    */   {
/* 47 */     return this.summary;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getType()
/*    */   {
/* 54 */     return this.type;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setRole(String role)
/*    */   {
/* 62 */     this.role = role;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSummary(String summary)
/*    */   {
/* 70 */     this.summary = summary;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setType(String type)
/*    */   {
/* 78 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\EventAssociatesModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */