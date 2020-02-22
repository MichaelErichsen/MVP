/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.jface.wizard.WizardDialog;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ import org.historyresearchenvironment.usergui.wizards.TMG9ProjectWizard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TMG9WizardHandler
/*    */ {
/*    */   @Execute
/*    */   public void execute(Shell shell)
/*    */   {
/* 22 */     WizardDialog wizardDialog = new WizardDialog(shell, new TMG9ProjectWizard());
/* 23 */     if (wizardDialog.open() == 0) {
/* 24 */       System.out.println("Ok pressed");
/*    */     } else {
/* 26 */       System.out.println("Cancel pressed");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\TMG9WizardHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */