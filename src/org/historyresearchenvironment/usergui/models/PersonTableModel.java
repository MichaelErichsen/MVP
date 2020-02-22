/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import org.historyresearchenvironment.usergui.providers.AbstractHreProvider;
/*    */ 
/*    */ 
/*    */ public class PersonTableModel
/*    */   extends AbstractHreProvider
/*    */ {
/*    */   public int counter;
/*    */   
/*    */   public PersonTableModel(int counter)
/*    */   {
/* 13 */     this.counter = counter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void readFromH2(int i) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 30 */     return "Item " + this.counter;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\PersonTableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */