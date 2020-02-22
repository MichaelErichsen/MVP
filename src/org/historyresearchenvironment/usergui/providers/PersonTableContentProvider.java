/*    */ package org.historyresearchenvironment.usergui.providers;
/*    */ 
/*    */ import org.eclipse.jface.viewers.IStructuredContentProvider;
/*    */ import org.historyresearchenvironment.usergui.models.PersonTableModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersonTableContentProvider
/*    */   implements IStructuredContentProvider
/*    */ {
/*    */   public Object[] getElements(Object inputElement)
/*    */   {
/* 17 */     return (PersonTableModel[])inputElement;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\providers\PersonTableContentProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */