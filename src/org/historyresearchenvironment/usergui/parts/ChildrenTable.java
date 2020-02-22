/*    */ package org.historyresearchenvironment.usergui.parts;
/*    */ 
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.eclipse.e4.ui.di.Focus;
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
/*    */ public class ChildrenTable
/*    */ {
/*    */   private Table table;
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 28 */     parent.setLayout(new GridLayout(1, false));
/*    */     
/* 30 */     this.table = new Table(parent, 67584);
/* 31 */     this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
/* 32 */     this.table.setHeaderVisible(true);
/* 33 */     this.table.setLinesVisible(true);
/*    */     
/* 35 */     TableColumn tblclmnNewColumn = new TableColumn(this.table, 0);
/* 36 */     tblclmnNewColumn.setWidth(100);
/* 37 */     tblclmnNewColumn.setText("Born");
/*    */     
/* 39 */     TableColumn tblclmnDied = new TableColumn(this.table, 0);
/* 40 */     tblclmnDied.setWidth(100);
/* 41 */     tblclmnDied.setText("Died");
/*    */     
/* 43 */     TableColumn tblclmnChildren = new TableColumn(this.table, 0);
/* 44 */     tblclmnChildren.setMoveable(true);
/* 45 */     tblclmnChildren.setWidth(100);
/* 46 */     tblclmnChildren.setText("Children: 4");
/*    */     
/* 48 */     TableColumn tblclmnSex = new TableColumn(this.table, 0);
/* 49 */     tblclmnSex.setWidth(100);
/* 50 */     tblclmnSex.setText("Sex");
/*    */     
/* 52 */     TableColumn tblclmnPartner = new TableColumn(this.table, 0);
/* 53 */     tblclmnPartner.setWidth(100);
/* 54 */     tblclmnPartner.setText("Partner");
/*    */     
/* 56 */     TableItem tableItem = new TableItem(this.table, 0);
/* 57 */     tableItem.setText(new String[] { "1832", "1908", "Conrad Zinck Jensen Thorsager (1630)", "M", 
/* 58 */       "Oliva Birgitte Holst (92)" });
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\ChildrenTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */