/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.e4.ui.model.application.MApplication;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MPart;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EModelService;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EPartService;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
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
/*    */ public class PolRegBrowserHandler
/*    */ {
/*    */   @Execute
/*    */   public void execute(EPartService partService, MApplication application, EModelService modelService)
/*    */   {
/* 27 */     MPart part = MBasicFactory.INSTANCE.createPart();
/* 28 */     part.setLabel("Cph Police Registry Search");
/* 29 */     part.setContainerData("500");
/* 30 */     part.setCloseable(true);
/* 31 */     part.setVisible(true);
/* 32 */     part.setContributionURI(
/* 33 */       "bundleclass://org.historyresearchenvironment.usergui/org.historyresearchenvironment.usergui.parts.PolRegBrowser");
/* 34 */     List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
/* 35 */     ((MPartStack)stacks.get(stacks.size() - 1)).getChildren().add(part);
/* 36 */     partService.showPart(part, EPartService.PartState.ACTIVATE);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\PolRegBrowserHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */