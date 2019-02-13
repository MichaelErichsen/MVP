///*    */ package net.myerichsen.hremvp.dialogs;
///*    */ 
///*    */ import org.eclipse.jface.wizard.Wizard;
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ public class TMG9ProjectWizard
///*    */   extends Wizard
///*    */ {
///*    */   protected TMG9ProjectWizardPage wizardPage;
///*    */   
///*    */   public TMG9ProjectWizard()
///*    */   {
//     setNeedsProgressMonitor(true);
///*    */   }
///*    */   
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */   public void addPages()
///*    */   {
//     this.wizardPage = new TMG9ProjectWizardPage();
//     addPage(this.wizardPage);
///*    */   }
///*    */   
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */   public String getWindowTitle()
///*    */   {
//     return "Convert TMG9 Project";
///*    */   }
///*    */   
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */   public boolean performFinish()
///*    */   {
//     return true;
///*    */   }
///*    */ }
//
