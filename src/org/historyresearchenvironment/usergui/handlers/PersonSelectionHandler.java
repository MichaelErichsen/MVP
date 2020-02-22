/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ import org.historyresearchenvironment.usergui.dialogs.PersonSelectionDialog;
/*    */ 
/*    */ public class PersonSelectionHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(Shell shell)
/*    */   {
/* 11 */     PersonSelectionDialog dialog = new PersonSelectionDialog(shell);
/* 12 */     dialog.open();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\PersonSelectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */