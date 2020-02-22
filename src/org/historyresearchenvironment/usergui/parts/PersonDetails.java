/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.eclipse.e4.ui.di.Focus;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.eclipse.swt.widgets.TableItem;
/*     */ import org.historyresearchenvironment.usergui.models.PersonModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonDetails
/*     */ {
/*     */   private Table table;
/*     */   private Table table_1;
/*  24 */   PersonModel perosn = new PersonModel();
/*     */   
/*     */ 
/*     */ 
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  31 */     parent.setLayout(new GridLayout(2, false));
/*     */     
/*  33 */     this.table = new Table(parent, 67584);
/*  34 */     GridData gd_table = new GridData(4, 4, true, true, 2, 1);
/*  35 */     gd_table.heightHint = 88;
/*  36 */     gd_table.widthHint = 444;
/*  37 */     this.table.setLayoutData(gd_table);
/*  38 */     this.table.setHeaderVisible(true);
/*  39 */     this.table.setLinesVisible(true);
/*     */     
/*  41 */     TableColumn tblclmnType = new TableColumn(this.table, 0);
/*  42 */     tblclmnType.setWidth(100);
/*  43 */     tblclmnType.setText("Type");
/*     */     
/*  45 */     TableColumn tblclmnName = new TableColumn(this.table, 0);
/*  46 */     tblclmnName.setWidth(138);
/*  47 */     tblclmnName.setText("Name");
/*     */     
/*  49 */     TableItem tableItem = new TableItem(this.table, 0);
/*  50 */     tableItem.setText(new String[] { "Name", "Niels Jensen (40)" });
/*     */     
/*  52 */     TableItem tableItem_1 = new TableItem(this.table, 0);
/*  53 */     tableItem_1.setText(new String[] { "Father", "Jens Madsen (80)" });
/*     */     
/*  55 */     TableItem tableItem_2 = new TableItem(this.table, 0);
/*  56 */     tableItem_2.setText(new String[] { "Mother", "Dorthe Jørgensdatter (81)" });
/*     */     
/*  58 */     this.table_1 = new Table(parent, 67584);
/*  59 */     GridData gd_table_1 = new GridData(4, 4, true, true, 2, 1);
/*  60 */     gd_table_1.widthHint = 617;
/*  61 */     this.table_1.setLayoutData(gd_table_1);
/*  62 */     this.table_1.setHeaderVisible(true);
/*  63 */     this.table_1.setLinesVisible(true);
/*     */     
/*  65 */     TableColumn tblclmnNewColumn = new TableColumn(this.table_1, 0);
/*  66 */     tblclmnNewColumn.setWidth(100);
/*  67 */     tblclmnNewColumn.setText("Type");
/*     */     
/*  69 */     TableColumn tblclmnNewColumn_1 = new TableColumn(this.table_1, 0);
/*  70 */     tblclmnNewColumn_1.setWidth(100);
/*  71 */     tblclmnNewColumn_1.setText("Date");
/*     */     
/*  73 */     TableColumn tblclmnNewColumn_2 = new TableColumn(this.table_1, 0);
/*  74 */     tblclmnNewColumn_2.setWidth(210);
/*  75 */     tblclmnNewColumn_2.setText("Name/Place");
/*     */     
/*  77 */     TableColumn tblclmnNewColumn_3 = new TableColumn(this.table_1, 0);
/*  78 */     tblclmnNewColumn_3.setWidth(48);
/*  79 */     tblclmnNewColumn_3.setText("Age");
/*     */     
/*  81 */     TableColumn tblclmnNewColumn_4 = new TableColumn(this.table_1, 32);
/*  82 */     tblclmnNewColumn_4.setWidth(58);
/*  83 */     tblclmnNewColumn_4.setText("Memo");
/*     */     
/*  85 */     TableColumn tblclmnNewColumn_5 = new TableColumn(this.table_1, 0);
/*  86 */     tblclmnNewColumn_5.setWidth(56);
/*  87 */     tblclmnNewColumn_5.setText("Sources");
/*     */     
/*  89 */     TableItem tableItem_3 = new TableItem(this.table_1, 0);
/*  90 */     tableItem_3.setText(new String[] { "Relation", "", "G-greatgrandfather of Michael Erichsen" });
/*     */     
/*  92 */     TableItem tableItem_4 = new TableItem(this.table_1, 0);
/*  93 */     tableItem_4.setText(new String[] { "Born", "23 Nov 1791", "Asnæs", "0" });
/*     */     
/*  95 */     TableItem tableItem_5 = new TableItem(this.table_1, 0);
/*  96 */     tableItem_5.setText(new String[] { "Baptized", "27 Nov 1791", "Asnæs", "0" });
/*     */     
/*  98 */     TableItem tableItem_6 = new TableItem(this.table_1, 0);
/*  99 */     tableItem_6.setText(new String[] { "Census", "1801", "(w) Jens Madsen & Dorthe Jørgensdatter", "~10" });
/*     */     
/* 101 */     TableItem tableItem_7 = new TableItem(this.table_1, 0);
/* 102 */     tableItem_7.setText(new String[] { "Graduated", "1812", "Jonstrup Seminarium", "~21" });
/*     */     
/* 104 */     TableItem tableItem_8 = new TableItem(this.table_1, 0);
/* 105 */     tableItem_8.setText(new String[] { "Occupation", "1812", "lærer i Herrestrup", "~21" });
/*     */     
/* 107 */     TableItem tableItem_9 = new TableItem(this.table_1, 0);
/* 108 */     tableItem_9.setText(new String[] { "Occupation", "1812", "Private teacher at", "~21" });
/*     */     
/* 110 */     TableItem tableItem_10 = new TableItem(this.table_1, 0);
/* 111 */     tableItem_10.setText(new String[] { "Occupation", "1814", "Teacher i Nexø", "~23" });
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void dispose() {}
/*     */   
/*     */   @Focus
/*     */   public void setFocus() {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonDetails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */