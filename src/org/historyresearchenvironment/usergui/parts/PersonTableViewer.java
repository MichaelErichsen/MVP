/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.jface.viewers.LabelProvider;
/*    */ import org.eclipse.jface.viewers.TableViewer;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Table;
/*    */ import org.historyresearchenvironment.usergui.models.PersonTableModel;
/*    */ import org.historyresearchenvironment.usergui.providers.PersonTableContentProvider;
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
/*    */ 
/*    */ public class PersonTableViewer
/*    */   extends AbstractHREGuiPart
/*    */ {
/*    */   protected void callBusinessLayer(int i) {}
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 37 */     TableViewer v = new TableViewer(parent);
/* 38 */     v.setLabelProvider(new LabelProvider());
/*    */     
/*    */ 
/* 41 */     v.setContentProvider(new PersonTableContentProvider());
/* 42 */     PersonTableModel[] model = createModel();
/* 43 */     v.setInput(model);
/* 44 */     v.getTable().setLinesVisible(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private PersonTableModel[] createModel()
/*    */   {
/* 51 */     PersonTableModel[] elements = new PersonTableModel[10];
/*    */     
/* 53 */     for (int i = 0; i < 10; i++) {
/* 54 */       elements[i] = new PersonTableModel(i);
/*    */     }
/*    */     
/* 57 */     return elements;
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */   
/*    */   protected void updateGui() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonTableViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */