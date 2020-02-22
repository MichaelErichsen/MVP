/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import org.eclipse.e4.ui.workbench.IWorkbench;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ 
/*    */ public class QuitHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(IWorkbench workbench, Shell shell)
/*    */   {
/* 11 */     if (org.eclipse.jface.dialogs.MessageDialog.openConfirm(shell, "Confirmation", "Do you want to exit?")) {
/* 12 */       workbench.close();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\QuitHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */