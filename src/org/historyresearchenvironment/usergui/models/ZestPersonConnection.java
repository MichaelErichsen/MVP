/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZestPersonConnection
/*    */ {
/*    */   final int perNo;
/*    */   
/*    */ 
/*    */ 
/*    */   final String label;
/*    */   
/*    */ 
/*    */   final ZestPersonNode source;
/*    */   
/*    */ 
/*    */   final ZestPersonNode destination;
/*    */   
/*    */ 
/*    */ 
/*    */   public ZestPersonConnection(int connId, String label, ZestPersonNode source, ZestPersonNode destination)
/*    */   {
/* 24 */     this.perNo = connId;
/* 25 */     this.label = label;
/* 26 */     this.source = source;
/* 27 */     this.destination = destination;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ZestPersonNode getDestination()
/*    */   {
/* 34 */     return this.destination;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 41 */     return this.label;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ZestPersonNode getSource()
/*    */   {
/* 48 */     return this.source;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\ZestPersonConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */