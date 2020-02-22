/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.e4.ui.di.Persist;
/*    */ import org.eclipse.e4.ui.model.application.ui.MDirtyable;
/*    */ import org.eclipse.jface.viewers.ArrayContentProvider;
/*    */ import org.eclipse.jface.viewers.TableViewer;
/*    */ import org.eclipse.swt.events.ModifyEvent;
/*    */ import org.eclipse.swt.events.ModifyListener;
/*    */ import org.eclipse.swt.layout.GridData;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Table;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocationSample
/*    */ {
/*    */   private Text txtInput;
/*    */   private TableViewer tableViewer;
/*    */   @Inject
/*    */   private MDirtyable dirty;
/*    */   
/*    */   @PostConstruct
/*    */   public void createComposite(Composite parent)
/*    */   {
/* 36 */     parent.setLayout(new GridLayout(1, false));
/*    */     
/* 38 */     this.txtInput = new Text(parent, 2048);
/* 39 */     this.txtInput.setMessage("Enter text to mark part as dirty");
/* 40 */     this.txtInput.addModifyListener(new ModifyListener()
/*    */     {
/*    */       public void modifyText(ModifyEvent e) {
/* 43 */         LocationSample.this.dirty.setDirty(true);
/*    */       }
/* 45 */     });
/* 46 */     this.txtInput.setLayoutData(new GridData(768));
/*    */     
/* 48 */     this.tableViewer = new TableViewer(parent);
/*    */     
/* 50 */     this.tableViewer.setContentProvider(ArrayContentProvider.getInstance());
/*    */     
/* 52 */     this.tableViewer.setInput(createInitialDataModel());
/* 53 */     this.tableViewer.getTable().setLayoutData(new GridData(1808));
/*    */   }
/*    */   
/*    */   private List<String> createInitialDataModel() {
/* 57 */     return Arrays.asList(new String[] { "Here", "There", "Everywhere", "Somewhere else again" });
/*    */   }
/*    */   
/*    */   @Persist
/*    */   public void save() {
/* 62 */     this.dirty.setDirty(false);
/*    */   }
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {
/* 67 */     this.tableViewer.getTable().setFocus();
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\LocationSample.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */