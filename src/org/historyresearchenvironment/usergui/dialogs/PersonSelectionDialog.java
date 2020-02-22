/*     */ package org.historyresearchenvironment.usergui.dialogs;
/*     */ 
/*     */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*     */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*     */ import org.eclipse.jface.dialogs.Dialog;
/*     */ import org.eclipse.jface.dialogs.IDialogConstants;
/*     */ import org.eclipse.swt.events.ModifyEvent;
/*     */ import org.eclipse.swt.events.ModifyListener;
/*     */ import org.eclipse.swt.events.MouseAdapter;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.graphics.Font;
/*     */ import org.eclipse.swt.graphics.FontData;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.layout.GridData;
/*     */ import org.eclipse.swt.layout.GridLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Control;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ import org.eclipse.wb.swt.SWTResourceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PersonSelectionDialog
/*     */   extends Dialog
/*     */ {
/*     */   private final ScopedPreferenceStore store;
/*     */   private Text textPerNo;
/*  36 */   private String perNo = "";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PersonSelectionDialog(Shell parentShell)
/*     */   {
/*  44 */     super(parentShell);
/*  45 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/*     */   }
/*     */   
/*     */   protected void configureShell(Shell newShell)
/*     */   {
/*  50 */     super.configureShell(newShell);
/*  51 */     newShell.setText("Select Person by ID");
/*  52 */     Font font = newShell.getFont();
/*  53 */     FontData fd = font.getFontData()[0];
/*  54 */     font = SWTResourceManager.getFont(fd.getName(), 12, fd.getStyle());
/*  55 */     newShell.setFont(font);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createButtonsForButtonBar(Composite parent)
/*     */   {
/*  65 */     Button button = createButton(parent, 0, IDialogConstants.OK_LABEL, true);
/*  66 */     button.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseDown(MouseEvent e) {
/*  69 */         PersonSelectionDialog.this.store.putValue("PERNO", PersonSelectionDialog.this.perNo);
/*     */       }
/*  71 */     });
/*  72 */     createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Control createDialogArea(Composite parent)
/*     */   {
/*  82 */     Composite container = (Composite)super.createDialogArea(parent);
/*  83 */     container.setLayout(new GridLayout(2, false));
/*     */     
/*  85 */     Label lblSelectPersonId = new Label(container, 0);
/*  86 */     lblSelectPersonId.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0));
/*  87 */     lblSelectPersonId.setLayoutData(new GridData(131072, 16777216, false, false, 1, 1));
/*  88 */     lblSelectPersonId.setText("Person Id:");
/*     */     
/*  90 */     this.textPerNo = new Text(container, 2048);
/*  91 */     this.textPerNo.addModifyListener(new ModifyListener()
/*     */     {
/*     */       public void modifyText(ModifyEvent e) {
/*  94 */         PersonSelectionDialog.this.perNo = PersonSelectionDialog.this.textPerNo.getText();
/*     */       }
/*  96 */     });
/*  97 */     this.textPerNo.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
/*     */     
/*  99 */     this.perNo = this.store.getString("PERNO");
/*     */     
/* 101 */     return container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Point getInitialSize()
/*     */   {
/* 109 */     return new Point(286, 139);
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\dialogs\PersonSelectionDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */