/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.e4.core.di.annotations.Optional;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ import org.eclipse.e4.ui.di.Focus;
/*    */ import org.eclipse.e4.ui.di.UIEventTopic;
/*    */ import org.eclipse.swt.layout.GridData;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Table;
/*    */ import org.eclipse.swt.widgets.TableColumn;
/*    */ import org.eclipse.swt.widgets.TableItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Console
/*    */ {
/*    */   @Inject
/*    */   IEventBroker eventBroker;
/*    */   private Table tableMessages;
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 34 */     parent.setLayout(new GridLayout(1, false));
/*    */     
/* 36 */     this.tableMessages = new Table(parent, 67584);
/* 37 */     this.tableMessages.setLayoutData(new GridData(4, 4, true, true, 1, 1));
/* 38 */     this.tableMessages.setHeaderVisible(true);
/* 39 */     this.tableMessages.setLinesVisible(true);
/*    */     
/* 41 */     TableColumn tblclmnMessage = new TableColumn(this.tableMessages, 0);
/* 42 */     tblclmnMessage.setText("Message");
/* 43 */     tblclmnMessage.setWidth(1000);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Inject
/*    */   @Optional
/*    */   public void messageHandler(@UIEventTopic("LOG") String s)
/*    */   {
/* 60 */     TableItem tableItem = new TableItem(this.tableMessages, 0);
/* 61 */     tableItem.setText(s);
/* 62 */     this.tableMessages.setSelection(tableItem);
/*    */   }
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\Console.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */