/*     */ package org.historyresearchenvironment.usergui.dialogs;
/*     */ 
/*     */ import org.eclipse.jface.dialogs.Dialog;
/*     */ import org.eclipse.jface.dialogs.IDialogConstants;
/*     */ import org.eclipse.swt.custom.CLabel;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Control;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AboutDialog
/*     */   extends Dialog
/*     */ {
/*     */   public AboutDialog(Shell parentShell)
/*     */   {
/*  32 */     super(parentShell);
/*     */   }
/*     */   
/*     */   protected void configureShell(Shell newShell)
/*     */   {
/*  37 */     super.configureShell(newShell);
/*  38 */     newShell.setText("About HRE");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createButtonsForButtonBar(Composite parent)
/*     */   {
/*  48 */     createButton(parent, 0, IDialogConstants.OK_LABEL, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Control createDialogArea(Composite parent)
/*     */   {
/*  58 */     Composite container = (Composite)super.createDialogArea(parent);
/*  59 */     container.setLayout(new GridLayout(2, false));
/*     */     
/*  61 */     CLabel lblNewLabel = new CLabel(container, 0);
/*  62 */     lblNewLabel.setLayoutData(new GridData(4, 16777216, false, false, 1, 4));
/*  63 */     lblNewLabel.setImage(
/*  64 */       ResourceManager.getPluginImage("org.historyresearchenvironment.usergui", "icons/HRE-144.png"));
/*  65 */     lblNewLabel.setText("");
/*     */     
/*  67 */     Label lblHistoryResearch = new Label(container, 0);
/*  68 */     lblHistoryResearch.setLayoutData(new GridData(16777216, 16777216, false, false, 1, 1));
/*  69 */     lblHistoryResearch.setFont(SWTResourceManager.getFont("Calibri", 14, 1));
/*  70 */     lblHistoryResearch.setText("History Research");
/*     */     
/*  72 */     Label lblNewLabel_1 = new Label(container, 0);
/*  73 */     lblNewLabel_1.setLayoutData(new GridData(16777216, 16777216, false, false, 1, 1));
/*  74 */     lblNewLabel_1.setFont(SWTResourceManager.getFont("Calibri", 14, 1));
/*  75 */     lblNewLabel_1.setText("Environment");
/*     */     
/*  77 */     Label lblBuild = new Label(container, 0);
/*  78 */     lblBuild.setLayoutData(new GridData(16777216, 16777216, false, false, 1, 1));
/*  79 */     lblBuild.setText("Build 0.0.5");
/*  80 */     lblBuild.setFont(SWTResourceManager.getFont("Calibri", 14, 1));
/*     */     
/*  82 */     Label label = new Label(container, 0);
/*  83 */     label.setLayoutData(new GridData(16777216, 128, false, false, 1, 1));
/*  84 */     label.setText("January 2018");
/*  85 */     label.setFont(SWTResourceManager.getFont("Calibri", 14, 1));
/*     */     
/*  87 */     Label lblNewLabel_2 = new Label(container, 0);
/*  88 */     lblNewLabel_2.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*  89 */     lblNewLabel_2.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/*  90 */     lblNewLabel_2.setText("HRE code is copyright to HRE Pty Ltd, as released");
/*     */     
/*  92 */     Label lblUnderGnuAffero = new Label(container, 0);
/*  93 */     lblUnderGnuAffero.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/*  94 */     lblUnderGnuAffero.setText("under GNU Affero General Public Licence ");
/*  95 */     lblUnderGnuAffero.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/*  97 */     Label label_1 = new Label(container, 0);
/*  98 */     label_1.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*  99 */     new Label(container, 0);
/*     */     
/* 101 */     Label lblIconsUsedFrom = new Label(container, 0);
/* 102 */     lblIconsUsedFrom.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 103 */     lblIconsUsedFrom.setText("Icons used from http://www.defaulticon.com       ");
/* 104 */     lblIconsUsedFrom.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 106 */     Label lblCopyrightInteractivemania = new Label(container, 0);
/* 107 */     lblCopyrightInteractivemania.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 108 */     lblCopyrightInteractivemania.setText("© copyright interactivemania 2010-2011,   ");
/* 109 */     lblCopyrightInteractivemania.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 111 */     Label lblAsReleasedUnder = new Label(container, 0);
/* 112 */     lblAsReleasedUnder.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 113 */     lblAsReleasedUnder.setText("as released under CC BY ND 3.0  ");
/* 114 */     lblAsReleasedUnder.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 116 */     Label label_3 = new Label(container, 0);
/* 117 */     label_3.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/* 118 */     new Label(container, 0);
/*     */     
/* 120 */     Label lblPreferenceCodeUsed = new Label(container, 0);
/* 121 */     lblPreferenceCodeUsed.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 122 */     lblPreferenceCodeUsed.setText("Preference code used from");
/* 123 */     lblPreferenceCodeUsed.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 125 */     Label lblHttpsgithubcomopcoachepreferences = new Label(container, 0);
/* 126 */     lblHttpsgithubcomopcoachepreferences.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 127 */     lblHttpsgithubcomopcoachepreferences.setText("https://github.com/opcoach/e4Preferences");
/* 128 */     lblHttpsgithubcomopcoachepreferences.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 130 */     Label lblWhichIsLicensed = new Label(container, 0);
/* 131 */     lblWhichIsLicensed.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 132 */     lblWhichIsLicensed.setText("which is licensed under the Eclipse Public License 1.0");
/* 133 */     lblWhichIsLicensed.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 135 */     Label lblNewLabel_3 = new Label(container, 0);
/* 136 */     lblNewLabel_3.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 137 */     lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/*     */     
/* 139 */     Label lblIfYouWish = new Label(container, 0);
/* 140 */     lblIfYouWish.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 141 */     lblIfYouWish.setText("If you wish to donate to continued development ");
/* 142 */     lblIfYouWish.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 144 */     Label lblOfHrePlease = new Label(container, 0);
/* 145 */     lblOfHrePlease.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 146 */     lblOfHrePlease.setText("of HRE, please go to our website at");
/* 147 */     lblOfHrePlease.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 149 */     Label lblWwwhistoryresearchenvironmentorgdonate = new Label(container, 0);
/* 150 */     lblWwwhistoryresearchenvironmentorgdonate.setLayoutData(new GridData(16384, 16777216, false, false, 2, 1));
/* 151 */     lblWwwhistoryresearchenvironmentorgdonate.setText("www.historyresearchenvironment.org/Donate/");
/* 152 */     lblWwwhistoryresearchenvironmentorgdonate.setFont(SWTResourceManager.getFont("Calibri", 12, 0));
/*     */     
/* 154 */     return container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Point getInitialSize()
/*     */   {
/* 162 */     return new Point(456, 671);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\dialogs\AboutDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */