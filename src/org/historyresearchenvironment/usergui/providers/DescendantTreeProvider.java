/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Vector;
/*    */ import java.util.logging.Logger;
/*    */ import org.historyresearchenvironment.usergui.models.DescendantModel;
/*    */ import org.historyresearchenvironment.usergui.models.DescendantVector;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DescendantTreeProvider
/*    */   extends AbstractHreProvider
/*    */ {
/*    */   protected DescendantVector descendantItems;
/* 16 */   protected int perNo = 1;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DescendantTreeProvider() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DescendantTreeProvider(int perNo)
/*    */   {
/* 30 */     setKey(Integer.toString(perNo));
/* 31 */     this.descendantItems = new DescendantVector(this.conn, perNo);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addDescendant(int perNo, Vector<Integer> children)
/*    */   {
/* 39 */     DescendantModel di = new DescendantModel(perNo, children);
/*    */     
/* 41 */     if (this.descendantItems == null) {
/* 42 */       this.descendantItems = new DescendantVector(this.conn, perNo);
/*    */     }
/* 44 */     this.descendantItems.add(di);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public DescendantVector getDescendantItems()
/*    */   {
/* 51 */     return this.descendantItems;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getPerNo()
/*    */   {
/* 58 */     return this.perNo;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void readFromH2(int i)
/*    */   {
/* 70 */     this.descendantItems = new DescendantVector(this.conn, this.perNo);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDescendantItems(DescendantVector descendantItems)
/*    */   {
/* 78 */     this.descendantItems = descendantItems;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPerNo(int perNo)
/*    */   {
/* 86 */     this.perNo = perNo;
/* 87 */     setKey(Integer.toString(perNo));
/* 88 */     this.descendantItems = new DescendantVector(this.conn, perNo);
/*    */   }
/*    */   
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */     try
/*    */     {
/* 96 */       this.conn.close();
/*    */     } catch (SQLException e) {
/* 98 */       LOGGER.severe(e.getClass() + ": " + e.getMessage() + " at line " + e.getStackTrace()[0].getLineNumber());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\DescendantTreeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */