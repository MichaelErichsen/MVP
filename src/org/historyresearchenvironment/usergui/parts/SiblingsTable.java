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
/*    */ public class SiblingsTable
/*    */ {
/*    */   private Table table;
/*    */   
/*    */   @PostConstruct
/*    */   public void createControls(Composite parent)
/*    */   {
/* 23 */     parent.setLayout(new GridLayout(1, false));
/*    */     
/* 25 */     this.table = new Table(parent, 67584);
/* 26 */     this.table.setLayoutData(new GridData(4, 4, true, true, 1, 1));
/* 27 */     this.table.setHeaderVisible(true);
/* 28 */     this.table.setLinesVisible(true);
/*    */     
/* 30 */     TableColumn tblclmnNewColumn = new TableColumn(this.table, 0);
/* 31 */     tblclmnNewColumn.setWidth(100);
/* 32 */     tblclmnNewColumn.setText("Born");
/*    */     
/* 34 */     TableColumn tblclmnDied = new TableColumn(this.table, 0);
/* 35 */     tblclmnDied.setWidth(100);
/* 36 */     tblclmnDied.setText("Died");
/*    */     
/* 38 */     TableColumn tblclmnChildren = new TableColumn(this.table, 0);
/* 39 */     tblclmnChildren.setMoveable(true);
/* 40 */     tblclmnChildren.setWidth(100);
/* 41 */     tblclmnChildren.setText("Siblings: 7");
/*    */     
/* 43 */     TableColumn tblclmnSex = new TableColumn(this.table, 0);
/* 44 */     tblclmnSex.setWidth(100);
/* 45 */     tblclmnSex.setText("Sex");
/*    */     
/* 47 */     TableColumn tblclmnPartner = new TableColumn(this.table, 0);
/* 48 */     tblclmnPartner.setWidth(100);
/* 49 */     tblclmnPartner.setText("Partner");
/*    */     
/* 51 */     TableItem tableItem = new TableItem(this.table, 0);
/* 52 */     tableItem.setText(new String[] { "1794", "1820", "Karen Jensdatter (1713)", "F", "Christen Pedersen (7085)" });
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void dispose() {}
/*    */   
/*    */   @Focus
/*    */   public void setFocus() {}
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\SiblingsTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */