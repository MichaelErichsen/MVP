/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import org.historyresearchenvironment.usergui.dialogs.AboutDialog;
/*    */ 
/*    */ public class AboutHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(org.eclipse.swt.widgets.Shell shell)
/*    */   {
/* 10 */     AboutDialog dialog = new AboutDialog(shell);
/* 11 */     dialog.open();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\AboutHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */