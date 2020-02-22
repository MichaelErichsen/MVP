/*     */ package org.historyresearchenvironment.usergui.parts;
/*     */ 
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.eclipse.e4.ui.di.Focus;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Table;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.historyresearchenvironment.usergui.models.PersonModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonH2
/*     */ {
/*     */   private Text perNoText;
/*     */   private Text nameText;
/*     */   private Text fatherText;
/*     */   private Text motherText;
/*     */   private Text lastEditedText;
/*     */   private Text datasetText;
/*     */   private Text birthDateText;
/*     */   private Text deathDateText;
/*     */   private Text spouseText;
/*     */   private Text sexText;
/*     */   private Table eventTable;
/*     */   private Text messageText;
/*     */   private PersonModel personModel;
/*     */   
/*     */   @PostConstruct
/*     */   public void createControls(Composite parent)
/*     */   {
/*  39 */     parent.setLayout(new GridLayout(5, false));
/*     */     
/*  41 */     Label lblNewLabel = new Label(parent, 0);
/*  42 */     lblNewLabel.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  43 */     lblNewLabel.setText("Person No.");
/*     */     
/*  45 */     this.perNoText = new Text(parent, 2048);
/*  46 */     this.perNoText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  48 */     Button btnSearch = new Button(parent, 0);
/*  49 */     btnSearch.addMouseListener(new MouseAdapter()
/*     */     {
/*     */ 
/*     */       public void mouseDown(MouseEvent e)
/*     */       {
/*  54 */         PersonH2.this.personModel.readFromH2(Integer.parseInt(PersonH2.this.perNoText.getText()));
/*     */         
/*  56 */         PersonH2.this.nameText.setText(PersonH2.this.personModel.getSrnamedisp());
/*  57 */         PersonH2.this.fatherText.setText(PersonH2.this.personModel.getFatherName());
/*  58 */         PersonH2.this.motherText.setText(PersonH2.this.personModel.getMotherName());
/*  59 */         PersonH2.this.lastEditedText.setText(PersonH2.this.personModel.getLastEditString());
/*  60 */         PersonH2.this.datasetText.setText(PersonH2.this.personModel.getDatasetName());
/*  61 */         PersonH2.this.birthDateText.setText(PersonH2.this.personModel.getPbirth());
/*  62 */         PersonH2.this.deathDateText.setText(PersonH2.this.personModel.getPdeath());
/*  63 */         PersonH2.this.spouseText.setText(PersonH2.this.personModel.getSpoulastName());
/*  64 */         PersonH2.this.sexText.setText(PersonH2.this.personModel.getSex());
/*  65 */         PersonH2.this.messageText.setText(PersonH2.this.personModel.getMessage());
/*     */       }
/*  67 */     });
/*  68 */     btnSearch.setText("Search");
/*     */     
/*  70 */     Label lblNewLabel_1 = new Label(parent, 0);
/*  71 */     lblNewLabel_1.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  72 */     lblNewLabel_1.setText("Name");
/*     */     
/*  74 */     this.nameText = new Text(parent, 2048);
/*  75 */     this.nameText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  77 */     Label lblNewLabel_2 = new Label(parent, 0);
/*  78 */     lblNewLabel_2.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  79 */     lblNewLabel_2.setText("Father");
/*     */     
/*  81 */     this.fatherText = new Text(parent, 2048);
/*  82 */     this.fatherText.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/*  84 */     Label lblNewLabel_3 = new Label(parent, 0);
/*  85 */     lblNewLabel_3.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  86 */     lblNewLabel_3.setText("Mother");
/*     */     
/*  88 */     this.motherText = new Text(parent, 2048);
/*  89 */     this.motherText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  91 */     Label lblNewLabel_4 = new Label(parent, 0);
/*  92 */     lblNewLabel_4.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  93 */     lblNewLabel_4.setText("Last Edited");
/*     */     
/*  95 */     this.lastEditedText = new Text(parent, 2048);
/*  96 */     this.lastEditedText.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/*  98 */     Label lblNewLabel_5 = new Label(parent, 0);
/*  99 */     lblNewLabel_5.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 100 */     lblNewLabel_5.setText("Dataset");
/*     */     
/* 102 */     this.datasetText = new Text(parent, 2048);
/* 103 */     this.datasetText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/* 105 */     Label lblNewLabel_6 = new Label(parent, 0);
/* 106 */     lblNewLabel_6.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 107 */     lblNewLabel_6.setText("Birth Date");
/*     */     
/* 109 */     this.birthDateText = new Text(parent, 2048);
/* 110 */     this.birthDateText.setLayoutData(new GridData(4, 16777216, true, false, 2, 1));
/*     */     
/* 112 */     Label lblNewLabel_7 = new Label(parent, 0);
/* 113 */     lblNewLabel_7.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 114 */     lblNewLabel_7.setText("Death Date");
/*     */     
/* 116 */     this.deathDateText = new Text(parent, 2048);
/* 117 */     this.deathDateText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/* 119 */     Label lblNewLabel_8 = new Label(parent, 0);
/* 120 */     lblNewLabel_8.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 121 */     lblNewLabel_8.setText("Spouse");
/*     */     
/* 123 */     this.spouseText = new Text(parent, 2048);
/* 124 */     GridData gd_spouseText = new GridData(4, 16777216, true, false, 2, 1);
/* 125 */     gd_spouseText.widthHint = 346;
/* 126 */     this.spouseText.setLayoutData(gd_spouseText);
/*     */     
/* 128 */     Label lblNewLabel_9 = new Label(parent, 0);
/* 129 */     lblNewLabel_9.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/* 130 */     lblNewLabel_9.setText("Sex");
/*     */     
/* 132 */     this.sexText = new Text(parent, 2048);
/* 133 */     this.sexText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/* 135 */     Label lblNewLabel_10 = new Label(parent, 0);
/* 136 */     lblNewLabel_10.setText("Events");
/*     */     
/* 138 */     this.eventTable = new Table(parent, 67584);
/* 139 */     this.eventTable.setLayoutData(new GridData(4, 4, true, true, 4, 1));
/* 140 */     this.eventTable.setHeaderVisible(true);
/* 141 */     this.eventTable.setLinesVisible(true);
/*     */     
/* 143 */     this.messageText = new Text(parent, 2048);
/* 144 */     this.messageText.setEditable(false);
/* 145 */     this.messageText.setLayoutData(new GridData(4, 16777216, true, false, 5, 1));
/*     */     
/* 147 */     this.personModel = new PersonModel(1);
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void dispose() {}
/*     */   
/*     */   @Focus
/*     */   public void setFocus() {}
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\parts\PersonH2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */