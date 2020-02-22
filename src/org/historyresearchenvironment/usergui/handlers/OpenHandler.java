/*    */ package org.historyresearchenvironment.usergui.handlers;
/*    */ 
/*    */ import com.opcoach.e4.preferences.ScopedPreferenceStore;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.logging.Logger;
/*    */ import javax.inject.Inject;
/*    */ import org.eclipse.core.runtime.preferences.InstanceScope;
/*    */ import org.eclipse.e4.core.di.annotations.Execute;
/*    */ import org.eclipse.e4.core.services.events.IEventBroker;
/*    */ import org.eclipse.e4.ui.model.application.MApplication;
/*    */ import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
/*    */ import org.eclipse.e4.ui.workbench.modeling.EModelService;
/*    */ import org.eclipse.swt.widgets.FileDialog;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ import org.historyresearchenvironment.usergui.HreH2ConnectionPool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpenHandler
/*    */ {
/*    */   @Inject
/*    */   private static IEventBroker eventBroker;
/*    */   @Inject
/*    */   MApplication application;
/*    */   @Inject
/*    */   EModelService modelService;
/* 37 */   private static final Logger LOGGER = Logger.getLogger("global");
/*    */   
/*    */   private ScopedPreferenceStore store;
/*    */   
/*    */ 
/*    */   @Execute
/*    */   public void execute(Shell shell)
/*    */   {
/* 45 */     this.store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.historyresearchenvironment.usergui");
/* 46 */     this.store.getString("H2USERID");
/* 47 */     this.store.getString("H2PASSWORD");
/* 48 */     String dbName = this.store.getString("DBNAME");
/*    */     try
/*    */     {
/* 51 */       Connection conn = HreH2ConnectionPool.getConnection();
/* 52 */       conn.createStatement().execute("SHUTDOWN");
/* 53 */       conn.close();
/* 54 */       HreH2ConnectionPool.dispose();
/*    */     } catch (SQLException e) {
/* 56 */       String message = e.getClass() + ": " + e.getMessage() + ": " + e.getSQLState() + ", " + 
/* 57 */         e.getErrorCode();
/* 58 */       LOGGER.severe(message);
/* 59 */       eventBroker.post("MESSAGE", message);
/*    */     }
/*    */     
/* 62 */     LOGGER.info(dbName);
/* 63 */     FileDialog dialog = new FileDialog(shell);
/* 64 */     String[] extensions = { "*.db", "*.*" };
/* 65 */     dialog.setFilterExtensions(extensions);
/* 66 */     dialog.open();
/*    */     
/* 68 */     String shortname = dialog.getFileName();
/* 69 */     String[] parts = shortname.split("\\.");
/*    */     
/* 71 */     dbName = dialog.getFilterPath() + "/" + parts[0];
/* 72 */     LOGGER.info(dbName);
/* 73 */     this.store.putValue("DBNAME", dbName);
/*    */     
/* 75 */     MWindow window = (MWindow)this.modelService.find(
/* 76 */       "org.historyresearchenvironment.usergui.trimmedwindow.historyresourceenvironmentguimockup", 
/* 77 */       this.application);
/* 78 */     window.setLabel("History Resource Environment Mock Up V0.0.5 - " + dbName);
/*    */     try
/*    */     {
/* 81 */       Connection conn = HreH2ConnectionPool.getConnection();
/* 82 */       PreparedStatement stmt = conn.prepareStatement("SELECT MIN(PER_NO) SMALLEST FROM PERSON");
/* 83 */       ResultSet rs = stmt.executeQuery();
/*    */       
/* 85 */       if (rs.next()) {
/* 86 */         String perno = rs.getString("SMALLEST");
/* 87 */         LOGGER.info("Smallest person number: " + perno);
/* 88 */         this.store.putValue("PERNO", perno);
/* 89 */         eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(Integer.parseInt(perno)));
/*    */       } else {
/* 91 */         eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(1));
/*    */       }
/* 93 */       conn.close();
/*    */     } catch (SQLException localSQLException1) {
/* 95 */       eventBroker.post("PERSON_UPDATE_TOPIC", Integer.valueOf(1));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Temp\HRE Mockup Product-win32.win32.x86_64\plugins\org.historyresearchenvironment.usergui_1.0.0.201801161825.jar!\org\historyresearchenvironment\usergui\handlers\OpenHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */