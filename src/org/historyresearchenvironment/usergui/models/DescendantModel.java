/*    */ package org.historyresearchenvironment.usergui.models;
/*    */ 
/*    */ import java.util.Vector;
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
/*    */ public class DescendantModel
/*    */   extends AbstractHreModel
/*    */ {
/*    */   private int current;
/*    */   private String name;
/*    */   private Vector<Integer> children;
/*    */   
/*    */   public DescendantModel() {}
/*    */   
/*    */   public DescendantModel(int current, Vector<Integer> children)
/*    */   {
/* 27 */     this.current = current;
/* 28 */     this.children = children;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Vector<Integer> getChildren()
/*    */   {
/* 35 */     return this.children;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getCurrent()
/*    */   {
/* 42 */     return this.current;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 49 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setChildren(Vector<Integer> children)
/*    */   {
/* 57 */     this.children = children;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCurrent(int current)
/*    */   {
/* 65 */     this.current = current;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 73 */     this.name = name;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\models\DescendantModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */