/*    */ package org.historyresearchenvironment.usergui.wizards;
/*    */ 
/*    */ import org.eclipse.jface.wizard.Wizard;
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
/*    */ public class TMG9ProjectWizard
/*    */   extends Wizard
/*    */ {
/*    */   protected TMG9ProjectWizardPage wizardPage;
/*    */   
/*    */   public TMG9ProjectWizard()
/*    */   {
/* 21 */     setNeedsProgressMonitor(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addPages()
/*    */   {
/* 31 */     this.wizardPage = new TMG9ProjectWizardPage();
/* 32 */     addPage(this.wizardPage);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getWindowTitle()
/*    */   {
/* 42 */     return "Convert TMG9 Project";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean performFinish()
/*    */   {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\wizards\TMG9ProjectWizard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */