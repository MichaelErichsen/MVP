///*    */ package net.myerichsen.hremvp.handlers;
//
///*    */ import org.eclipse.e4.core.di.annotations.Execute;
///*    */ import org.eclipse.jface.wizard.WizardDialog;
///*    */ import org.eclipse.swt.widgets.Shell;
//
//import net.myerichsen.hremvp.dialogs.TMG9ProjectWizard;
//
//public class TMG9WizardHandler
///*    */ {
///*    */   @Execute
///*    */   public void execute(Shell shell)
///*    */   {
///* 22 */     WizardDialog wizardDialog = new WizardDialog(shell, new TMG9ProjectWizard());
///* 23 */     if (wizardDialog.open() == 0) {
///* 24 */       System.out.println("Ok pressed");
///*    */     } else {
///* 26 */       System.out.println("Cancel pressed");
///*    */     }
///*    */   }
///*    */ }
//
//
