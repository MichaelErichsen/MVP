/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.logging.Logger;
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
/*    */ public class PolRegHandler
/*    */ {
/* 21 */   protected static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Execute
/*    */   public void execute(EPartService partService, MApplication application, EModelService modelService)
/*    */   {
/* 30 */     LOGGER.info("execute");
/* 31 */     MPart part = MBasicFactory.INSTANCE.createPart();
/* 32 */     part.setLabel("Cph Police Registry Search");
/* 33 */     part.setContainerData("500");
/* 34 */     part.setCloseable(true);
/* 35 */     part.setVisible(true);
/* 36 */     part.setContributionURI(
/* 37 */       "bundleclass://org.historyresearchenvironment.usergui/org.historyresearchenvironment.usergui.parts.PolRegExplorer");
/* 38 */     List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
/* 39 */     ((MPartStack)stacks.get(stacks.size() - 1)).getChildren().add(part);
/* 40 */     partService.showPart(part, EPartService.PartState.ACTIVATE);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\PolRegHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */