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
///* 21 */     setNeedsProgressMonitor(true);
///*    */   }
///*    */   
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */   public void addPages()
///*    */   {
///* 31 */     this.wizardPage = new TMG9ProjectWizardPage();
///* 32 */     addPage(this.wizardPage);
///*    */   }
///*    */   
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */ 
///*    */   public String getWindowTitle()
///*    */   {
///* 42 */     return "Convert TMG9 Project";
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
///* 53 */     return true;
///*    */   }
///*    */ }
//
