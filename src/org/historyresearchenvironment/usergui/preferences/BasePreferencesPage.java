/*     */ package org.historyresearchenvironment.usergui.preferences;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.eclipse.jface.preference.BooleanFieldEditor;
/*     */ import org.eclipse.jface.preference.ComboFieldEditor;
/*     */ import org.eclipse.jface.preference.DirectoryFieldEditor;
/*     */ import org.eclipse.jface.preference.FieldEditorPreferencePage;
/*     */ import org.eclipse.jface.preference.FontFieldEditor;
/*     */ import org.eclipse.jface.preference.IntegerFieldEditor;
/*     */ import org.eclipse.jface.preference.StringFieldEditor;
/*     */ import org.eclipse.jface.util.PropertyChangeEvent;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasePreferencesPage
/*     */   extends FieldEditorPreferencePage
/*     */ {
/*  22 */   private final Logger LOGGER = Logger.getLogger("global");
/*     */   
/*     */   private ComboFieldEditor comboFieldEditorCsMode;
/*     */   
/*     */   private ComboFieldEditor comboFieldEditorLogLevel;
/*     */   private FontFieldEditor fontFieldEditor;
/*     */   private StringFieldEditor updateSiteFieldEditor;
/*     */   private DirectoryFieldEditor directoryFieldEditor;
/*     */   private IntegerFieldEditor helpportIntegerFieldEditor;
/*     */   private IntegerFieldEditor serverportIntegerFieldEditor;
/*     */   
/*     */   public BasePreferencesPage()
/*     */   {
/*  35 */     super(1);
/*  36 */     setTitle("Base Preferences");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void createFieldEditors()
/*     */   {
/*  47 */     this.comboFieldEditorCsMode = new ComboFieldEditor(
/*  48 */       "CSMODE", "Client/Server Mode", new String[][] { { "TEST (Hardcoded Values)", "TEST" }, 
/*  49 */       { "DIRECT (Not using TCP/IP)", "DIRECT" }, { "SERVER (Call using TCP/IP)", "SERVER" } }, 
/*  50 */       getFieldEditorParent());
/*     */     
/*  52 */     addField(this.comboFieldEditorCsMode);
/*     */     
/*  54 */     Composite composite = getFieldEditorParent();
/*     */     
/*  56 */     StringFieldEditor stringFieldEditorServerAddress = new StringFieldEditor("SERVERADDRESS", 
/*  57 */       "Server IP Address and port", composite);
/*  58 */     stringFieldEditorServerAddress.getTextControl(composite).setText("127.0.0.1:8000");
/*  59 */     addField(stringFieldEditorServerAddress);
/*     */     
/*  61 */     BooleanFieldEditor booleanFieldEditorTls = new BooleanFieldEditor("TLS", "Secure Connection", 
/*  62 */       0, getFieldEditorParent());
/*  63 */     addField(booleanFieldEditorTls);
/*     */     
/*  65 */     this.directoryFieldEditor = new DirectoryFieldEditor("LOGFILEPATH", "Log file directory", getFieldEditorParent());
/*  66 */     addField(this.directoryFieldEditor);
/*     */     
/*  68 */     this.comboFieldEditorLogLevel = new ComboFieldEditor("LOGLEVEL", "Log Level", 
/*  69 */       new String[][] { { "OFF", "OFF" }, { "SEVERE", "SEVERE" }, { "WARNING", "WARNING" }, { "INFO", "INFO" }, 
/*  70 */       { "CONFIG", "CONFIG" }, { "FINE", "FINE" }, { "FINER", "FINER" }, { "FINEST", "FINEST" }, 
/*  71 */       { "ALL", "ALL" } }, 
/*  72 */       getFieldEditorParent());
/*  73 */     addField(this.comboFieldEditorLogLevel);
/*     */     
/*  75 */     this.fontFieldEditor = new FontFieldEditor("HREFONT", "Font Selection", null, getFieldEditorParent());
/*  76 */     addField(this.fontFieldEditor);
/*     */     
/*  78 */     this.updateSiteFieldEditor = new StringFieldEditor("UPDATESITE", "HRE Update Site", -1, 
/*  79 */       0, getFieldEditorParent());
/*  80 */     addField(this.updateSiteFieldEditor);
/*     */     
/*  82 */     this.helpportIntegerFieldEditor = new IntegerFieldEditor("HELPSYSTEMPORT", "Port number for Help System", 
/*  83 */       getFieldEditorParent());
/*  84 */     addField(this.helpportIntegerFieldEditor);
/*     */     
/*  86 */     this.serverportIntegerFieldEditor = new IntegerFieldEditor("SERVERPORT", "Port Number for local HRE Server", 
/*  87 */       getFieldEditorParent());
/*  88 */     addField(this.serverportIntegerFieldEditor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent event)
/*     */   {
/* 100 */     super.propertyChange(event);
/*     */     
/* 102 */     if (event.getSource() == this.fontFieldEditor) {
/* 103 */       this.LOGGER.info("New font: " + event.getNewValue().toString());
/* 104 */     } else if (event.getSource() == this.updateSiteFieldEditor) {
/* 105 */       this.LOGGER.info("New update site: " + event.getNewValue().toString());
/* 106 */     } else if (event.getSource() == this.comboFieldEditorLogLevel) {
/* 107 */       String levelName = event.getNewValue().toString();
/* 108 */       if (levelName.equals("OFF")) {
/* 109 */         this.LOGGER.setLevel(Level.OFF);
/* 110 */       } else if (levelName.equals("SEVERE")) {
/* 111 */         this.LOGGER.setLevel(Level.SEVERE);
/* 112 */       } else if (levelName.equals("WARNING")) {
/* 113 */         this.LOGGER.setLevel(Level.WARNING);
/* 114 */       } else if (levelName.equals("CONFIG")) {
/* 115 */         this.LOGGER.setLevel(Level.CONFIG);
/* 116 */       } else if (levelName.equals("FINE")) {
/* 117 */         this.LOGGER.setLevel(Level.FINE);
/* 118 */       } else if (levelName.equals("FINER")) {
/* 119 */         this.LOGGER.setLevel(Level.FINER);
/* 120 */       } else if (levelName.equals("FINEST")) {
/* 121 */         this.LOGGER.setLevel(Level.FINEST);
/* 122 */       } else if (levelName.equals("ALL")) {
/* 123 */         this.LOGGER.setLevel(Level.ALL);
/*     */       } else {
/* 125 */         this.LOGGER.setLevel(Level.INFO);
/*     */       }
/* 127 */       this.LOGGER.info("Logger level: " + levelName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\preferences\BasePreferencesPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */