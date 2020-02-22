/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import org.eclipse.e4.ui.model.application.MApplication;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EPartService;
/*    */ 
/*    */ public class ScreenhostPerspectiveHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(MApplication app, EPartService partService, org.eclipse.e4.ui.workbench.modeling.EModelService modelService)
/*    */   {
/* 11 */     partService.switchPerspective("org.historyresearchenvironment.usergui.perspective.screenshots");
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\ScreenhostPerspectiveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */