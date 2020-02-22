/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.core.di.annotations.Optional;
/*    */ import org.eclipse.e4.ui.di.UIEventTopic;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
/*    */ import org.eclipse.zest.core.viewers.GraphViewer;
/*    */ import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
/*    */ import org.eclipse.zest.layouts.LayoutAlgorithm;
/*    */ import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
/*    */ import org.historyresearchenvironment.usergui.providers.ZestLabelProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.ZestNodeContentProvider;
/*    */ import org.historyresearchenvironment.usergui.providers.ZestPersonNodeModelContentProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZestView
/*    */   extends AbstractHREGuiPart
/*    */   implements IZoomableWorkbenchPart
/*    */ {
/*    */   private GraphViewer viewer;
/*    */   
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */   public AbstractZoomableViewer getZoomableViewer()
/*    */   {
/* 42 */     return this.viewer;
/*    */   }
/*    */   
/*    */   @PostConstruct
/*    */   public void postConstruct(Composite parent) {
/* 47 */     this.viewer = new GraphViewer(parent, 2048);
/* 48 */     this.viewer.setContentProvider(new ZestNodeContentProvider());
/* 49 */     this.viewer.setLabelProvider(new ZestLabelProvider());
/* 50 */     ZestPersonNodeModelContentProvider model = new ZestPersonNodeModelContentProvider(1);
/* 51 */     this.viewer.setInput(model.getNodes());
/* 52 */     LayoutAlgorithm layout = setLayout();
/* 53 */     this.viewer.setLayoutAlgorithm(layout, true);
/* 54 */     this.viewer.applyLayout();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private LayoutAlgorithm setLayout()
/*    */   {
/* 63 */     LayoutAlgorithm layout = new TreeLayoutAlgorithm(1);
/* 64 */     return layout;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Inject
/*    */   @Optional
/*    */   private void subscribePersonUpdateTopic(@UIEventTopic("PERSON_UPDATE_TOPIC") int perNo)
/*    */   {
/* 74 */     ZestPersonNodeModelContentProvider model = new ZestPersonNodeModelContentProvider(perNo);
/* 75 */     this.viewer.setInput(model.getNodes());
/* 76 */     this.viewer.refresh();
/*    */   }
/*    */   
/*    */   protected void updateGui() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\ZestView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */