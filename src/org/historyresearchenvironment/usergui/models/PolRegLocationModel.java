/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolRegLocationModel
/*    */ {
/*    */   private String address;
/*    */   
/*    */ 
/*    */   private String date;
/*    */   
/*    */ 
/*    */   private String latitude;
/*    */   
/*    */   private String longitude;
/*    */   
/*    */ 
/*    */   public PolRegLocationModel()
/*    */   {
/* 20 */     this.address = "";
/* 21 */     this.date = "";
/* 22 */     this.latitude = "";
/* 23 */     this.longitude = "";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getAddress()
/*    */   {
/* 30 */     return this.address;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getDate()
/*    */   {
/* 37 */     return this.date;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getLatitude()
/*    */   {
/* 44 */     return this.latitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getLongitude()
/*    */   {
/* 51 */     return this.longitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAddress(String address)
/*    */   {
/* 59 */     this.address = address;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDate(String date)
/*    */   {
/* 67 */     this.date = date;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLatitude(String latitude)
/*    */   {
/* 75 */     this.latitude = latitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLongitude(String longitude)
/*    */   {
/* 83 */     this.longitude = longitude;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 93 */     return this.address + ", " + this.date + ", " + this.latitude + ", " + this.longitude;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PolRegLocationModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */