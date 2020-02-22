/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import org.eclipse.swt.graphics.Font;
/*     */ import org.eclipse.swt.graphics.FontData;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.TableColumn;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.eclipse.wb.swt.ResourceManager;
/*     */ import org.eclipse.wb.swt.SWTResourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonalRelativesExplorerComposite
/*     */   extends Composite
/*     */ {
/*     */   private Text textGenNo;
/*     */   private Text textComAncM;
/*     */   private Text textComAncF;
/*     */   private Table tableParents;
/*     */   
/*     */   public PersonalRelativesExplorerComposite(Composite parent, int style)
/*     */   {
/*  34 */     super(parent, style);
/*  35 */     setLayout(new GridLayout(7, false));
/*  36 */     parent.setFont(getHreFont(parent));
/*     */     
/*  38 */     Label btnHide = new Label(this, 0);
/*  39 */     btnHide.setToolTipText("Does nothing");
/*  40 */     btnHide.setImage(
/*  41 */       ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/HideCorner.png"));
/*     */     
/*  43 */     Label lblGeneration = new Label(this, 0);
/*  44 */     lblGeneration.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  45 */     lblGeneration.setText("Generation");
/*     */     
/*  47 */     this.textGenNo = new Text(this, 0);
/*  48 */     GridData gd_textGenNo = new GridData(16384, 16777216, false, false, 1, 1);
/*  49 */     gd_textGenNo.widthHint = 32;
/*  50 */     this.textGenNo.setLayoutData(gd_textGenNo);
/*  51 */     this.textGenNo.setEditable(false);
/*     */     
/*  53 */     Label lblCommonAncestorm = new Label(this, 0);
/*  54 */     lblCommonAncestorm.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  55 */     lblCommonAncestorm.setText("Common Ancestor (M)");
/*     */     
/*  57 */     this.textComAncM = new Text(this, 2048);
/*  58 */     this.textComAncM.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  60 */     Label lblCommonAncestorf = new Label(this, 0);
/*  61 */     lblCommonAncestorf.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  62 */     lblCommonAncestorf.setText("Common Ancestor (F)");
/*     */     
/*  64 */     this.textComAncF = new Text(this, 2048);
/*  65 */     this.textComAncF.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  67 */     this.tableParents = new Table(this, 67584);
/*  68 */     GridData gd_tableParents = new GridData(4, 4, true, true, 7, 1);
/*  69 */     gd_tableParents.widthHint = 702;
/*  70 */     this.tableParents.setLayoutData(gd_tableParents);
/*  71 */     this.tableParents.setFont(getHreFont(parent));
/*  72 */     this.tableParents.setHeaderVisible(true);
/*  73 */     this.tableParents.setLinesVisible(true);
/*     */     
/*  75 */     TableColumn tblclmnName = new TableColumn(this.tableParents, 0);
/*  76 */     tblclmnName.setWidth(347);
/*  77 */     tblclmnName.setText("Name");
/*     */     
/*  79 */     TableColumn tblclmnRelationship = new TableColumn(this.tableParents, 0);
/*  80 */     tblclmnRelationship.setWidth(74);
/*  81 */     tblclmnRelationship.setText("Relationship");
/*     */     
/*  83 */     TableColumn tblclmnSex = new TableColumn(this.tableParents, 0);
/*  84 */     tblclmnSex.setWidth(32);
/*  85 */     tblclmnSex.setText("Sex");
/*     */     
/*  87 */     TableColumn tblclmnLifespan = new TableColumn(this.tableParents, 0);
/*  88 */     tblclmnLifespan.setWidth(75);
/*  89 */     tblclmnLifespan.setText("Lifespan");
/*     */     
/*  91 */     TableColumn tblclmnGen = new TableColumn(this.tableParents, 0);
/*  92 */     tblclmnGen.setWidth(39);
/*  93 */     tblclmnGen.setText("Gen.");
/*     */     
/*  95 */     TableColumn tblclmnCh = new TableColumn(this.tableParents, 0);
/*  96 */     tblclmnCh.setWidth(33);
/*  97 */     tblclmnCh.setText("Ch.");
/*     */     
/*  99 */     TableColumn tblclmnOptionalFieldsHere = new TableColumn(this.tableParents, 0);
/* 100 */     tblclmnOptionalFieldsHere.setWidth(100);
/* 101 */     tblclmnOptionalFieldsHere.setText("Optional fields here");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void checkSubclass() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Font getHreFont(Composite parent)
/*     */   {
/* 115 */     Font font = parent.getShell().getFont();
/* 116 */     FontData fd = font.getFontData()[0];
/* 117 */     font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
/* 118 */     return font;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Table getTableParents()
/*     */   {
/* 125 */     return this.tableParents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Text getTextComAncF()
/*     */   {
/* 132 */     return this.textComAncF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Text getTextComAncM()
/*     */   {
/* 139 */     return this.textComAncM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Text getTextGenNo()
/*     */   {
/* 146 */     return this.textGenNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTableParents(Table tableParents)
/*     */   {
/* 154 */     this.tableParents = tableParents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTextComAncF(Text textComAncF)
/*     */   {
/* 162 */     this.textComAncF = textComAncF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTextComAncM(Text textComAncM)
/*     */   {
/* 170 */     this.textComAncM = textComAncM;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTextGenNo(Text textGenNo)
/*     */   {
/* 178 */     this.textGenNo = textGenNo;
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonalRelativesExplorerComposite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */