/*    */ package org.historyresearchenvironment.usergui.preferences;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.logging.Logger;
/*    */ import org.eclipse.jface.preference.ComboFieldEditor;
/*    */ import org.eclipse.jface.preference.FieldEditorPreferencePage;
/*    */ import org.eclipse.jface.preference.StringFieldEditor;
/*    */ import org.eclipse.jface.util.PropertyChangeEvent;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Text;
/*    */ import org.historyresearchenvironment.usergui.HreH2ConnectionPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class H2PreferencesPage
/*    */   extends FieldEditorPreferencePage
/*    */ {
/* 20 */   private final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */   private ComboFieldEditor comboFieldEditorH2TraceLevel;
/*    */   
/*    */   private StringFieldEditor stringFieldEditorUserid;
/*    */   private StringFieldEditor stringFieldEditorPassword;
/*    */   
/*    */   public H2PreferencesPage()
/*    */   {
/* 29 */     super(1);
/* 30 */     setTitle("H2 Preferences");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void createFieldEditors()
/*    */   {
/* 41 */     Composite composite = getFieldEditorParent();
/*    */     
/* 43 */     this.stringFieldEditorUserid = new StringFieldEditor("H2USERID", "User Id", -1, 
/* 44 */       0, composite);
/* 45 */     this.stringFieldEditorUserid.getTextControl(composite).setText("sa");
/* 46 */     addField(this.stringFieldEditorUserid);
/*    */     
/* 48 */     this.stringFieldEditorPassword = new StringFieldEditor("H2PASSWORD", "Password", -1, 
/* 49 */       0, getFieldEditorParent());
/* 50 */     addField(this.stringFieldEditorPassword);
/*    */     
/* 52 */     this.comboFieldEditorH2TraceLevel = new ComboFieldEditor("H2TRACELEVEL", "Trace Level", 
/* 53 */       new String[][] { { "OFF", "OFF" }, { "ERROR", "ERROR" }, { "INFO", "INFO" }, { "DEBUG", "DEBUG" } }, 
/* 54 */       getFieldEditorParent());
/* 55 */     addField(this.comboFieldEditorH2TraceLevel);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void propertyChange(PropertyChangeEvent event)
/*    */   {
/* 67 */     super.propertyChange(event);
/*    */     
/* 69 */     if (event.getSource() == this.comboFieldEditorH2TraceLevel) {
/* 70 */       int h2TraceLevel = 2;
/* 71 */       String levelName = event.getNewValue().toString();
/*    */       
/* 73 */       if (levelName.equals("OFF")) {
/* 74 */         h2TraceLevel = 0;
/* 75 */       } else if (levelName.equals("ERROR")) {
/* 76 */         h2TraceLevel = 1;
/*    */       } else {
/* 78 */         h2TraceLevel = 3;
/*    */       }
/*    */       try
/*    */       {
/* 82 */         Connection conn = HreH2ConnectionPool.getConnection();
/* 83 */         PreparedStatement prep = conn.prepareStatement("SET TRACE_LEVEL_SYSTEM_OUT ?");
/* 84 */         prep.setInt(1, h2TraceLevel);
/* 85 */         prep.executeUpdate();
/* 86 */         prep = conn.prepareStatement("SET TRACE_LEVEL_FILE ?");
/* 87 */         prep.setInt(1, h2TraceLevel);
/* 88 */         prep.executeUpdate();
/* 89 */         prep.close();
/* 90 */         conn.close();
/*    */       } catch (SQLException e) {
/* 92 */         this.LOGGER.severe(e.getMessage() + ", " + e.getErrorCode() + ", " + e.getSQLState());
/*    */       }
/*    */       
/* 95 */       this.LOGGER.info("H2 trace level: " + h2TraceLevel);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\preferences\H2PreferencesPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */