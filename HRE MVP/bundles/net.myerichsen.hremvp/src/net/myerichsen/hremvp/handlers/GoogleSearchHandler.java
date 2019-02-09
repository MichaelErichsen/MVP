/*    */ package net.myerichsen.hremvp.handlers;

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

public class GoogleSearchHandler
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
/*    */     
/* 32 */     MPart part = MBasicFactory.INSTANCE.createPart();
/* 33 */     part.setLabel("Google Search");
/* 34 */     part.setCloseable(true);
/* 35 */     part.setContainerData("500");
/* 36 */     part.setVisible(true);
/* 37 */     part.setContributionURI(
/* 38 */       "bundleclass://org.historyresearchenvironment.usergui/org.historyresearchenvironment.usergui.parts.GoogleBrowser");
/* 39 */     List<MPartStack> stacks = modelService.findElements(application, null, MPartStack.class, null);
/* 40 */     ((MPartStack)stacks.get(stacks.size() - 1)).getChildren().add(part);
/* 41 */     partService.showPart(part, EPartService.PartState.ACTIVATE);
/* 42 */     LOGGER.info("executed");
/*    */   }
/*    */ }


