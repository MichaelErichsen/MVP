/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZestPersonNode
/*    */ {
/*    */   private final int perNo;
/*    */   private final String name;
/*    */   private final String sex;
/*    */   private final List<ZestPersonNode> connections;
/*    */   
/*    */   public ZestPersonNode(int perNo, String name, String sex)
/*    */   {
/* 20 */     this.perNo = perNo;
/* 21 */     this.name = name;
/* 22 */     this.sex = sex;
/* 23 */     this.connections = new ArrayList();
/*    */   }
/*    */   
/*    */   public List<ZestPersonNode> getConnectedTo() {
/* 27 */     return this.connections;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 34 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getPerNo()
/*    */   {
/* 41 */     return this.perNo;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getSex()
/*    */   {
/* 48 */     return this.sex;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\ZestPersonNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */