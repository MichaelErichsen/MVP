/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EPartService;
/*    */ 
/*    */ public class SaveHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.CanExecute
/*    */   public boolean canExecute(EPartService partService)
/*    */   {
/* 11 */     if (partService != null) {
/* 12 */       return !partService.getDirtyParts().isEmpty();
/*    */     }
/* 14 */     return false;
/*    */   }
/*    */   
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(EPartService partService) {
/* 19 */     partService.saveAll(false);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\SaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */