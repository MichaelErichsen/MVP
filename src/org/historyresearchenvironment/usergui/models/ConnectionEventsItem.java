/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionEventsItem
/*    */ {
/*    */   private String person;
/*    */   
/*    */   private String role;
/*    */   
/*    */   private String event;
/*    */   
/*    */   private String date;
/*    */   
/*    */ 
/*    */   public ConnectionEventsItem(String person, String role, String event, String date)
/*    */   {
/* 18 */     this.person = person;
/* 19 */     this.role = role;
/* 20 */     this.event = event;
/* 21 */     this.date = date;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getDate()
/*    */   {
/* 28 */     return this.date;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getEvent()
/*    */   {
/* 35 */     return this.event;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getPerson()
/*    */   {
/* 42 */     return this.person;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getRole()
/*    */   {
/* 49 */     return this.role;
/*    */   }
/*    */   
/*    */   public String[] getStrings() {
/* 53 */     String[] sa = { this.person, this.role, this.event, this.date };
/* 54 */     return sa;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDate(String date)
/*    */   {
/* 62 */     this.date = date;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setEvent(String event)
/*    */   {
/* 70 */     this.event = event;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPerson(String person)
/*    */   {
/* 78 */     this.person = person;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setRole(String role)
/*    */   {
/* 86 */     this.role = role;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\ConnectionEventsItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */