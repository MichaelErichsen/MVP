/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.eclipse.e4.ui.model.application.MApplication;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MPart;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EModelService;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EPartService;
/*    */ 
/*    */ public class OpenSampleViewHandler
/*    */ {
/*    */   @org.eclipse.e4.core.di.annotations.Execute
/*    */   public void execute(EPartService partService, MApplication application, EModelService modelService)
/*    */   {
/* 16 */     MPart part = MBasicFactory.INSTANCE.createPart();
/* 17 */     part.setLabel("New Sample Part");
/* 18 */     part.setContributionURI("bundleclass://org.historyresearchenvironment.usergui/org.historyresearchenvironment.usergui.parts.SamplePart");
/*    */     
/* 20 */     List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
/* 21 */     ((MPartStack)stacks.get(0)).getChildren().add(part);
/* 22 */     partService.showPart(part, org.eclipse.e4.ui.workbench.modeling.EPartService.PartState.ACTIVATE);
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\OpenSampleViewHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */